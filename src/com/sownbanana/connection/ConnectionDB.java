/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sownbanana.connection;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;


/**
 *
 * @author son.ph173344
 */
public class ConnectionDB {
    private static String DB_URL = "jdbc:mysql://localhost:3306/store_manager?useSSL=false";
    private static String USER_NAME = "root";
    private static String PASSWORD = "root";
    
     public static Connection getConnection(String dbURL, String userName, 
            String password) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = (Connection) DriverManager.getConnection(dbURL, userName, password);
            System.out.println("connect successfully!");
        } catch (Exception ex) {
            System.out.println("connect failure!");
            ex.printStackTrace();
        }
        return conn;
    }
     public static Connection getConnection() {
        return getConnection(DB_URL, USER_NAME, PASSWORD);
    }
//     public static void main(String[] args) {
//        try {
//            // connnect to database 'testdb'
//            Connection conn = getConnection(DB_URL, USER_NAME, PASSWORD);
//            // crate statement
//            Statement stmt = conn.createStatement();
//            // get data from table 'student'
//            ResultSet rs = stmt.executeQuery("select * from product");
//            // show data
//            while (rs.next()) {
//                System.out.println(rs.getInt(1) + "  " + rs.getString(2) 
//                        + "  " + rs.getInt(3) + "  " +rs.getInt(4) + "  " +rs.getString(5));
//            }
//            // close connection
//            conn.close();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
}
