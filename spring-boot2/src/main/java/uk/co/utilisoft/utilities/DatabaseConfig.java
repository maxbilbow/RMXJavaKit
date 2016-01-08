package main.java.uk.co.utilisoft.utilities;


import org.springframework.jdbc.datasource.DriverManagerDataSource;
import uk.co.utilisoft.poi.PoisxlsApplication;

import javax.sql.DataSource;

/**
 * Created by bilbowm (Max Bilbow) on 30/11/2015.
 */
public interface DatabaseConfig {
    String getUrl();

    String getUrl(Class<?> clazz);

    String getUrl(String path);

    default String getUrl(Object object)
    {
        if (object == null)
            return getUrl(PoisxlsApplication.class);
        if (object instanceof String)
            return getUrl((String) object);
        else if (object instanceof Class<?>)
            return getUrl((Class<?>) object);
        else
            throw new RuntimeException(object.toString());

    }

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
