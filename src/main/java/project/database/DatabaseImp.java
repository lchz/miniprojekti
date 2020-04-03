package project.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import static spark.Spark.port;

/**
 *
 * @author chenhuiz
 */
public class DatabaseImp implements Database {

    private String databaseUrl;

    public DatabaseImp(String databaseUrl) throws SQLException {
        this.databaseUrl = databaseUrl;
        init();
    }

    @Override
    public void init() throws SQLException {
        port(4566);

        createTables();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseUrl);
    }

    private void createTables() throws SQLException {

        String createBookTable = "CREATE TABLE IF NOT EXISTS Book(\n"
                + "     id integer PRIMARY KEY,\n"
                + "     title varchar(144) NOT NULL,\n"
                + "     author varchar(144) NOT NULL,\n"
                + "     ISBN varchar(144) NOT NULL, \n"
                + "     description varchar(1000) NOT NULL,\n"
                + "     url varchar(1000) NOT NULL,\n"
                + "     checked boolean\n"
                + ");";

        String createVideoTable = "CREATE TABLE IF NOT EXISTS Video(\n"
                + "     id integer PRIMARY KEY,\n"
                + "     title varchar(144) NOT NULL,\n"
                + "     author varchar(144) NOT NULL,\n"
                + "     description varchar(1000) NOT NULL,\n"
                + "     url varchar(1000) NOT NULL,\n"
                + "     checked boolean\n"
                + ");";

        Statement stmt = getConnection().createStatement();
        stmt.executeUpdate(createBookTable);
        stmt.executeUpdate(createVideoTable);

    }

}
