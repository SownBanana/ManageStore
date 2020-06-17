/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sownbanana.connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author son.ph173344
 */
public class CategoryDAO {
    Connection connection = EntityManager.connection;
    public List<String> getCategoryName() {
        String query = "SELECT c.name \n"
                + "FROM store_manager.category as c \n";
        List<String> list = new ArrayList<>();
        try {
//            Connection connection = conn.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                String item = rs.getString("name");
                list.add(item);
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public int getCategoryIdbyName(String name){
        String query = "SELECT c.categoryid \n"
                + "FROM store_manager.category as c \n"
                + "WHERE c.name = '";
        query += name + "'";
        int id = 0;
        try {
//            Connection connection = conn.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                id = rs.getInt("categoryid");
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
     public String getCategoryNameById(int id){
        String query = "SELECT c.name \n"
                + "FROM store_manager.category as c \n"
                + "WHERE c.categoryid = ";
        query += id;
        String name = null;
        try {
//            Connection connection = conn.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                name = rs.getString("name");
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }
    public int insertCategory(String name) {
        int rs = -1;
        System.out.println("Thêm category!!");
        String query = "INSERT INTO `store_manager`.`category` "
                + "(`name`)"
                + " VALUES (?);";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);

            rs = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
}
