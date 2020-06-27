/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sownbanana.connection;

import com.sownbanana.model.ImportInvoice;
import com.sownbanana.model.Invoice;
import com.sownbanana.model.LoanInvoice;
import com.sownbanana.model.Product;
import com.sownbanana.model.SalesInvoice;
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
public class InvoiceDAO {

    private Connection connection = EntityManager.connection;

    public List<Invoice> getAllInvoices() {
        String query = "SELECT * \n"
                + "FROM store_manager.invoice as i";

        List<Invoice> list = new ArrayList<Invoice>();
        ImportInvoice importInvoice = new ImportInvoice();
        LoanInvoice loanInvoice = new LoanInvoice();
        SalesInvoice salesInvoice = new SalesInvoice();
        try {
//            Connection connection = conn.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                int type = rs.getInt("type");
                if (type == 1) {
                    importInvoice.setId(rs.getInt("invoiceid"));
                    importInvoice.setCreateDate(rs.getDate("create_date"));
                    importInvoice.setReceiveDate(rs.getDate("finish_date"));
                    importInvoice.setNote(rs.getString("note"));
                    list.add(importInvoice);
                } else if (type == 3) {
                    loanInvoice.setId(rs.getInt("invoiceid"));
                    loanInvoice.setCreateDate(rs.getDate("create_date"));
                    loanInvoice.setReturnDate(rs.getDate("finish_date"));
                    loanInvoice.setNote(rs.getString("note"));
                    list.add(loanInvoice);
                } else {
                    salesInvoice.setId(rs.getInt("invoiceid"));
                    salesInvoice.setCreateDate(rs.getDate("create_date"));
                    salesInvoice.setShipDate(rs.getDate("finish_date"));
                    salesInvoice.setNote(rs.getString("note"));
                    list.add(salesInvoice);
                }

            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ImportInvoice> getAllImportInvoices() {
        String query = "SELECT * \n"
                + "FROM store_manager.invoice as i WHERE i.type = 0";

        List<ImportInvoice> list = new ArrayList<ImportInvoice>();
        ImportInvoice importInvoice = new ImportInvoice();
        try {
//            Connection connection = conn.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                importInvoice.setId(rs.getInt("invoiceid"));
                importInvoice.setCreateDate(rs.getDate("create_date"));
                importInvoice.setReceiveDate(rs.getDate("finish_date"));
                importInvoice.setNote(rs.getString("note"));
                list.add(importInvoice);
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<LoanInvoice> getAllLoanInvoices() {
        String query = "SELECT * \n"
                + "FROM store_manager.invoice as i WHERE i.type = 3";

        List<LoanInvoice> list = new ArrayList<LoanInvoice>();
        LoanInvoice loanInvoice = new LoanInvoice();
        try {
//            Connection connection = conn.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                loanInvoice.setId(rs.getInt("invoiceid"));
                loanInvoice.setCreateDate(rs.getDate("create_date"));
                loanInvoice.setReturnDate(rs.getDate("finish_date"));
                loanInvoice.setNote(rs.getString("note"));
                loanInvoice.setPaid(rs.getDouble("paid"));
                list.add(loanInvoice);
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<SalesInvoice> getAllSalesInvoices() {
        String query = "SELECT * \n"
                + "FROM store_manager.invoice as i WHERE i.type = 2";

        List<SalesInvoice> list = new ArrayList<SalesInvoice>();
        SalesInvoice salesInvoice = new SalesInvoice();
        try {
//            Connection connection = conn.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                salesInvoice.setId(rs.getInt("invoiceid"));
                salesInvoice.setCreateDate(rs.getDate("create_date"));
                salesInvoice.setShipDate(rs.getDate("finish_date"));
                salesInvoice.setNote(rs.getString("note"));
                salesInvoice.setPaid(rs.getDouble("paid"));
                list.add(salesInvoice);
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    //Get Invoice by id
    public List<ImportInvoice> getImportInvoices(int id) {
        String query = "SELECT * \n"
                + "FROM store_manager.invoice as i WHERE i.type = 1 AND ownerid = ";
        query += id;
        List<ImportInvoice> list = new ArrayList<ImportInvoice>();
        ImportInvoice importInvoice = new ImportInvoice();
        try {
//            Connection connection = conn.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                importInvoice.setId(rs.getInt("invoiceid"));
                importInvoice.setCreateDate(rs.getDate("create_date"));
                importInvoice.setReceiveDate(rs.getDate("finish_date"));
                importInvoice.setNote(rs.getString("note"));
                importInvoice.setPaid(rs.getDouble("paid"));
                list.add(importInvoice);
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<LoanInvoice> getLoanInvoices(int id) {
        String query = "SELECT * \n"
                + "FROM store_manager.invoice as i WHERE i.type = 3 AND ownerid = ";
        query += id;
        List<LoanInvoice> list = new ArrayList<LoanInvoice>();
        LoanInvoice loanInvoice = new LoanInvoice();
        try {
//            Connection connection = conn.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                loanInvoice.setId(rs.getInt("invoiceid"));
                loanInvoice.setCreateDate(rs.getDate("create_date"));
                loanInvoice.setReturnDate(rs.getDate("finish_date"));
                loanInvoice.setNote(rs.getString("note"));
                list.add(loanInvoice);
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<SalesInvoice> getSalesInvoices(int id) {
        String query = "SELECT * \n"
                + "FROM store_manager.invoice as i WHERE i.type = 2 AND ownerid = ";
        query += id;
        List<SalesInvoice> list = new ArrayList<SalesInvoice>();
        SalesInvoice salesInvoice = new SalesInvoice();
        try {
//            Connection connection = conn.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                salesInvoice.setId(rs.getInt("invoiceid"));
                salesInvoice.setCreateDate(rs.getDate("create_date"));
                salesInvoice.setShipDate(rs.getDate("finish_date"));
                salesInvoice.setNote(rs.getString("note"));
                list.add(salesInvoice);
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    //Get invoice detail by invoice id
    public ImportInvoice getImportInvoiceDetail(int id) {
        String query = "SELECT * FROM \n"
                + "((store_manager.invoice as i \n"
                + "LEFT JOIN store_manager.invoice_product as ip ON i.invoiceid = ip.invoiceid)\n"
                + "LEFT JOIN store_manager.product as p ON ip.productid = p.productid)"
                + "WHERE i.invoiceid = ";
        query += id;

        ImportInvoice importInvoice = new ImportInvoice();
        Product importProduct = null;
        try {
//            Connection connection = conn.getConnection();
            Statement statement = connection.createStatement();
//            System.out.println(query);
            ResultSet rs = statement.executeQuery(query);
            rs.next();

            importInvoice.setId(rs.getInt("invoiceid"));
            importInvoice.setCreateDate(rs.getDate("create_date"));
            importInvoice.setReceiveDate(rs.getDate("finish_date"));
            importInvoice.setNote(rs.getString("note"));
            importInvoice.setPaid(rs.getDouble("paid"));
            do {
                importProduct = new Product();
                importProduct.setId(rs.getInt("productid"));
                importProduct.setQuantity(rs.getDouble("quantity"));
                importProduct.setName(rs.getString("name"));
                importProduct.setCategoryString(rs.getInt("categoryid"));
                importProduct.setUnit(rs.getString("unit"));
//                importProduct.setSize(rs.getString("size"));
                importProduct.setRetailPrice(rs.getDouble("retail_price"));

                importInvoice.getSoldProducts().add(importProduct);
             
            } while (rs.next());

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return importInvoice;
    }

    public LoanInvoice getLoanInvoiceDetail(int id) {
        String query = "SELECT * FROM \n"
                + "store_manager.invoice as i \n"
                + "LEFT JOIN store_manager.invoice_product as ip ON i.invoiceid = ip.invoiceid\n"
                + "LEFT JOIN product as p ON ip.productid = p.productid "
                + "WHERE i.invoiceid = ";
        query += id;
        LoanInvoice loanInvoice = new LoanInvoice();
        Product loanpProduct = new Product();
        try {
//            Connection connection = conn.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            loanInvoice.setId(rs.getInt("invoiceid"));
            loanInvoice.setCreateDate(rs.getDate("create_date"));
            loanInvoice.setReturnDate(rs.getDate("finish_date"));
            loanInvoice.setNote(rs.getString("note"));
            loanInvoice.setPaid(rs.getDouble("paid"));
            do {
                loanpProduct = new Product();
                loanpProduct.setId(rs.getInt("productid"));
                loanpProduct.setQuantity(rs.getDouble("quantity"));
                loanpProduct.setName(rs.getString("name"));
                loanpProduct.setCategoryString(rs.getInt("categoryid"));
                loanpProduct.setUnit(rs.getString("unit"));
                loanpProduct.setSize(rs.getString("size"));
                loanpProduct.setRetailPrice(rs.getDouble("retail_price"));

                loanInvoice.getSoldProducts().add(loanpProduct);

            } while (rs.next());
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loanInvoice;
    }

    public SalesInvoice getSalesInvoiceDetail(int id) {
        String query = "SELECT * FROM \n"
                + "store_manager.invoice as i \n"
                + "LEFT JOIN store_manager.invoice_product as ip ON i.invoiceid = ip.invoiceid\n"
                + "LEFT JOIN product as p ON ip.productid = p.productid "
                + "WHERE i.invoiceid = ";
        query += id;
        SalesInvoice salesInvoice = new SalesInvoice();
        Product salesProduct = new Product();
        try {
//            Connection connection = conn.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            salesInvoice.setId(rs.getInt("invoiceid"));
            salesInvoice.setCreateDate(rs.getDate("create_date"));
            salesInvoice.setShipDate(rs.getDate("finish_date"));
            salesInvoice.setNote(rs.getString("note"));
            salesInvoice.setPaid(rs.getDouble("paid"));
            do {
                salesProduct = new Product();
                salesProduct.setId(rs.getInt("productid"));
                salesProduct.setQuantity(rs.getDouble("quantity"));
                salesProduct.setName(rs.getString("name"));
                salesProduct.setCategoryString(rs.getInt("categoryid"));
                salesProduct.setUnit(rs.getString("unit"));
                salesProduct.setSize(rs.getString("size"));
                salesProduct.setRetailPrice(rs.getDouble("retail_price"));

                salesInvoice.getSoldProducts().add(salesProduct);
            }while (rs.next());
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salesInvoice;
    }

}
