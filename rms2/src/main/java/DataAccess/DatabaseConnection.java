package DataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection;
    
    private DatabaseConnection(){
        
    }

    public static synchronized Connection getInstance() {
        if (connection == null) {
            connect();
        }
        return connection;
    }

    private static void connect(){
        try{
            String url = "jdbc:postgresql://localhost:5432/rms";
            String username = "mike";
            String password = "landis";

            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the Database.");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void close(){
        try {
            if(connection != null){
                connection.close();
                System.out.println("Connection Closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    public Connection getConn(){
        return connection;
    }
}
