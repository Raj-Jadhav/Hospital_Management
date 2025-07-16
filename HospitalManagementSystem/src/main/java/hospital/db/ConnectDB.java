/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hospital.db;
import java.sql.*;
import javax.swing.*;

/**
 *
 * @author ITACHI
 */
public class ConnectDB {
    Connection conn = null;
    
    public static Connection ConnectDB(){
        try {
            //Load SQL SERVER JDBC Driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            
            //Connect to database
            String url = "jdbc:sqlserver://localhost\\MSSQLSERVER:1433;databaseName=hospital;encrypt=true;TrustServerCertificate=true;";
            String username = "sa";
            String password = "123";
            
            Connection conn = DriverManager.getConnection(url, username, password);
            return conn;
        
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Database Connection Failed: " + e);
            return null;
        }
          
       //
    }
    
    
}
