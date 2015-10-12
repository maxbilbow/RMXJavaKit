package click.rmx.persistence;

import click.rmx.debug.Bugger;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import java.util.Properties;

/**
 * Created by Max on 12/10/2015.
 */
public class SQLConnectionDescriber implements SQLBeano {
    private String
            username = "root",
            password = "password",
            connectionUrl;

    private final SQLDescriber sqlDescriber;

    private String [] packagesToScan;

    private Properties hibernateProperties = new Properties();

    public SQLConnectionDescriber(SQLDescriber sqlDescriber) {
        this.sqlDescriber = sqlDescriber;
    }


    public static SQLConnectionDescriber newMySQLDescriber(String connectionUrl)
    {
        return new SQLConnectionDescriber(SQLType.MY_SQL).setConnectionUrl(connectionUrl);
    }
    public static SQLConnectionDescriber newMySQLDescriber()
    {
        return new SQLConnectionDescriber(SQLType.MY_SQL);
    }

    public Properties getHibernateProperties() {
        return hibernateProperties;
    }

    public SQLConnectionDescriber setHibernateProperties(Properties hibernateProperties) {
        this.hibernateProperties = hibernateProperties;
        return this;
    }

    public String[] getPackagesToScan() {
        return packagesToScan;
    }

    public SQLConnectionDescriber setPackagesToScan(String... packagesToScan) {
        this.packagesToScan = packagesToScan;
        return this;
    }

    public SQLDescriber getSqlDescriber() {
        return sqlDescriber;
    }

    public String getConnectionUrl() {
        return connectionUrl;
    }

    public SQLConnectionDescriber setConnectionUrl(String connectionUrl) {
        this.connectionUrl = connectionUrl;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public SQLConnectionDescriber setPassword(String password) {
        this.password = password; return this;
    }

    public String getUsername() {
        return username;
    }

    public SQLConnectionDescriber setUsername(String username) {
        this.username = username; return this;
    }

    public String getDialect() {
        return this.getSqlDescriber().getDialect();
    }

    public String getDriver()
    {
        return this.getSqlDescriber().getDriver();
    }


    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(driverManagerDataSourceBean());
        if (packagesToScan == null)
            Bugger.logAndPrint("WARNING: packageToScan not set.", true);
        else
            em.setPackagesToScan(packagesToScan);

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        em.setJpaProperties(hibernateProperties);

        return em;
    }


    public DriverManagerDataSource driverManagerDataSourceBean() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("org.hsqldb.jdbc.JDBCDriver");
//        dataSource.setUrl("jdbc:hsqldb:file:/Users/Max/Gits/rex-persistence/hsqldb/rmxdb?autoReconnect=true&createDatabaseIfNotExist=true");
        dataSource.setDriverClassName(this.getDriver());
        dataSource.setUrl(this.getConnectionUrl());
        dataSource.setUsername(this.getUsername());
        dataSource.setPassword(this.getPassword());
        return dataSource;
    }



    public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }


    public static SQLConnectionDescriber newHSQLDBDescriber() {
        return new SQLConnectionDescriber(SQLType.HSQLDB);
    }
}
