/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sownbanana.connection;

import com.sownbanana.model.Address;
import com.sownbanana.model.FriendAgency;
import com.sownbanana.model.Payment;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author son.ph173344
 */
public class FriendAgencyDAO {
    private Connection connection = EntityManager.connection;

    public List<FriendAgency> getAllFriendAgencies() {
        String query = "SELECT * \n"
                + "FROM ((store_manager.supplier as s \n"
                + "LEFT JOIN (SELECT * FROM store_manager.address WHERE address.typeid = 1) as addr ON addr.ownerid = s.supplierid)\n"
                + "LEFT JOIN (SELECT * FROM store_manager.payment WHERE payment.typeid = 1) as pm ON pm.ownerid = s.supplierid)";

        List<FriendAgency> list = new ArrayList<FriendAgency>();
        FriendAgency friendAgency = new FriendAgency();
        Address address = new Address();
        Payment payment = new Payment();
        try {
//            Connection connection = conn.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                friendAgency.setId(rs.getInt("customerid"));
                friendAgency.setName(rs.getString(2));
                friendAgency.addPhoneNumber(rs.getString("phone_number"));

                address.setId(rs.getInt(5));
                address.setDetail(rs.getString("detail"));
                address.setVillage(rs.getString("village"));
                address.setCommune(rs.getString("commune"));
                address.setTown(rs.getString("town"));
                address.setProvine(rs.getString("provine"));

                friendAgency.addAddress(address);

                payment.setId(rs.getInt(13));
                payment.setName(rs.getString(16));
                payment.setBankID(rs.getString("bankID"));
                payment.setBank(rs.getString("bank"));

                friendAgency.addPayment(payment);

                list.add(friendAgency);

            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public FriendAgency getFriendAgency(int id) {
        String query = "SELECT * \n"
                + "FROM ((store_manager.supplier as s \n"
                + "LEFT JOIN (SELECT * FROM store_manager.address WHERE address.typeid = 1) as addr ON addr.ownerid = s.supplierid)\n"
                + "LEFT JOIN (SELECT * FROM store_manager.payment WHERE payment.typeid = 1) as pm ON pm.ownerid = s.supplierid)\n"
                + "WHERE s.supplierid = ";
        query = query + id + ";";
        FriendAgency friendAgency = new FriendAgency();
        Address address = new Address();
        Payment payment = new Payment();
        try {
//            Connection connection = conn.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            friendAgency.setId(rs.getInt("supplierid"));
            friendAgency.setName(rs.getString(2));
            friendAgency.addPhoneNumber(rs.getString("phone_number"));

            address.setId(rs.getInt(5));
            address.setDetail(rs.getString("detail"));
            address.setVillage(rs.getString("village"));
            address.setCommune(rs.getString("commune"));
            address.setTown(rs.getString("town"));
            address.setProvine(rs.getString("provine"));

            friendAgency.addAddress(address);

            payment.setId(rs.getInt(13));
            payment.setName(rs.getString(16));
            payment.setBankID(rs.getString("bankID"));
            payment.setBank(rs.getString("bank"));

            friendAgency.addPayment(payment);

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friendAgency;
    }

    public FriendAgency getFriendAgency(String name) {
        String query = "SELECT * \n"
                + "FROM ((store_manager.supplier as s \n"
                + "LEFT JOIN (SELECT * FROM store_manager.address WHERE address.typeid = 1) as addr ON addr.ownerid = s.supplierid)\n"
                + "LEFT JOIN (SELECT * FROM store_manager.payment WHERE payment.typeid = 1) as pm ON pm.ownerid = s.supplierid)\n"
                + "WHERE s.name = \"";
        query = query  + name + "\"" + ";";
        FriendAgency friendAgency = new FriendAgency();
        Address address = new Address();
        Payment payment = new Payment();
        try {
//            Connection connection = conn.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            friendAgency.setId(rs.getInt("supplierid"));
            friendAgency.setName(rs.getString(2));
            friendAgency.addPhoneNumber(rs.getString("phone_number"));

            address.setId(rs.getInt(5));
            address.setDetail(rs.getString("detail"));
            address.setVillage(rs.getString("village"));
            address.setCommune(rs.getString("commune"));
            address.setTown(rs.getString("town"));
            address.setProvine(rs.getString("provine"));

            friendAgency.addAddress(address);

            payment.setId(rs.getInt(13));
            payment.setName(rs.getString(16));
            payment.setBankID(rs.getString("bankID"));
            payment.setBank(rs.getString("bank"));

            friendAgency.addPayment(payment);

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friendAgency;
    }
}
