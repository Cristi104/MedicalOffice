package persistence.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class DataBaseConnection {
    private Connection connection;
    private static DataBaseConnection dataBaseConnection;

    public static DataBaseConnection getInstance(){
        if(DataBaseConnection.dataBaseConnection == null){
            DataBaseConnection.dataBaseConnection = new DataBaseConnection("root", "password");
        }
        return DataBaseConnection.dataBaseConnection;
    }

    public DataBaseConnection(String username, String password){
        String url = "jdbc:mysql://127.0.0.1:3306/mysql?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
        try {
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            System.out.println("Connection failed.");
            System.out.println(e);
        }
    }

    public void seedDatabase(){
        try {
            String content = new Scanner(new File("db_init.sql")).useDelimiter("\\Z").next();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void close(){
        try {
            this.connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

}

