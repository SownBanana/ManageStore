/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sownbanana.connection;

import java.sql.Connection;
import com.sownbanana.model.Customer;
import com.sownbanana.model.FriendAgency;
import com.sownbanana.model.Product;
import com.sownbanana.model.Supplier;
import com.sownbanana.view.MainUI;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author son.ph173344
 */
public class EntityManager {
    public static Connection connection = ConnectionDB.getConnection();
    public static List<Product> warehouse = new ArrayList<Product>();
    public static List<Supplier> supliers = new ArrayList<Supplier>();
    public static List<Customer> customers = new ArrayList<Customer>();
    public static List<FriendAgency> friendAgencys = new ArrayList<FriendAgency>();
    
    public static FriendAgencyDAO friendAgencyDAO = new FriendAgencyDAO();
    public static CustomerDAO customerDAO = new CustomerDAO();
    public static SupplierDAO supplierDAO = new SupplierDAO();
    public static ProductDAO productDAO = new ProductDAO();
    public static InvoiceDAO invoiceDAO = new InvoiceDAO();
    public static CategoryDAO categoryDAO = new CategoryDAO();
    public static UnitDAO unitDAO = new UnitDAO();
    
    public static MainUI mainUI;
}
