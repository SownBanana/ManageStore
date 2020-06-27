/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sownbanana.connection;

import com.sownbanana.model.Product;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author son.ph173344
 */
public class ProductDAO {

    private Connection connection = EntityManager.connection;

    public List<Product> getAllProducts() {
//        String query = "SELECT * \n"
//                + "FROM (((store_manager.product as p\n"
//                + "INNER JOIN store_manager.supplier as s ON p.supplierid = s.supplierid)\n"
//                + "LEFT JOIN (SELECT * FROM store_manager.address WHERE address.typeid = 1) as addr ON addr.ownerid = s.supplierid)\n"
//                + "LEFT JOIN (SELECT * FROM store_manager.payment WHERE payment.typeid = 1) as pm ON pm.ownerid = s.supplierid)";
        String query = "SELECT * FROM store_manager.product";
        List<Product> list = new ArrayList<Product>();
        Product product = null;
//        Supplier supplier = new Supplier();
//        Address address = new Address();
//        Payment payment = new Payment();
        try {
//            Connection connection = conn.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                product = new Product();
                product.setId(rs.getInt("productid"));
                product.setName(rs.getString("name"));
                product.setCategoryString(rs.getInt("categoryid"));
                product.setSupplierId(rs.getInt("supplierid"));
                product.setType(rs.getString("type"));
                product.setQuantity(rs.getDouble("quantity"));
                product.setUnit(rs.getString("unit"));
                product.setSize(rs.getString("size"));
                product.setImportPrice(rs.getDouble("import_price"));
                product.setRetailPrice(rs.getDouble("retail_price"));
                product.setImportDate(rs.getDate("import_date"));

//                supplier.setId(rs.getInt("supplierid"));
//                supplier.setName(rs.getString(13));
//                supplier.addPhoneNumber(rs.getString("phone_number"));
//
//                address.setId(rs.getInt(15));
//                address.setDetail(rs.getString("detail"));
//                address.setVillage(rs.getString("village"));
//                address.setCommune(rs.getString("commune"));
//                address.setTown(rs.getString("town"));
//                address.setProvine(rs.getString("provine"));
//
//                supplier.addAddress(address);
//
//                payment.setId(rs.getInt(23));
//                payment.setName(rs.getString(26));
//                payment.setBankID(rs.getString("bankID"));
//                payment.setBank(rs.getString("bank"));
//
//                supplier.addPayment(payment);
//
//                product.setSupplier(supplier);
                list.add(product);

            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Product> getAllProductsByKey(String key, String supplier, String category, Date date, boolean isAvailable) {
        String query = "SELECT * FROM store_manager.product as p WHERE (p.name LIKE '%";
        String query2 = query + key + "%' " + "OR p.type LIKE '%" + key + "%')";
        query += key + "%' " + "OR p.type LIKE '%" + key + "%' OR p.import_date LIKE '%" + key + "%')";

        PreparedStatement statement = null;
        if (supplier != null) {
            int supplierid = EntityManager.supplierDAO.getSupplierIdbyName(supplier);
            query += " AND p.supplierid = " + supplierid;
            query2 += " AND p.supplierid = " + supplierid;
        }
        if (category != null) {
            int categoryid = EntityManager.categoryDAO.getCategoryIdbyName(category);
            query += " AND p.categoryid = " + categoryid;
            query2 += " AND p.categoryid = " + categoryid;
        }
        if(isAvailable){
            query += " AND p.quantity > 0";
            query2 += " AND p.quantity > 0";
        }
        if (date != null) {
            query2 += " AND p.import_date <= ?";
            try {
                statement = connection.prepareStatement(query2);
                statement.setDate(1, date);
            } catch (SQLException ex) {
                Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            try {
                statement = connection.prepareStatement(query);
            } catch (SQLException ex) {
                Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        System.out.println(query);
        System.out.println(query2);
        List<Product> list = new ArrayList<Product>();
        Product product = null;
//        Supplier supplier = new Supplier();
//        Address address = new Address();
//        Payment payment = new Payment();
        try {
//            Connection connection = conn.getConnection();
//            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                product = new Product();
                product.setId(rs.getInt("productid"));
                product.setName(rs.getString("name"));
                product.setCategoryString(rs.getInt("categoryid"));
                product.setSupplierId(rs.getInt("supplierid"));
                product.setType(rs.getString("type"));
                product.setQuantity(rs.getDouble("quantity"));
                product.setUnit(rs.getString("unit"));
                product.setSize(rs.getString("size"));
                product.setImportPrice(rs.getDouble("import_price"));
                product.setRetailPrice(rs.getDouble("retail_price"));
                product.setImportDate(rs.getDate("import_date"));

                list.add(product);

            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
            try {
//            Connection connection = conn.getConnection();
                Statement statement2 = connection.createStatement();
                ResultSet rs = statement2.executeQuery(query2);
                while (rs.next()) {
                    product = new Product();
                    product.setId(rs.getInt("productid"));
                    product.setName(rs.getString("name"));
                    product.setCategoryString(rs.getInt("categoryid"));
                    product.setSupplierId(rs.getInt("supplierid"));
                    product.setType(rs.getString("type"));
                    product.setQuantity(rs.getDouble("quantity"));
                    product.setUnit(rs.getString("unit"));
                    product.setSize(rs.getString("size"));
                    product.setImportPrice(rs.getDouble("import_price"));
                    product.setRetailPrice(rs.getDouble("retail_price"));
                    product.setImportDate(rs.getDate("import_date"));

                    list.add(product);

                }
                rs.close();

            } catch (SQLException ex) {
                ex.printStackTrace();

            }
        }
        return list;
    }

    public Product getProduct(int id) {
//        String query = "SELECT * \n"
//                + "FROM (((store_manager.product as p\n"
//                + "INNER JOIN store_manager.supplier as s ON p.supplierid = s.supplierid)\n"
//                + "LEFT JOIN (SELECT * FROM store_manager.address WHERE address.typeid = 1) as addr ON addr.ownerid = s.supplierid)\n"
//                + "LEFT JOIN (SELECT * FROM store_manager.payment WHERE payment.typeid = 1) as pm ON pm.ownerid = s.supplierid) "
//                + "WHERE p.productid = ";
        String query = "SELECT * FROM store_manager.product as p WHERE p.productid = ";
        query = query + id + ";";
        Product product = new Product();
//        Supplier supplier = new Supplier();
//        Address address = new Address();
//        Payment payment = new Payment();
        try {
//            Connection connection = conn.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            product.setId(rs.getInt("productid"));
            product.setName(rs.getString("name"));
            product.setCategoryString(rs.getInt("categoryid"));
            product.setSupplierId(rs.getInt("supplierid"));
            product.setType(rs.getString("type"));
            product.setQuantity(rs.getDouble("quantity"));
            product.setUnit(rs.getString("unit"));
            product.setSize(rs.getString("size"));
            product.setImportPrice(rs.getDouble("import_price"));
            product.setRetailPrice(rs.getDouble("retail_price"));
            product.setImportDate(rs.getDate("import_date"));

//            supplier.setId(rs.getInt("supplierid"));
//            supplier.setName(rs.getString(13));
//            supplier.addPhoneNumber(rs.getString("phone_number"));
//
//            address.setId(rs.getInt(15));
//            address.setDetail(rs.getString("detail"));
//            address.setVillage(rs.getString("village"));
//            address.setCommune(rs.getString("commune"));
//            address.setTown(rs.getString("town"));
//            address.setProvine(rs.getString("provine"));
//
//            supplier.addAddress(address);
//
//            payment.setId(rs.getInt(23));
//            payment.setName(rs.getString(26));
//            payment.setBankID(rs.getString("bankID"));
//            payment.setBank(rs.getString("bank"));
//
//            supplier.addPayment(payment);
//
//            product.setSupplier(supplier);
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    public List<Product> getAllProductsByCategory(int categoryid) {
//        String query = "SELECT * \n"
//                + "FROM (((store_manager.product as p\n"
//                + "INNER JOIN store_manager.supplier as s ON p.supplierid = s.supplierid)\n"
//                + "LEFT JOIN (SELECT * FROM store_manager.address WHERE address.typeid = 1) as addr ON addr.ownerid = s.supplierid)\n"
//                + "LEFT JOIN (SELECT * FROM store_manager.payment WHERE payment.typeid = 1) as pm ON pm.ownerid = s.supplierid)"
//                + "WHERE p.categoryid = ";
//        
        String query = "SELECT * FROM store_manager.product as p WHERE p.categoryid = ";
        query += categoryid;
        List<Product> list = new ArrayList<Product>();
        Product product = null;
//        Supplier supplier = new Supplier();
//        Address address = new Address();
//        Payment payment = new Payment();
        try {
//            Connection connection = conn.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                product = new Product();
                product.setId(rs.getInt("productid"));
                product.setName(rs.getString("name"));
                product.setCategoryString(rs.getInt("categoryid"));
                product.setSupplierId(rs.getInt("supplierid"));
                product.setType(rs.getString("type"));
                product.setQuantity(rs.getDouble("quantity"));
                product.setUnit(rs.getString("unit"));
                product.setSize(rs.getString("size"));
                product.setImportPrice(rs.getDouble("import_price"));
                product.setRetailPrice(rs.getDouble("retail_price"));
                product.setImportDate(rs.getDate("import_date"));

//                supplier.setId(rs.getInt("supplierid"));
//                supplier.setName(rs.getString(13));
//                supplier.addPhoneNumber(rs.getString("phone_number"));
//
//                address.setId(rs.getInt(15));
//                address.setDetail(rs.getString("detail"));
//                address.setVillage(rs.getString("village"));
//                address.setCommune(rs.getString("commune"));
//                address.setTown(rs.getString("town"));
//                address.setProvine(rs.getString("provine"));
//
//                supplier.addAddress(address);
//
//                payment.setId(rs.getInt(23));
//                payment.setName(rs.getString(26));
//                payment.setBankID(rs.getString("bankID"));
//                payment.setBank(rs.getString("bank"));
//
//                supplier.addPayment(payment);
//
//                product.setSupplier(supplier);
                list.add(product);

            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Product> getAllProductsByCategoryAndType(int categoryid, String type) {
//        String query = "SELECT * \n"
//                + "FROM (((store_manager.product as p\n"
//                + "INNER JOIN store_manager.supplier as s ON p.supplierid = s.supplierid)\n"
//                + "LEFT JOIN (SELECT * FROM store_manager.address WHERE address.typeid = 1) as addr ON addr.ownerid = s.supplierid)\n"
//                + "LEFT JOIN (SELECT * FROM store_manager.payment WHERE payment.typeid = 1) as pm ON pm.ownerid = s.supplierid)"
//                + "WHERE p.categoryid = ";
        String query = "SELECT * FROM store_manager.product as p WHERE p.categoryid = ";
        query += categoryid;
        query += " AND p.type = \"" + type + "\"";
        List<Product> list = new ArrayList<Product>();
        Product product = null;
//        Supplier supplier = new Supplier();
//        Address address = new Address();
//        Payment payment = new Payment();
        try {
//            Connection connection = conn.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                product = new Product();
                product.setId(rs.getInt("productid"));
                product.setName(rs.getString("name"));
                product.setCategoryString(rs.getInt("categoryid"));
                product.setSupplierId(rs.getInt("supplierid"));
                product.setType(rs.getString("type"));
                product.setQuantity(rs.getDouble("quantity"));
                product.setUnit(rs.getString("unit"));
                product.setSize(rs.getString("size"));
                product.setImportPrice(rs.getDouble("import_price"));
                product.setRetailPrice(rs.getDouble("retail_price"));
                product.setImportDate(rs.getDate("import_date"));

//                supplier.setId(rs.getInt("supplierid"));
//                supplier.setName(rs.getString(13));
//                supplier.addPhoneNumber(rs.getString("phone_number"));
//
//                address.setId(rs.getInt(15));
//                address.setDetail(rs.getString("detail"));
//                address.setVillage(rs.getString("village"));
//                address.setCommune(rs.getString("commune"));
//                address.setTown(rs.getString("town"));
//                address.setProvine(rs.getString("provine"));
//
//                supplier.addAddress(address);
//
//                payment.setId(rs.getInt(23));
//                payment.setName(rs.getString(26));
//                payment.setBankID(rs.getString("bankID"));
//                payment.setBank(rs.getString("bank"));
//
//                supplier.addPayment(payment);
//
//                product.setSupplier(supplier);
                list.add(product);

            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Product getProductDetail(int id) {
//        String query = "SELECT * \n"
//                + "FROM (((store_manager.product as p\n"
//                + "INNER JOIN store_manager.supplier as s ON p.supplierid = s.supplierid)\n"
//                + "LEFT JOIN (SELECT * FROM store_manager.address WHERE address.typeid = 1) as addr ON addr.ownerid = s.supplierid)\n"
//                + "LEFT JOIN (SELECT * FROM store_manager.payment WHERE payment.typeid = 1) as pm ON pm.ownerid = s.supplierid)"
//                + "WHERE p.productid = ";
        String query = "SELECT * FROM store_manager.product as p WHERE p.productid = ";
        query += id;
        Product product = null;
//        Supplier supplier = new Supplier();
//        Address address = new Address();
//        Payment payment = new Payment();
        try {
//            Connection connection = conn.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            product = new Product();
            product.setId(rs.getInt("productid"));
            product.setName(rs.getString("name"));
            product.setCategoryString(rs.getInt("categoryid"));
            product.setSupplierId(rs.getInt("supplierid"));
            product.setType(rs.getString("type"));
            product.setQuantity(rs.getDouble("quantity"));
            product.setUnit(rs.getString("unit"));
            product.setSize(rs.getString("size"));
            product.setImportPrice(rs.getDouble("import_price"));
            product.setRetailPrice(rs.getDouble("retail_price"));
            product.setImportDate(rs.getDate("import_date"));

//            supplier.setId(rs.getInt("supplierid"));
//            supplier.setName(rs.getString(13));
//            supplier.addPhoneNumber(rs.getString("phone_number"));
//
//            address.setId(rs.getInt(15));
//            address.setDetail(rs.getString("detail"));
//            address.setVillage(rs.getString("village"));
//            address.setCommune(rs.getString("commune"));
//            address.setTown(rs.getString("town"));
//            address.setProvine(rs.getString("provine"));
//
//            supplier.addAddress(address);
//
//            payment.setId(rs.getInt(23));
//            payment.setName(rs.getString(26));
//            payment.setBankID(rs.getString("bankID"));
//            payment.setBank(rs.getString("bank"));
//
//            supplier.addPayment(payment);
//
//            product.setSupplier(supplier);
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    public boolean insertProduct(Product product) {
        boolean rs = true;
        System.out.println("Thêm");
        return rs;
    }

    public int insertProduct(String name, String category, String type, double quantity, String unit, String size, int supplier, double importPrice, double retailPrice, Date importDate) {
        int rs = -1;
        System.out.println("Thêm product!!");
        String query = "INSERT INTO `store_manager`.`product` "
                + "(`name`, `categoryid`, `supplierid`, `type`, `quantity`, `unit`, `import_price`, `retail_price`, `import_date`)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setInt(2, EntityManager.categoryDAO.getCategoryIdbyName(category));
            statement.setInt(3, supplier);
            statement.setString(4, type);
            statement.setDouble(5, quantity);
            statement.setString(6, unit);
            statement.setDouble(7, importPrice);
            statement.setDouble(8, retailPrice);
            statement.setDate(9, importDate);

            rs = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public int removeProduct(Product product) {
        System.out.println("Xoá");
        int rs = -1;
        String query = "DELETE FROM store_manager.product WHERE productid = ";
        query += product.getId();

        try {
            Statement statement = connection.createStatement();
            rs = statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public int updateProduct(int id, String name, String category, String type, double quantity, String unit, String size, int supplier, double importPrice, double retailPrice, Date importDate) {
        int rs = -1;
        System.out.println("Update product!!");
        String query = "UPDATE `store_manager`.`product` SET "
                + "`name` = ?, `categoryid` = ?, `supplierid` = ?, `type` = ?, `quantity` = ?, `unit` = ?, `import_price` = ?, `retail_price` = ?, `import_date` = ? WHERE (`productid` = ?);";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setInt(2, EntityManager.categoryDAO.getCategoryIdbyName(category));
            statement.setInt(3, supplier);
            statement.setString(4, type);
            statement.setDouble(5, quantity);
            statement.setString(6, unit);
            statement.setDouble(7, importPrice);
            statement.setDouble(8, retailPrice);
            statement.setDate(9, importDate);
            statement.setInt(10, id);

            rs = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
}
