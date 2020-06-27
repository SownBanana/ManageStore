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
public class FriendAgencyDAO {

    private Connection connection = EntityManager.connection;

    public List<FriendAgency> getAllFriendAgencies() {
        String query = "SELECT * \n"
                + "FROM ((store_manager.friend_agency as s \n"
                + "LEFT JOIN (SELECT * FROM store_manager.address WHERE address.typeid = 3) as addr ON addr.ownerid = s.friend_agencyid)\n"
                + "LEFT JOIN (SELECT * FROM store_manager.payment WHERE payment.typeid = 3) as pm ON pm.ownerid = s.friend_agencyid)";

        List<FriendAgency> list = new ArrayList<FriendAgency>();
        FriendAgency friendAgency = new FriendAgency();
        Address address = new Address();
        Payment payment = new Payment();
        try {
//            Connection connection = conn.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                friendAgency.setId(rs.getInt("friend_agencyid"));
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
                + "FROM ((store_manager.friend_agency as s \n"
                + "LEFT JOIN (SELECT * FROM store_manager.address WHERE address.typeid = 3) as addr ON addr.ownerid = s.friend_agencyid)\n"
                + "LEFT JOIN (SELECT * FROM store_manager.payment WHERE payment.typeid = 3) as pm ON pm.ownerid = s.friend_agencyid)\n"
                + "WHERE s.friend_agencyid = ";
        query = query + id + ";";
        FriendAgency friendAgency = new FriendAgency();
        Address address = new Address();
        Payment payment = new Payment();
        try {
//            Connection connection = conn.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            friendAgency.setId(rs.getInt("friend_agencyid"));
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
                + "FROM ((store_manager.friend_agency as s \n"
                + "LEFT JOIN (SELECT * FROM store_manager.address WHERE address.typeid = 3) as addr ON addr.ownerid = s.friend_agencyid)\n"
                + "LEFT JOIN (SELECT * FROM store_manager.payment WHERE payment.typeid = 3) as pm ON pm.ownerid = s.friend_agencyid)\n"
                + "WHERE s.name = \"";
        query = query + name + "\"" + ";";
        FriendAgency friendAgency = new FriendAgency();
        Address address = new Address();
        Payment payment = new Payment();
        try {
//            Connection connection = conn.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            friendAgency.setId(rs.getInt("friend_agencyid"));
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

    public int getFriendIdbyName(String name) {
        String query = "SELECT s.friend_agencyid \n"
                + "FROM store_manager.friend_agency as s \n"
                + "WHERE s.name = '";
        query += name + "'";
        int id = -1;
        try {
//            Connection connection = conn.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                id = rs.getInt("friend_agencyid");
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public String getFriendNameById(int id) {
        String query = "SELECT s.name \n"
                + "FROM store_manager.friend_agency as s \n"
                + "WHERE s.friend_agencyid = ";
        query += id;
        String name = null;
        try {
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

    public List<String> getFriendAgencysName() {
        String query = "SELECT f.name \n"
                + "FROM store_manager.friend_agency as f \n";
        List<String> list = new ArrayList<>();
        try {
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

    public void insertFriend(String name, String phoneNumber, String address, String usernameBank, String bankID, String bank) throws SQLException {

        String query = "INSERT INTO friend_agency (name, phone_number) "
                + "VALUES(?,?); ";

        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, name);
        statement.setString(2, phoneNumber);

        statement.executeUpdate();

        int id = 0;
        ResultSet rs = statement.getGeneratedKeys();
        if (rs.next()) {
            id = rs.getInt(1);
        }

        query = "INSERT INTO address (ownerid, typeid, provine) "
                + "VALUES(?, 3, ?) ";

        statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        statement.setString(2, address);

        statement.executeUpdate();

        query = "INSERT INTO payment (ownerid, typeid, namebank, bankID, bank)\n"
                + "VALUES(?, 3, ?, ?, ?);";

        statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        statement.setString(2, usernameBank);
        statement.setString(3, bankID);
        statement.setString(4, bank);

        statement.executeUpdate();

    }

    public int removeFriend(int friend_agencyid) throws Exception {
        System.out.println("Xoá");
        int rs = -1;
        String query = "SELECT COUNT(invoiceid) as num_of_invoice FROM store_manager.invoice "
                + "WHERE type = 3 AND ownerid = ";
        query += friend_agencyid + " GROUP BY ownerid";

        try {
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(query);
            if (set.next()) {
                int count_invoice = set.getInt(1);
                System.out.println("Invoice number: " + count_invoice);
                if(count_invoice > 0) throw new Exception("Supplier have Invoice");
            }
            else{
                System.out.println("Invoice number = 0, delete");
            }
            
            query = "DELETE FROM store_manager.friend_agency WHERE friend_agencyid = ";
            query += friend_agencyid;
            statement = connection.createStatement();
            rs = statement.executeUpdate(query);
            query = "DELETE FROM store_manager.payment WHERE typeid = 3 AND ownerid = ";
            query += friend_agencyid;

            statement = connection.createStatement();
            rs = statement.executeUpdate(query);

            query = "DELETE FROM store_manager.address WHERE typeid = 3 AND ownerid = ";
            query += friend_agencyid;

            statement = connection.createStatement();
            rs = statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public int updateFriend(int id, String name, String phoneNumber, String address, String usernameBank, String bankID, String bank) throws SQLException {
        int rs = -1;
        String query = "UPDATE store_manager.friend_agency, store_manager.address, store_manager.payment \n"
                + "SET `name` = ?, `phone_number` = ?, `provine` = ?, `namebank` = ?, `bankID` = ?, `bank` = ? \n"
                + "WHERE (`friend_agencyid` = ?) AND address.typeid = 3 AND payment.typeid = 3 \n"
                + "AND address.ownerid = friend_agency.friend_agencyid\n"
                + "AND payment.ownerid = friend_agency.friend_agencyid;";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, name);
        statement.setString(2, phoneNumber);
        statement.setString(3, address);
        statement.setString(4, usernameBank);
        statement.setString(5, bankID);
        statement.setString(6, bank);
        statement.setInt(7, id);

        rs = statement.executeUpdate();

        return rs;
    }
}
