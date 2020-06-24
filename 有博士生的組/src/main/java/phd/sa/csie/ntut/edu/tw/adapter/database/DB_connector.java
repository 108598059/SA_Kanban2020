package phd.sa.csie.ntut.edu.tw.adapter.database;

import java.sql.*;

public class DB_connector {
    private final static String url = "jdbc:mysql://db4free.net:3306/teddysa";
    private final static String user = "teddysa";
    private final static String password = "lab1321bal";

    public final static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public final static void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }

}