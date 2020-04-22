package phd.sa.csie.ntut.edu.tw.controller.database;

import java.sql.*;

public class DB_connector {
  private final static String url = "jdbc:mysql://ois-mysql.hsiang.me:3306/SA";
  private final static String user = "SA";
  private final static String password = "sa";

  public final static Connection getConnection() throws SQLException{
    return DriverManager.getConnection(url, user, password);
  }

  public final static void closeConnection(Connection connection) throws SQLException{
    connection.close();
  }

}