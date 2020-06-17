/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sownbanana.connection;

import com.sownbanana.model.Address;
import com.sownbanana.model.Payment;
import com.sownbanana.model.Product;
import com.sownbanana.model.Supplier;
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
public class SupplierDAO {

    private Connection connection = EntityManager.connection;

    public List<Supplier> getAllSuppliers() {
        String query = "SELECT * \n"
                + "FROM ((store_manager.supplier as s \n"
                + "LEFT JOIN (SELECT * FROM store_manager.address WHERE address.typeid = 1) as addr ON addr.ownerid = s.supplierid)\n"
                + "LEFT JOIN (SELECT * FROM store_manager.payment WHERE payment.typeid = 1) as pm ON pm.ownerid = s.supplierid)";

        List<Supplier> list = new ArrayList<Supplier>();
        Supplier supplier = new Supplier();
        Address address = new Address();
        Payment payment = new Payment();
        try {
//            Connection connection = conn.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                supplier.setId(rs.getInt("supplierid"));
                supplier.setName(rs.getString(2));
                supplier.addPhoneNumber(rs.getString("phone_number"));

                address.setId(rs.getInt(4));
                address.setDetail(rs.getString("detail"));
                address.setVillage(rs.getString("village"));
                address.setCommune(rs.getString("commune"));
                address.setTown(rs.getString("town"));
                address.setProvine(rs.getString("provine"));

                supplier.addAddress(address);

                payment.setId(rs.getInt(12));
                payment.setName(rs.getString(15));
                payment.setBankID(rs.getString("bankID"));
                payment.setBank(rs.getString("bank"));

                supplier.addPayment(payment);

                list.add(supplier);

            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Supplier getSupplier(int id) {
        String query = "SELECT * \n"
                + "FROM ((store_manager.supplier as s \n"
                + "LEFT JOIN (SELECT * FROM store_manager.address WHERE address.typeid = 1) as addr ON addr.ownerid = s.supplierid)\n"
                + "LEFT JOIN (SELECT * FROM store_manager.payment WHERE payment.typeid = 1) as pm ON pm.ownerid = s.supplierid)\n"
                + "WHERE s.supplierid = ";
        query = query + id + ";";
        Supplier supplier = new Supplier();
        Address address = new Address();
        Payment payment = new Payment();
        try {
//            Connection connection = conn.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            supplier.setId(rs.getInt("supplierid"));
            supplier.setName(rs.getString(2));
            supplier.addPhoneNumber(rs.getString("phone_number"));

            address.setId(rs.getInt(4));
            address.setDetail(rs.getString("detail"));
            address.setVillage(rs.getString("village"));
            address.setCommune(rs.getString("commune"));
            address.setTown(rs.getString("town"));
            address.setProvine(rs.getString("provine"));

            supplier.addAddress(address);

            payment.setId(rs.getInt(12));
            payment.setName(rs.getString(15));
            payment.setBankID(rs.getString("bankID"));
            payment.setBank(rs.getString("bank"));

            supplier.addPayment(payment);

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return supplier;
    }

    public Supplier getSupplier(String name) {
        String query = "SELECT * \n"
                + "FROM ((store_manager.supplier as s \n"
                + "LEFT JOIN (SELECT * FROM store_manager.address WHERE address.typeid = 1) as addr ON addr.ownerid = s.supplierid)\n"
                + "LEFT JOIN (SELECT * FROM store_manager.payment WHERE payment.typeid = 1) as pm ON pm.ownerid = s.supplierid)\n"
                + "WHERE s.name = \"";
        query = query  + name + "\"" + ";";
        Supplier supplier = new Supplier();
        Address address = new Address();
        Payment payment = new Payment();
        try {
//            Connection connection = conn.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            supplier.setId(rs.getInt("supplierid"));
            supplier.setName(rs.getString(2));
            supplier.addPhoneNumber(rs.getString("phone_number"));

            address.setId(rs.getInt(4));
            address.setDetail(rs.getString("detail"));
            address.setVillage(rs.getString("village"));
            address.setCommune(rs.getString("commune"));
            address.setTown(rs.getString("town"));
            address.setProvine(rs.getString("provine"));
            System.out.println(address.toString());
            supplier.addAddress(address);

            payment.setId(rs.getInt(12));
            System.out.println(rs.getInt(12));
            payment.setName(rs.getString(15));
            System.out.println(rs.getString(15));
            payment.setBankID(rs.getString("bankID"));
            payment.setBank(rs.getString("bank"));
            
            System.out.println(payment.toString());
            supplier.addPayment(payment);

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return supplier;
    }
    
    public List<Supplier> getSuppliers(String name) {
        String query = "SELECT * \n"
                + "FROM store_manager.supplier as s \n"
                + "WHERE s.name LIKE \"";
        query += name + "%\"";
        List<Supplier> list = new ArrayList<Supplier>();
        Supplier supplier = new Supplier();

        try {
//            Connection connection = conn.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                supplier.setId(rs.getInt("supplierid"));
                supplier.setName(rs.getString(2));
                supplier.addPhoneNumber(rs.getString("phone_number"));
                list.add(supplier);

            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<String> getSuppliersName() {
        String query = "SELECT s.name \n"
                + "FROM store_manager.supplier as s \n";
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
    
    public int getSupplierIdbyName(String name){
        String query = "SELECT s.supplierid \n"
                + "FROM store_manager.supplier as s \n"
                + "WHERE s.name = '";
        query += name + "'";
        int id = -1;
        try {
//            Connection connection = conn.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                id = rs.getInt("supplierid");
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
    public String getSupplierNameById(int id){
        String query = "SELECT s.name \n"
                + "FROM store_manager.supplier as s \n"
                + "WHERE s.supplierid = ";
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
}
