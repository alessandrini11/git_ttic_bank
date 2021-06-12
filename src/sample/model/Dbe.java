package sample.model;

import java.sql.*;

public class Dbe {
    private final static String link = "jdbc:mysql://localhost:3306/bank";
    private final static String user = "root";
    private final static String password = "";

    public static Connection getConnection()  {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(link,user,password);
            System.out.println("connected");

        } catch (SQLException e) {
            System.out.println("Erreur :"+e.getMessage());

        }
        return connection;
    }

}
