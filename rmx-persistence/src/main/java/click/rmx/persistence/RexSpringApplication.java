package click.rmx.persistence;

import click.rmx.debug.Bugger;
import click.rmx.debug.RMXException;
import click.rmx.persistence.model.Box;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.util.Properties;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories
@EnableTransactionManagement
@ComponentScan("click.rmx.persistence")
public class RexSpringApplication {

    private static SQLConnectionDescriber sqlConnectionTemp;

    private final SQLConnectionDescriber sqlConnectionDescriber;

    public RexSpringApplication()
    {
        this.sqlConnectionDescriber = sqlConnectionTemp;
        sqlConnectionTemp = null;
    }

    /**
     *   Minimum requirement for properties:
     *   <code>
     *        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
     *   </code>
     * @param args - from main(String [] args);
     * @throws RMXException
     */
    public static void startDatabaseWithProperties(
            Class<? extends RexSpringApplication> springBootApplication,
            SQLConnectionDescriber sqlConnectionDescriber,
            String [] args)
    {

        sqlConnectionTemp = sqlConnectionDescriber;
        Properties hibernateProperties = sqlConnectionDescriber.getHibernateProperties();

        hibernateProperties.setProperty("hibernate.dialect", sqlConnectionDescriber.getDialect());

        if (!hibernateProperties.containsKey("hibernate.hbm2ddl.auto"))
        {
            Bugger.logAndPrint("hibernate.hbm2ddl.auto not given. Default will be \"update\"", true);
            hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
        }
        if (!hibernateProperties.containsKey("hibernate.globally_quoted_identifiers")) {
            Bugger.logAndPrint("hibernate.globally_quoted_identifiers not given. Default will be \"true\"", true);
            hibernateProperties.setProperty("hibernate.globally_quoted_identifiers", "true");
        }

        SpringApplication.run(springBootApplication, args);
    }

    private static final SQLType sqlType = SQLType.HSQLDB;

    public static void main(String[] args) {
        SQLConnectionDescriber sqlDescriber;// = SQLConnectionDescriber.newMySQLDescriber();

        switch (sqlType) {
            case HSQLDB:
                sqlDescriber = SQLConnectionDescriber.newHSQLDBDescriber();
                sqlDescriber.setConnectionUrl("jdbc:hsqldb:file:/Users/Max/Gits/rex-spring/hsqldb/rmxdb" +
                        "?autoReconnect=true&createDatabaseIfNotExist=true");
                break;
            case MY_SQL:
            default:
                sqlDescriber = SQLConnectionDescriber.newMySQLDescriber();
                sqlDescriber.setConnectionUrl("jdbc:mysql://localhost:3306/rmxdb" +
                        "?autoReconnect=true&createDatabaseIfNotExist=true");
                break;

        }

        sqlDescriber.setPackagesToScan("click.rmx.persistence.model");

        startDatabaseWithProperties(
                RexSpringApplication.class,
                sqlDescriber,
                args);


//        SpringApplication.run(RexSpringApplication.class, args);
        Box box = new Box();
        box.setName("Boxy: " + Math.random());
//        boxRepository.save(box);
        BoxController.getInstance().save(box);

        BoxController.getInstance().getAll().stream().forEach(System.out::println);
    }



    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        return this.sqlConnectionDescriber.entityManagerFactoryBean();
    }



    @Bean
    public DriverManagerDataSource dataSource() {
        return this.sqlConnectionDescriber.driverManagerDataSourceBean();
    }


    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }


}