/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sownbanana.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author son.ph173344
 */
public class UnitDAO {
    Connection connection = EntityManager.connection;
    public List<String> getUnits() {
        String query = "SELECT u.name \n"
                + "FROM store_manager.unit as u \n";
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
    public int insertUnit(String name) {
        int rs = -1;
        System.out.println("Thêm unit!!");
        String query = "INSERT INTO `store_manager`.`unit` "
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
