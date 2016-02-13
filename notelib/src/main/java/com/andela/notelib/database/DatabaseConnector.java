package com.andela.notelib.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * This class creates a <code>Connection</code> object's database is able to provide information
 * describing its tables, its supported SQL grammar, its stored
 * procedures, the capabilities of this connection, and so on.
 *
 * @author OGUNDE KEHINDE
 * @see DriverManager#getConnection
 * @see java.sql.Statement
 * @see java.sql.ResultSet
 * @see java.sql.DatabaseMetaData
 */
public class DatabaseConnector {

    /**
     * <P>A connection (session) with a specific
     * database. SQL statements are executed and results are returned
     * within the context of a connection.
     * <p/>
     */
    private Connection connection;

    /**
     * stores universal resource locator to a database
     */
    private String url;

    /**
     * stores username to a database
     */
    private String username;

    /**
     * stores password to a database
     */
    private String password;

    /**
     * stores a database name
     */
    private String database;

    private String driver;

    /**
     * detect a connection
     */
    private boolean active;

    /**
     * constructs this class with the specified argument
     *  @param url      to a database
     * @param driver to set connection
     */
    public DatabaseConnector(String url, String database, String driver) {
        this.url = url;
        this.username = "";
        this.password = "";
        this.database = database;
        this.driver = driver;
    }

    /**
     * constructs this class with the specified argument
     *  @param url      to a database
     * @param username to a database
     * @param password to a database
     * @param driver to set connection
     */
    public DatabaseConnector(String url, String username, String password, String driver) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.driver = driver;
    }

    /**
     * constructs this class with the specified arguments
     *
     * @param url      to a database
     * @param username to a database
     * @param password to a database
     * @param database to a database
     */
    public DatabaseConnector(String url, String username, String password, String database, String driver) {
        this(url, username, password, driver);
        this.database = database;
    }

    public DatabaseConnector(String url) {
        this.url = url;
    }

    /**
     * Try to create a database connection
     * if and only if this url, username and password
     * is not <code>null</code>.
     * Returns a Connection element if and only if
     * connection throws no errors.
     *
     * @return a connection element
     * @throws Exception
     */
    public Connection connect() throws Exception {
        this.connection = new org.sqldroid.SQLDroidDriver().connect(this.url, new Properties());
        return connection;
    }

    /**
     * Disconnect this connection if its been active
     *
     * @throws SQLException
     */
    public void disconnect() throws SQLException {
        if (connection != null)
            connection.close();
    }

    /**
     * Returns <code>true</code> is and only is this connection
     * is not closed.
     *
     * @return a boolean <code>true</code> or <code>false</code>
     * @throws SQLException if error occurs.
     */
    public boolean isActive() throws SQLException {
        return !connection.isClosed();
    }

    public String getDatabase() {
        return this.database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }
}
