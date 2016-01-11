package click.rmx.config;

/**
 * Created by bilbowm (Max Bilbow) on 30/11/2015.
 */
public enum Database implements DatabaseConfig {
    DEFAULT("jdbc:sqlserver://10.0.125.93:1433", //3307 at home
            "Sa", "formfill", "com.microsoft.sqlserver.jdbc.SQLServerDriver", "org.hibernate.dialect.SQLServer2012Dialect"),
    AMOPS("jdbc:sqlserver://10.0.125.93:1433;databaseName=AMOPS_GKA", //3307 at home
            "Sa", "formfill", "com.microsoft.sqlserver.jdbc.SQLServerDriver", "org.hibernate.dialect.SQLServer2012Dialect"),
    ENT_CASE("jdbc:sqlserver://10.0.125.93:1433;databaseName=ENT_CASE113_1_0", //3307 at home
            "Sa", "formfill", "com.microsoft.sqlserver.jdbc.SQLServerDriver", "org.hibernate.dialect.SQLServer2012Dialect"),
    H2("jdbc:h2:${path}app","","", "org.h2.Driver", "org.hibernate.dialect.H2Dialect");
    public final String username, password, driver, dialect;
    private final String url;

    Database(String url, String username, String password, String driver, String dialect) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.driver = driver;
        this.dialect = dialect;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getUrl(Class<?> clazz) {
        String database = url.replace("${path}", clazz.getResource("").getFile());
        return database;
    }

    @Override
    public String getUrl(String path) {
        String database = url.replace("${path}", path);
        return database;
    }

    @Override
    public String getDriver() {
        return driver;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getDialect() {
        return dialect;
    }


}