package click.rmx.debug.logger.config;

import click.rmx.debug.Bugger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.persistence.EntityManagerFactory;
import java.util.Properties;

/**
 * Created by bilbowm on 15/10/2015.
 */
//@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("click.rmx.debug.logger.repository")
public class DBConfig implements TransactionManagementConfigurer {

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return transactionManager(entityManagerFactory);
    }
//jdbc:h2:C:/ide/projects/rmx/RMXJavaKit/rmx-kit/rmx-debug-logger/src/main/resources/click/rmx/debug/logger/config/h2
    enum DB {
        LOCAL("jdbc:mysql://localhost:3306/debug_server", //3307 at home
                "root", "password", "com.mysql.jdbc.Driver", "org.hibernate.dialect.MySQL5Dialect"),
        H2("jdbc:h2:${path}debug_server","","", "org.h2.Driver", "org.hibernate.dialect.H2Dialect");
        public final String username, password, driver, dialect;
        private final String url;

        DB(String url, String username, String password, String driver, String dialect) {
            this.url = url;
            this.username = username;
            this.password = password;
            this.driver = driver;
            this.dialect = dialect;
        }

        public String getUrl(Class<?> clazz){
            String database = url.replace("${path}",clazz.getResource("").getFile());
            Bugger.print(database);
            return database;
        }

    }
    private final DB db = DB.H2;


    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(new String[] { "click.rmx.debug.logger.model" });

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());

        return em;
    }

    final Properties additionalProperties() {
        final Properties hibernateProperties = new Properties();//TODO change to create-drop or add config options
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", false ? "update" : "create-drop");
        hibernateProperties.setProperty("hibernate.dialect", db.dialect);
        hibernateProperties.setProperty("hibernate.globally_quoted_identifiers", "true");
        hibernateProperties.setProperty("autoReconnect","true");
        hibernateProperties.setProperty("createDatabaseIfNotExist","true");
        return hibernateProperties;
    }


    @Bean
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(db.driver);
        dataSource.setUrl(db.getUrl(this.getClass()));
        dataSource.setUsername(db.username);
        dataSource.setPassword(db.password);
        return dataSource;
    }

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory = emf);
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

}
