package domain.adapters;

import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseImpl{
    public static Connection getConnection() {
        try {
            new Driver();
            return DriverManager.getConnection("jdbc:mysql://140.124.181.23:8787/kanban?user=user&password=Password123&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC");
        } catch (SQLException var5) {
            System.out.println("SQLException: " + var5.getMessage());
            System.out.println("SQLState: " + var5.getSQLState());
            System.out.println("VendorError: " + var5.getErrorCode());
            return null;
        }
    }
}
