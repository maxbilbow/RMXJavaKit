package click.rmx.config;

import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by Max on 20/12/2015.
 */
public abstract class AbstractDatabaseConfig {
    private DataSource dataSource;
    private LocalContainerEntityManagerFactoryBean entityManagerFactoryBean;
    private JpaTransactionManager transactionManager;
    protected abstract DatabaseConfig getDatabase();

    protected abstract String[] getDaoScanLocations();

    /**
     *
     * @return Either a string path or a class (which will give a resource location).
     */
    protected abstract Object databaseLocation();

    /**
     * Expose this bean, override or reference.
     * @return
     */
    protected DataSource dataSource()
    {
        if (dataSource != null)
            return dataSource;

        final DatabaseConfig db = getDatabase();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(db.getDriver());
        dataSource.setUrl(db.getUrl(databaseLocation()));
        dataSource.setUsername(db.getUsername());
        dataSource.setPassword(db.getPassword());
//        logInfo("Creating Data Source: " + db);
        return this.dataSource = dataSource;
    }


    protected Properties additionalProperties() {
        final DatabaseConfig db = getDatabase();
        final Properties hibernateProperties = new Properties();//TODO change to create-drop or add config options
//        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", false ? "update" : "create-drop");
        hibernateProperties.setProperty("hibernate.dialect", db.getDialect());
//        hibernateProperties.setProperty("hibernate.globally_quoted_identifiers", "true");
        hibernateProperties.setProperty("autoReconnect","true");
//        hibernateProperties.setProperty("createDatabaseIfNotExist","true");
        return hibernateProperties;
    }


    protected LocalContainerEntityManagerFactoryBean entityManagerFactory()
    {
        return this.entityManagerFactory(dataSource());
    }

    //    @Bean
    protected LocalContainerEntityManagerFactoryBean entityManagerFactory(final DataSource dataSource) {
        if (entityManagerFactoryBean != null)
            return entityManagerFactoryBean;
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan(getDaoScanLocations());

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());
//        logInfo("Creating LocalContainerEntityManagerFactoryBean: " + getDatabase());
        return entityManagerFactoryBean = em;
    }

    protected PlatformTransactionManager transactionManager()
    {
        return transactionManager(entityManagerFactory().getObject());
    }

    protected PlatformTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory) {
        if (transactionManager != null)
            return transactionManager;
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);//entityManagerFactory = emf);
//        logInfo("Creating transactionManager: " + getDatabase());
        return this.transactionManager = transactionManager;
    }

    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

}
