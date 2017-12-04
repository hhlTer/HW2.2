package net.procelyte.jdbctutorial;

//import com.mysql.jdbc.*;

import java.sql.*;

class DBConnection {
    private static final java.lang.String URL =  "jdbc:mysql://localhost:3306/first_linux";
    private static final java.lang.String DRIVER = "com.mysql.jdbc.Driver";
    private static final String NAME = "root";
    private static final String PASS = "cttcmy";

    private static Connection connection;

    static {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, NAME, PASS);
        } catch (SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    static Connection getConnection() {
        return connection;
    }
}
