package ddd.kanban.adapter.database;

import ddd.kanban.adapter.repository.board.SqliteBoardRepository;

import java.sql.*;

public class DataBaseUtility {

    private Connection connection;

    public DataBaseUtility(){

    }

    public Connection getConnection(){
        connection = null;
        String url = "jdbc:sqlite:ddd.kanban.db";
        try {
            connection = DriverManager.getConnection(url);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
           throw new RuntimeException(e.getMessage());
        }
        return connection;
    }

    public void rollBack(Connection connection){
        try{
            connection.rollback();
        } catch (SQLException e){
            throw new RuntimeException("roll back fail");
        }
    }

    public void close(Connection connection){
        try{
            connection.close();
        } catch (SQLException e){
            throw new RuntimeException("close connection fail");
        }
    }
}
