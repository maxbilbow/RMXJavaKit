package click.rmx.persistance;


import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * Created by bilbowm (Max Bilbow) on 30/11/2015.
 */
public interface DatabaseConfig {
    String getUrl();

    String getDriver();

    String getUsername();

    String getPassword();

    String getDialect();


    default DataSource create()
    {
        return create(this.getUrl(),this.getDriver(),this.getUsername(),this.getPassword());
    }

    static DataSource create(String url,
                             String driver,
                             String username,
                             String password)
    {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
//        dataSource.
//        logInfo("Creating Data Source: " + db);
        return dataSource;
    }


//
}
