package click.rmx.config;


import org.springframework.jdbc.datasource.DriverManagerDataSource;

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
            return getUrl(ClassLoader.getSystemResource("")); //TODO Check where this points to...
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
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(this.getDriver());
        dataSource.setUrl(this.getUrl());
        dataSource.setUsername(this.getUsername());
        dataSource.setPassword(this.getPassword());
//        logInfo("Creating Data Source: " + db);
        return dataSource;
    }
//
}
