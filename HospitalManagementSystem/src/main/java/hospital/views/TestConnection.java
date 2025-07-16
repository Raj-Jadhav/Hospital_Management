/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hospital.views;
import hospital.db.ConnectDB;
import java.sql.Connection;

/**
 *
 * @author ITACHI
 */
public class TestConnection {
    public static void main(String[] args){
        Connection conn = ConnectDB.ConnectDB();
        if(conn != null){
            System.out.println("Connected to database successfully!");
        }else{
            System.out.println("Failed to connect!!!");
        }
    }
    
}
