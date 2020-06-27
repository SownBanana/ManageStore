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
import java.sql.*;
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
        query = query + name + "\"" + ";";
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

    public int getSupplierIdbyName(String name) {
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

    public String getSupplierNameById(int id) {
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

    public void insertProduct(String name, String phoneNumber, String address, String usernameBank, String bankID, String bank) throws SQLException {
//        String query = "BEGIN; "
//                + "INSERT INTO supplier (name, phone_number) "
//                + "VALUES(?,?); "
//                + "SELECT LAST_INSERT_ID() INTO @last_index; "
//                + "INSERT INTO address (ownerid, typeid, provine) "
//                + "VALUES(@last_index, 1, ?) "
//                + "INSERT INTO payment (ownerid, typeid, namebank, bankID, bank) "
//                + "VALUES(@last_index, 1, ?, ?, ?); "
//                + "COMMIT;";

        String query = "INSERT INTO supplier (name, phone_number) "
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
                + "VALUES(?, 1, ?) ";

        statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        statement.setString(2, address);

        statement.executeUpdate();

        query = "INSERT INTO payment (ownerid, typeid, namebank, bankID, bank)\n"
                + "VALUES(?, 1, ?, ?, ?);";

        statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        statement.setString(2, usernameBank);
        statement.setString(3, bankID);
        statement.setString(4, bank);

        statement.executeUpdate();

    }

    public int removeSupplier(int supplierid) throws Exception {
        System.out.println("Xoá");
        int rs = -1;
        String query = "SELECT COUNT(invoiceid) as num_of_invoice FROM store_manager.invoice "
                + "WHERE type = 1 AND ownerid = ";
        query += supplierid + " GROUP BY ownerid";
        
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
            query = "DELETE FROM store_manager.supplier WHERE supplierid = ";
            query += supplierid;

            statement = connection.createStatement();
            rs = statement.executeUpdate(query);
            
            query = "DELETE FROM store_manager.payment WHERE typeid = 1 AND ownerid = ";
            query += supplierid;
        
            statement = connection.createStatement();
            rs = statement.executeUpdate(query);
            
            query = "DELETE FROM store_manager.address WHERE typeid = 1 AND ownerid = ";
            query += supplierid;
        
            statement = connection.createStatement();
            rs = statement.executeUpdate(query);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public int updateSupplier(int id, String name, String phoneNumber, String address, String usernameBank, String bankID, String bank) throws SQLException {
        int rs = -1;
        String query = "UPDATE store_manager.supplier, store_manager.address, store_manager.payment \n"
                + "SET `name` = ?, `phone_number` = ?, `provine` = ?, `namebank` = ?, `bankID` = ?, `bank` = ? \n"
                + "WHERE (`supplierid` = ?) AND address.typeid = 1 AND payment.typeid = 1 \n"
                + "AND address.ownerid = supplier.supplierid\n"
                + "AND payment.ownerid = supplier.supplierid;";

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
