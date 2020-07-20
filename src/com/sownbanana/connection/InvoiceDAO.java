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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.chart.XYChart;
import javafx.util.Pair;

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
                loanInvoice.setIsImport(rs.getBoolean("is_in"));
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
        ImportInvoice importInvoice;
        try {
//            Connection connection = conn.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                importInvoice = new ImportInvoice();
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
        LoanInvoice loanInvoice;
        try {
//            Connection connection = conn.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                loanInvoice = new LoanInvoice();
                loanInvoice.setId(rs.getInt("invoiceid"));
                loanInvoice.setCreateDate(rs.getDate("create_date"));
                loanInvoice.setReturnDate(rs.getDate("finish_date"));
                loanInvoice.setNote(rs.getString("note"));
                loanInvoice.setIsImport(rs.getBoolean("is_in"));
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
        SalesInvoice salesInvoice;
        try {
//            Connection connection = conn.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                salesInvoice = new SalesInvoice();
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
            loanInvoice.setIsImport(rs.getBoolean("is_in"));
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
            } while (rs.next());
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salesInvoice;
    }

    public void insertInvoice(int typeid, int ownerid, Date createDate, Date finishDate, Double paid, String note, List<Product> products, boolean isImport) {
        try {
            String query = "INSERT INTO invoice (type, ownerid, create_date, finish_date, note, paid, is_in) "
                    + "VALUES(?,?,?,?,?,?,?); ";

            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, typeid);
            statement.setInt(2, ownerid);
            statement.setDate(3, createDate);
            statement.setDate(4, finishDate);
            statement.setString(5, note);
            statement.setDouble(6, paid);
            statement.setBoolean(7, isImport);

            statement.executeUpdate();

            int id = 0;
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            for (int i = 0; i < products.size(); i++) {
                query = "INSERT INTO invoice_product (invoiceid, productid, quantity) "
                        + "VALUES(?, ?, ?) ";
                statement = connection.prepareStatement(query);
                statement.setInt(1, id);
                statement.setInt(2, products.get(i).getId());
                statement.setDouble(3, products.get(i).getQuantity());
                statement.executeUpdate();
                if (!isImport) {
                    query = "UPDATE `store_manager`.`product` SET `quantity` = `quantity` - ? WHERE (`productid` = ?);";
                    statement = connection.prepareStatement(query);
                    statement.setDouble(1, products.get(i).getQuantity());
                    statement.setInt(2, products.get(i).getId());
                    statement.executeUpdate();
                } else {
                    query = "UPDATE `store_manager`.`product` SET `quantity` = `quantity` + ?, import_date = ? WHERE (`productid` = ?);";
                    statement = connection.prepareStatement(query);
                    statement.setDouble(1, products.get(i).getQuantity());
                    statement.setDate(2, (Date) products.get(i).getImportDate());
                    statement.setInt(3, products.get(i).getId());
                    statement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int updateInvoice(int id, Double paid) {
        int rs = -1;
        String query = "UPDATE `store_manager`.`invoice` SET "
                + "`paid` = ? WHERE (`invoiceid` = ?);";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.setDouble(2, paid);
            rs = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public int updateInvoice(int id, Double paid, Date finishDate, String note) {
        int rs = -1;
        String query = "UPDATE `store_manager`.`invoice` SET "
                + "`paid` = ?, finish_date = ? , note = ? WHERE (`invoiceid` = ?);";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDouble(1, paid);
            statement.setDate(2, finishDate);
            statement.setString(3, note);
            statement.setInt(4, id);
            rs = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public List<String> getYears() {
        List<String> list = new ArrayList<>();
        String query = "SELECT DISTINCT year(finish_date) FROM store_manager.invoice order by finish_date";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                list.add(rs.getString(1));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<XYChart.Data<Number, Number>> getSaleCategoryByMonth(String category, String year) {
        List<XYChart.Data<Number, Number>> list = new ArrayList<>();
        String query = "SELECT month(finish_date), sum(ip.quantity) FROM store_manager.invoice as i \n"
                + "                LEFT JOIN store_manager.invoice_product as ip ON i.invoiceid = ip.invoiceid\n"
                + "                LEFT JOIN product as p ON ip.productid = p.productid \n"
                + "                LEFT JOIN category as c ON c.categoryid = p.categoryid"
                + "			WHERE i.type = 2 AND year(finish_date) = ? AND c.name = ?"
                + "                 group by month(finish_date)";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, year);
            statement.setString(2, category);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                XYChart.Data<Number, Number> pair = new XYChart.Data<Number, Number>(rs.getInt(1), rs.getDouble(2));
                list.add(pair);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Double getIncomeMonthNow() {
        String query = "SELECT sum((p.retail_price - p.import_price)* ip.quantity) as sum FROM store_manager.invoice as i \n"
                + "                LEFT JOIN store_manager.invoice_product as ip ON i.invoiceid = ip.invoiceid\n"
                + "                LEFT JOIN product as p ON ip.productid = p.productid \n"
                + "                LEFT JOIN category as c ON c.categoryid = p.categoryid\n"
                + "				WHERE (i.type = 2 OR i.is_in = 0) AND year(finish_date) = year(now()) AND  month(finish_date) = month(now())";
        Double ret = 0.0;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                ret = rs.getDouble(1);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public Double getTotalRecv() {
        String query = "SELECT sum(p.retail_price* ip.quantity) as sum FROM store_manager.invoice as i \n"
                + "                LEFT JOIN store_manager.invoice_product as ip ON i.invoiceid = ip.invoiceid\n"
                + "                LEFT JOIN product as p ON ip.productid = p.productid \n"
                + "                LEFT JOIN category as c ON c.categoryid = p.categoryid\n"
                + "				WHERE (i.type = 2 OR i.is_in = 0) AND year(finish_date) = year(now())";
        Double ret = 0.0;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                ret = rs.getDouble(1);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public Double getTotalPay() {
        String query = "SELECT sum(p.import_price* ip.quantity) as sum FROM store_manager.invoice as i \n"
                + "                LEFT JOIN store_manager.invoice_product as ip ON i.invoiceid = ip.invoiceid\n"
                + "                LEFT JOIN product as p ON ip.productid = p.productid \n"
                + "                LEFT JOIN category as c ON c.categoryid = p.categoryid\n"
                + "				WHERE (i.type = 2 OR i.is_in = 1) AND year(finish_date) = year(now())";
        Double ret = 0.0;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                ret = rs.getDouble(1);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public List<Pair<String, Double>> getSaleCategotyMonth() {
        List<Pair<String, Double>> list = new ArrayList<>();

        String query = "SELECT  c.name, sum(ip.quantity) FROM store_manager.invoice as i\n"
                + "                          LEFT JOIN store_manager.invoice_product as ip ON i.invoiceid = ip.invoiceid\n"
                + "                                LEFT JOIN product as p ON ip.productid = p.productid\n"
                + "                                LEFT JOIN category as c ON c.categoryid = p.categoryid\n"
                + "                				WHERE (i.type = 2 OR i.is_in = 0) AND year(finish_date) = year(now()) AND  month(finish_date) = month(now())\n"
                + "								GROUP BY c.name";

        Statement statement;
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                list.add(new Pair<String, Double>(rs.getString(1), rs.getDouble(2)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
