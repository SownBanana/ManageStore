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
                + "FROM ((store_manager.supplier as s \n"
                + "LEFT JOIN (SELECT * FROM store_manager.address WHERE address.typeid = 1) as addr ON addr.ownerid = s.supplierid)\n"
                + "LEFT JOIN (SELECT * FROM store_manager.payment WHERE payment.typeid = 1) as pm ON pm.ownerid = s.supplierid)";

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
                + "FROM ((store_manager.supplier as s \n"
                + "LEFT JOIN (SELECT * FROM store_manager.address WHERE address.typeid = 1) as addr ON addr.ownerid = s.supplierid)\n"
                + "LEFT JOIN (SELECT * FROM store_manager.payment WHERE payment.typeid = 1) as pm ON pm.ownerid = s.supplierid)\n"
                + "WHERE s.supplierid = ";
        query = query + id + ";";
        Customer customer = new Customer();
        Address address = new Address();
        Payment payment = new Payment();
        try {
//            Connection connection = conn.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            customer.setId(rs.getInt("supplierid"));
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
                + "FROM ((store_manager.supplier as s \n"
                + "LEFT JOIN (SELECT * FROM store_manager.address WHERE address.typeid = 1) as addr ON addr.ownerid = s.supplierid)\n"
                + "LEFT JOIN (SELECT * FROM store_manager.payment WHERE payment.typeid = 1) as pm ON pm.ownerid = s.supplierid)\n"
                + "WHERE s.name = \"";
        query = query  + name + "\"" + ";";
        Customer customer = new Customer();
        Address address = new Address();
        Payment payment = new Payment();
        try {
//            Connection connection = conn.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            customer.setId(rs.getInt("supplierid"));
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
}
