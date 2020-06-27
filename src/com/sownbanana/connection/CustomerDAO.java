/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sownbanana.connection;

import com.sownbanana.model.Address;
import com.sownbanana.model.Payment;
import com.sownbanana.model.Customer;
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
public class CustomerDAO {

    private Connection connection = EntityManager.connection;

    public List<Customer> getAllCustomers() {
        String query = "SELECT * \n"
                + "FROM ((store_manager.customer as s \n"
                + "LEFT JOIN (SELECT * FROM store_manager.address WHERE address.typeid = 2) as addr ON addr.ownerid = s.customerid)\n"
                + "LEFT JOIN (SELECT * FROM store_manager.payment WHERE payment.typeid = 2) as pm ON pm.ownerid = s.customerid)";

        List<Customer> list = new ArrayList<Customer>();
        Customer customer = new Customer();
        Address address = new Address();
        Payment payment = new Payment();
        try {
//            Connection connection = conn.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                customer.setId(rs.getInt("customerid"));
                customer.setName(rs.getString(2));
                customer.addPhoneNumber(rs.getString("phone_number"));

                address.setId(rs.getInt(4));
                address.setDetail(rs.getString("detail"));
                address.setVillage(rs.getString("village"));
                address.setCommune(rs.getString("commune"));
                address.setTown(rs.getString("town"));
                address.setProvine(rs.getString("provine"));

                customer.addAddress(address);

                payment.setId(rs.getInt(12));
                payment.setName(rs.getString(15));
                payment.setBankID(rs.getString("bankID"));
                payment.setBank(rs.getString("bank"));

                customer.addPayment(payment);

                list.add(customer);

            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Customer getCustomer(int id) {
        String query = "SELECT * \n"
                + "FROM ((store_manager.customer as s \n"
                + "LEFT JOIN (SELECT * FROM store_manager.address WHERE address.typeid = 2) as addr ON addr.ownerid = s.customerid)\n"
                + "LEFT JOIN (SELECT * FROM store_manager.payment WHERE payment.typeid = 2) as pm ON pm.ownerid = s.customerid)\n"
                + "WHERE s.customerid = ";
        query = query + id + ";";
        Customer customer = new Customer();
        Address address = new Address();
        Payment payment = new Payment();
        try {
//            Connection connection = conn.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            customer.setId(rs.getInt("customerid"));
            customer.setName(rs.getString(2));
            customer.addPhoneNumber(rs.getString("phone_number"));

            address.setId(rs.getInt(4));
            address.setDetail(rs.getString("detail"));
            address.setVillage(rs.getString("village"));
            address.setCommune(rs.getString("commune"));
            address.setTown(rs.getString("town"));
            address.setProvine(rs.getString("provine"));

            customer.addAddress(address);

            payment.setId(rs.getInt(12));
            payment.setName(rs.getString(15));
            payment.setBankID(rs.getString("bankID"));
            payment.setBank(rs.getString("bank"));

            customer.addPayment(payment);

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public Customer getCustomer(String name) {
        String query = "SELECT * \n"
                + "FROM ((store_manager.customer as s \n"
                + "LEFT JOIN (SELECT * FROM store_manager.address WHERE address.typeid = 2) as addr ON addr.ownerid = s.customerid)\n"
                + "LEFT JOIN (SELECT * FROM store_manager.payment WHERE payment.typeid = 2) as pm ON pm.ownerid = s.customerid)\n"
                + "WHERE s.name = ?";
        Customer customer = new Customer();
        Address address = new Address();
        Payment payment = new Payment();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            rs.next();
            customer.setId(rs.getInt("customerid"));
            customer.setName(rs.getString(2));
            customer.addPhoneNumber(rs.getString("phone_number"));

            address.setId(rs.getInt(4));
            address.setDetail(rs.getString("detail"));
            address.setVillage(rs.getString("village"));
            address.setCommune(rs.getString("commune"));
            address.setTown(rs.getString("town"));
            address.setProvine(rs.getString("provine"));

            customer.addAddress(address);

            payment.setId(rs.getInt(12));
            payment.setName(rs.getString(15));
            payment.setBankID(rs.getString("bankID"));
            payment.setBank(rs.getString("bank"));

            customer.addPayment(payment);

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public int getCustomerIdbyName(String name) {
        String query = "SELECT s.customerid \n"
                + "FROM store_manager.customer as s \n"
                + "WHERE s.name = ?";
        int id = -1;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                id = rs.getInt("customerid");
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public String getCustomerNameById(int id) {
        String query = "SELECT s.name \n"
                + "FROM store_manager.customer as s \n"
                + "WHERE s.customerid = ";
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

    public List<String> getCustomersName() {
        String query = "SELECT c.name \n"
                + "FROM store_manager.customer as c \n";
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

    public void insertCustomer(String name, String phoneNumber, String address, String usernameBank, String bankID, String bank) throws SQLException {

        String query = "INSERT INTO customer (name, phone_number) "
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
                + "VALUES(?, 2, ?) ";

        statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        statement.setString(2, address);

        statement.executeUpdate();

        query = "INSERT INTO payment (ownerid, typeid, namebank, bankID, bank)\n"
                + "VALUES(?, 2, ?, ?, ?);";

        statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        statement.setString(2, usernameBank);
        statement.setString(3, bankID);
        statement.setString(4, bank);

        statement.executeUpdate();

    }

    public int removeCustomer(int customerid) throws Exception {
        System.out.println("Xoá");
        int rs = -1;
        String query = "SELECT COUNT(invoiceid) as num_of_invoice FROM store_manager.invoice "
                + "WHERE type = 2 AND ownerid = ";
        query += customerid + " GROUP BY ownerid";

        try {
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(query);
            if (set.next()) {
                int count_invoice = set.getInt(1);
                System.out.println("Invoice number: " + count_invoice);
                if(count_invoice > 0) throw new Exception("Customer have Invoice");
            }
            else{
                System.out.println("Invoice number = 0, delete");
            }
            
            query = "DELETE FROM store_manager.customer WHERE customerid = ";
            query += customerid;

            statement = connection.createStatement();
            rs = statement.executeUpdate(query);

            query = "DELETE FROM store_manager.payment WHERE typeid = 2 AND ownerid = ";
            query += customerid;

            statement = connection.createStatement();
            rs = statement.executeUpdate(query);

            query = "DELETE FROM store_manager.address WHERE typeid = 2 AND ownerid = ";
            query += customerid;

            statement = connection.createStatement();
            rs = statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public int updateCustomer(int id, String name, String phoneNumber, String address, String usernameBank, String bankID, String bank) throws SQLException {
        int rs = -1;
        String query = "UPDATE store_manager.customer, store_manager.address, store_manager.payment \n"
                + "SET `name` = ?, `phone_number` = ?, `provine` = ?, `namebank` = ?, `bankID` = ?, `bank` = ? \n"
                + "WHERE (`customerid` = ?) AND address.typeid = 2 AND payment.typeid = 2 \n"
                + "AND address.ownerid = customer.customerid\n"
                + "AND payment.ownerid = customer.customerid;";

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
