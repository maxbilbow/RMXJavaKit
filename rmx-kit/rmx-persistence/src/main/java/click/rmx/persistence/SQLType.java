package click.rmx.persistence;

/**
 * Created by Max on 11/10/2015.
 */
public enum SQLType implements SQLDescriber {
    MY_SQL(
            "com.mysql.jdbc.Driver",
            "org.hibernate.dialect.MySQL5Dialect"
    ),
    HSQLDB(
            "org.hsqldb.jdbc.JDBCDriver",
            "org.hibernate.dialect.HSQLDialect"
    );

    private final String driver;
    private final String dialect;

    SQLType(String driver, String dialect) {
        this.driver = driver;
        this.dialect = dialect;
    }

    public String getDriver() {
        return driver;
    }

    public String getDialect() {
        return dialect;
    }
}
