/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sownbanana.view;

import com.sownbanana.connection.EntityManager;
import com.sownbanana.controller.ProductController;
import com.sownbanana.model.Customer;
import com.sownbanana.model.FriendAgency;
import com.sownbanana.model.ImportInvoice;
import com.sownbanana.model.LoanInvoice;
import com.sownbanana.model.Product;
import com.sownbanana.model.ProductTableModel;
import com.sownbanana.model.SalesInvoice;
import com.sownbanana.model.ShowProductModel;
import com.sownbanana.model.Supplier;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author son.ph173344
 */
public class MainUI extends javax.swing.JFrame {

    RangeSlider priceSlider;
    DefaultTableCellRenderer centerRenderer;
    DefaultTableCellRenderer rightRenderer;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    ProductController productController = new ProductController();
    //Product
    List<Product> productsList;
    ProductTableModel productsModel;
    Vector vctProductsHeader = new Vector();
    Vector vctProductsData = new Vector();

    //Customer
    List<Customer> customersList;
    DefaultListModel<String> customerNameModel;
    List<String> customersNameList;
    
    List<SalesInvoice> salesInvoicesList;
    
    DefaultTableModel salesInvoicesModel;
    Vector vctSalesInvoicesHeader = new Vector();
//    Vector vctSalesInvoicesData = new Vector();
    
    DefaultTreeModel treeImportModel;
    DefaultMutableTreeNode importRoot;

    int cusId;

    //Supplier
    DefaultListModel<String> supplierNameModel;
    List<Supplier> suppliersList;
    List<String> supplierNameList;
    List<ImportInvoice> importInvoicesList;
    
    DefaultTableModel importInvoicesModel;
    Vector vctImportInvoicesHeader = new Vector();
//    Vector vctImportInvoicesData = new Vector();
    
    DefaultTreeModel treeSaleModel;
    DefaultMutableTreeNode saleRoot;

    int supId;

    //Friend Agency
    List<Product> friendsList;
    DefaultListModel<String> friendsNameModel;
    List<String> friendsNameList;
    
    List<LoanInvoice> loanInvoicesList;
    
    DefaultTableModel loanInvoicesModel;
    Vector vctLoanInvoicesHeader = new Vector();
//    Vector vctLoanInvoicesData = new Vector();

    DefaultTreeModel treeLoanModel;
    DefaultMutableTreeNode loanRoot;

    int friendId;

    boolean isAdding = false;

    /**
     * Creates new form MainUI
     */
    public MainUI() {
        initComponents();

        DefaultTreeCellRenderer render = new DefaultTreeCellRenderer();
//        render.setLeafIcon(null);
        render.setOpenIcon(null);
        render.setClosedIcon(null);
        
        //Product Tab Init
        vctProductsHeader.add("ID");
        vctProductsHeader.add("Tên");
        vctProductsHeader.add("Loại");
        vctProductsHeader.add("Thuộc tính");
        vctProductsHeader.add("Số lượng");
        vctProductsHeader.add("Đơn vị");
        vctProductsHeader.add("Nguồn nhập");
        vctProductsHeader.add("Giá nhập (VNĐ)");
        vctProductsHeader.add("Giá bán");
        vctProductsHeader.add("Ngày nhập");

        tableProduct.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                System.out.println(Arrays.toString(tableProduct.getSelectedRows()));
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    int select = tableProduct.getSelectedRow();
                    Product p = productsList.get(select);
                    System.out.println(select);
                    ProductViewUI editproduct = new ProductViewUI(EntityManager.mainUI, rootPaneCheckingEnabled, p);
                    if (editproduct.isDataChange) {
                        showProductTable();
                    }
                }
            }
        });

        //Combo box supplier
        List<String> supplierComboItems = EntityManager.supplierDAO.getSuppliersName();
        for (String item : supplierComboItems) {
            cboxSupplier.addItem(item);
        }
        //Combo box category
        List<String> categoryComboitems = EntityManager.categoryDAO.getCategoryName();
        for (String item : categoryComboitems) {
            cboxCategory.addItem(item);
        }
        //Combo box situation
        lblTimeInProduct.setVisible(false);
        txtTimeInProduct.setVisible(false);

        //Customer Tab Init
        vctSalesInvoicesHeader.add("ID");
        vctSalesInvoicesHeader.add("Ngày đặt hàng");
        vctSalesInvoicesHeader.add("Ngày giao hàng");
        vctSalesInvoicesHeader.add("Tổng thu");
        vctSalesInvoicesHeader.add("Đã thu");
        vctSalesInvoicesHeader.add("Ghi chú");
        
        //Tree
        saleRoot = new DefaultMutableTreeNode("Root");
        treeSaleModel = new DefaultTreeModel(saleRoot);
        treeSale.setCellRenderer(render);
        treeSale.setModel(treeSaleModel);
        treeSale.setRootVisible(false);
        treeSale.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() >= 2 && !SwingUtilities.isRightMouseButton(e)) {
                    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treeSale.getLastSelectedPathComponent();
                    if (selectedNode != null) {
                        if (selectedNode.isLeaf()) {
                            ShowProductModel pm = (ShowProductModel)selectedNode.getUserObject();
                            ProductViewUI editproduct = new ProductViewUI(EntityManager.mainUI, rootPaneCheckingEnabled, EntityManager.productDAO.getProduct(pm.id));
                            if (editproduct.isDataChange) {
                                showProductTable();
                            }
                        }
                    }
                }
            }

        });

        //Supplier Tab Init
        vctImportInvoicesHeader.add("ID");
        vctImportInvoicesHeader.add("Ngày đặt hàng");
        vctImportInvoicesHeader.add("Ngày giao hàng");
        vctImportInvoicesHeader.add("Tổng chi");
        vctImportInvoicesHeader.add("Đã chi");
        vctImportInvoicesHeader.add("Ghi chú");
        
        //Tree supplier
        importRoot = new DefaultMutableTreeNode("Root");
        treeImportModel = new DefaultTreeModel(importRoot);
        treeImport.setCellRenderer(render);
        treeImport.setModel(treeImportModel);
        treeImport.setRootVisible(false);
        treeImport.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() >= 2 && !SwingUtilities.isRightMouseButton(e)) {
                    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treeImport.getLastSelectedPathComponent();
                    if (selectedNode != null) {
                        if (selectedNode.isLeaf()) {
                            ShowProductModel pm = (ShowProductModel)selectedNode.getUserObject();
                            ProductViewUI editproduct = new ProductViewUI(EntityManager.mainUI, rootPaneCheckingEnabled, EntityManager.productDAO.getProduct(pm.id));
                            if (editproduct.isDataChange) {
                                showProductTable();
                            }
                        }
                    }
                }
            }

        });

        centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        tableProduct.setDefaultRenderer(String.class, centerRenderer);
        tableProduct.setDefaultRenderer(Double.class, centerRenderer);
        tableProduct.setDefaultRenderer(LocalDate.class, rightRenderer);

        //Friend Agency Tab Init
        //Table
        vctLoanInvoicesHeader.add("ID");
        vctLoanInvoicesHeader.add("Ngày đặt hàng");
        vctLoanInvoicesHeader.add("Ngày giao hàng");
        vctLoanInvoicesHeader.add("Tổng giá");
        vctLoanInvoicesHeader.add("Đã thanh toán");
        vctLoanInvoicesHeader.add("Ghi chú");

        //Tree
        loanRoot = new DefaultMutableTreeNode("Root");
        treeLoanModel = new DefaultTreeModel(loanRoot);
        treeLoan.setCellRenderer(render);
        treeLoan.setModel(treeLoanModel);
        treeLoan.setRootVisible(false);
        treeLoan.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() >= 2 && !SwingUtilities.isRightMouseButton(e)) {
                    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treeLoan.getLastSelectedPathComponent();
                    if (selectedNode != null) {
                        if (selectedNode.isLeaf()) {
                            ShowProductModel pm = (ShowProductModel)selectedNode.getUserObject();
                            ProductViewUI editproduct = new ProductViewUI(EntityManager.mainUI, rootPaneCheckingEnabled, EntityManager.productDAO.getProduct(pm.id));
                            if (editproduct.isDataChange) {
                                showProductTable();
                            }
                        }
                    }
                }
            }

        });

        
        centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        tableProduct.setDefaultRenderer(String.class, centerRenderer);
        tableProduct.setDefaultRenderer(Double.class, centerRenderer);
        tableProduct.setDefaultRenderer(LocalDate.class, rightRenderer);

        //align center header
        ((DefaultTableCellRenderer) tableProduct.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER);
        ((DefaultTableCellRenderer) tableImportInvoice.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER);
        ((DefaultTableCellRenderer) tableSalesInvoice.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER);
        ((DefaultTableCellRenderer) tableLoanInvoice.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER);

        //Slider      
        priceSlider = new RangeSlider(0, 50000);
        priceSlider.setValue(0);
        priceSlider.setUpperValue(50000);
        sliderPane.setLayout(new BoxLayout(sliderPane, BoxLayout.LINE_AXIS));
        sliderPane.add(priceSlider);
        priceSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                RangeSlider slider = (RangeSlider) e.getSource();
                lblMinPrice.setText(String.valueOf(slider.getValue()));
                lblMaxPrice.setText(String.valueOf(slider.getUpperValue()));
//                List<Product>
                showProductTable(productsList);
            }
        });
        showResult();

        //List suppliers
        supplierNameList = EntityManager.supplierDAO.getSuppliersName();
        supplierNameModel = new DefaultListModel<>();
        supplierNameModel.addAll(supplierNameList);
        listSupplier.setModel(supplierNameModel);
        listSupplier.setSelectedIndex(0);
        showSupplier(listSupplier.getSelectedValue());

        lblSupExist.setVisible(false);

        //List customers
        customersNameList = EntityManager.customerDAO.getCustomersName();
        customerNameModel = new DefaultListModel<>();
        customerNameModel.addAll(customersNameList);
        listCustomer.setModel(customerNameModel);
        listCustomer.setSelectedIndex(0);
        showCustomer(listCustomer.getSelectedValue());

        lblCusExist.setVisible(false);

        //List friends
        friendsNameList = EntityManager.friendAgencyDAO.getFriendAgencysName();
        friendsNameModel = new DefaultListModel<>();
        friendsNameModel.addAll(friendsNameList);
        listFriend.setModel(friendsNameModel);
        listFriend.setSelectedIndex(0);
        showFriend(listFriend.getSelectedValue());

        lblFrdExist.setVisible(false);

        //dấdasdadasd
        btnSupSave.setVisible(false);
        btnSupCancel.setVisible(false);
        btnSupDel.setVisible(false);

        btnCusSave.setVisible(false);
        btnCusCancel.setVisible(false);
        btnCusDel.setVisible(false);

        btnFrdSave.setVisible(false);
        btnFrdCancel.setVisible(false);
        btnFrdDel.setVisible(false);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        editSearchProduct = new javax.swing.JTextField();
        btnSearchProduct = new javax.swing.JButton();
        cboxSituation = new javax.swing.JComboBox<>();
        cboxCategory = new javax.swing.JComboBox<>();
        jScrollPane7 = new javax.swing.JScrollPane();
        tableProduct = new javax.swing.JTable();
        btnEditProduct = new javax.swing.JButton();
        btnDelProduct = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        btnAddProduct = new javax.swing.JButton();
        cboxSupplier = new javax.swing.JComboBox<>();
        jLabel28 = new javax.swing.JLabel();
        txtTimeInProduct = new javax.swing.JTextField();
        lblTimeInProduct = new javax.swing.JLabel();
        sliderPane = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        lblMinPrice = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        lblMaxPrice = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        panelCusInfor = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtCusID = new javax.swing.JTextField();
        txtCusName = new javax.swing.JTextField();
        txtCusPhone = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableSalesInvoice = new javax.swing.JTable();
        btnCusCancel = new javax.swing.JButton();
        btnCusSave = new javax.swing.JButton();
        btnCusEdit = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        txtCusNameBank = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        txtCusBankID = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        txtCusBank = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        txtCusAddr = new javax.swing.JTextField();
        btnCusDel = new javax.swing.JButton();
        lblCusExist = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        treeSale = new javax.swing.JTree();
        editSearchCustomer = new javax.swing.JTextField();
        btnAddCustomer = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        listCustomer = new javax.swing.JList<>();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listSupplier = new javax.swing.JList<>();
        editSearchSupplier = new javax.swing.JTextField();
        panelSupInfor = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtSupID = new javax.swing.JTextField();
        txtSupName = new javax.swing.JTextField();
        txtSupPhone = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtSupBankID = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtSupBank = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableImportInvoice = new javax.swing.JTable();
        btnSupCancel = new javax.swing.JButton();
        btnSupSave = new javax.swing.JButton();
        btnSupEdit = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        txtSupAddr = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        txtSupNameBank = new javax.swing.JTextField();
        lblSupExist = new javax.swing.JLabel();
        btnSupDel = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        treeImport = new javax.swing.JTree();
        btnAddSupplier = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listFriend = new javax.swing.JList<>();
        editSearchFriend = new javax.swing.JTextField();
        panelFrdInfor = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtFrdID = new javax.swing.JTextField();
        txtFrdName = new javax.swing.JTextField();
        txtFrdPhone = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableLoanInvoice = new javax.swing.JTable();
        btnFrdCancel = new javax.swing.JButton();
        btnFrdSave = new javax.swing.JButton();
        btnFrdEdit = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        txtFrdNameBank = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        txtFrdBankID = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        txtFrdBank = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        txtFrdAddr = new javax.swing.JTextField();
        btnFrdDel = new javax.swing.JButton();
        lblFrdExist = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        treeLoan = new javax.swing.JTree();
        btnAddFriend = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setToolTipText("");
        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 865, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 560, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Trang chủ", jPanel1);

        editSearchProduct.setToolTipText("Nhận tên nguồn hàng");
        editSearchProduct.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                editSearchProductKeyReleased(evt);
            }
        });

        btnSearchProduct.setText("O");
        btnSearchProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchProductActionPerformed(evt);
            }
        });

        cboxSituation.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Trong kho", "Sắp đến" }));
        cboxSituation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboxSituationActionPerformed(evt);
            }
        });

        cboxCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả" }));
        cboxCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboxCategoryActionPerformed(evt);
            }
        });

        tableProduct.setAutoCreateRowSorter(true);
        tableProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane7.setViewportView(tableProduct);

        btnEditProduct.setText("Chỉnh sửa");
        btnEditProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditProductActionPerformed(evt);
            }
        });

        btnDelProduct.setText("Xoá bỏ");
        btnDelProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelProductActionPerformed(evt);
            }
        });

        jLabel22.setText(" Tình trạng");

        jLabel23.setText(" Loại hàng");

        jLabel24.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel24.setText("QUẢN LÝ KHO HÀNG");

        btnAddProduct.setText("Thêm hàng");
        btnAddProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddProductActionPerformed(evt);
            }
        });

        cboxSupplier.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả" }));
        cboxSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboxSupplierActionPerformed(evt);
            }
        });

        jLabel28.setText(" Nguồn hàng");

        txtTimeInProduct.setText("jTextField4");
        txtTimeInProduct.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimeInProductKeyReleased(evt);
            }
        });

        lblTimeInProduct.setText(" Thời gian");

        javax.swing.GroupLayout sliderPaneLayout = new javax.swing.GroupLayout(sliderPane);
        sliderPane.setLayout(sliderPaneLayout);
        sliderPaneLayout.setHorizontalGroup(
            sliderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        sliderPaneLayout.setVerticalGroup(
            sliderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 23, Short.MAX_VALUE)
        );

        jLabel34.setText("Giá nhập:");

        lblMinPrice.setText("0");

        jLabel36.setText(":");

        lblMaxPrice.setText("50000");

        jLabel30.setText("VNĐ");

        jLabel31.setText("VNĐ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(editSearchProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sliderPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(14, 14, 14)
                        .addComponent(txtTimeInProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboxSituation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cboxSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cboxCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSearchProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 594, Short.MAX_VALUE)
                        .addComponent(btnAddProduct)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDelProduct)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEditProduct))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel34)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblMinPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jLabel36)
                        .addGap(2, 2, 2)
                        .addComponent(lblMaxPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblTimeInProduct)
                        .addGap(105, 105, 105)
                        .addComponent(jLabel22)
                        .addGap(40, 40, 40)
                        .addComponent(jLabel28)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel23)
                        .addGap(69, 69, 69)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(jLabel22)
                            .addComponent(jLabel28)
                            .addComponent(lblTimeInProduct))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel34)
                            .addComponent(lblMinPrice)
                            .addComponent(jLabel36)
                            .addComponent(lblMaxPrice)
                            .addComponent(jLabel30)
                            .addComponent(jLabel31)
                            .addComponent(jLabel24))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sliderPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(editSearchProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSearchProduct)
                        .addComponent(cboxSituation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cboxCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cboxSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTimeInProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 464, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnDelProduct)
                            .addComponent(btnEditProduct))
                        .addGap(5, 5, 5))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(btnAddProduct)
                        .addContainerGap())))
        );

        jTabbedPane1.addTab("Kho hàng", jPanel2);

        panelCusInfor.setPreferredSize(new java.awt.Dimension(618, 541));

        jLabel15.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel15.setText("THÔNG TIN KHÁCH HÀNG");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel16.setText("ID:");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel17.setText("Tên:");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel18.setText("Số điện thoại:");

        txtCusID.setBackground(new java.awt.Color(240, 240, 240));
        txtCusID.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtCusID.setText("00001");
        txtCusID.setBorder(null);
        txtCusID.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtCusID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCusIDActionPerformed(evt);
            }
        });

        txtCusName.setBackground(new java.awt.Color(240, 240, 240));
        txtCusName.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtCusName.setText("Kiên");
        txtCusName.setBorder(null);
        txtCusName.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtCusName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCusNameFocusGained(evt);
            }
        });

        txtCusPhone.setBackground(new java.awt.Color(240, 240, 240));
        txtCusPhone.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtCusPhone.setText("0986414972");
        txtCusPhone.setBorder(null);
        txtCusPhone.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        tableSalesInvoice.setAutoCreateRowSorter(true);
        tableSalesInvoice.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(tableSalesInvoice);

        btnCusCancel.setText("Huỷ bỏ");
        btnCusCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCusCancelActionPerformed(evt);
            }
        });

        btnCusSave.setText("Lưu");
        btnCusSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCusSaveActionPerformed(evt);
            }
        });

        btnCusEdit.setText("Chỉnh sửa thông tin");
        btnCusEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCusEditActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel25.setText("Lịch sử mua hàng");

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel32.setText("Tên tài khoản:");

        txtCusNameBank.setBackground(new java.awt.Color(240, 240, 240));
        txtCusNameBank.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtCusNameBank.setText("LAI DAC KIEN");
        txtCusNameBank.setBorder(null);
        txtCusNameBank.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtCusNameBank.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCusNameBankActionPerformed(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel33.setText("Tài khoản:");

        txtCusBankID.setBackground(new java.awt.Color(240, 240, 240));
        txtCusBankID.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtCusBankID.setText("786 438 532 432");
        txtCusBankID.setBorder(null);
        txtCusBankID.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel35.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel35.setText("Ngân hàng:");

        txtCusBank.setBackground(new java.awt.Color(240, 240, 240));
        txtCusBank.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtCusBank.setText("VietinBank Nghệ An");
        txtCusBank.setBorder(null);
        txtCusBank.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel37.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel37.setText("Địa chỉ:");

        txtCusAddr.setBackground(new java.awt.Color(240, 240, 240));
        txtCusAddr.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtCusAddr.setText("Thôn Gì Đó, xã Gì Đó, huyện Gì Đó, tỉnh Gì đó ");
        txtCusAddr.setBorder(null);
        txtCusAddr.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtCusAddr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCusAddrActionPerformed(evt);
            }
        });

        btnCusDel.setText("Xoá");
        btnCusDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCusDelActionPerformed(evt);
            }
        });

        lblCusExist.setForeground(new java.awt.Color(255, 0, 0));
        lblCusExist.setText("* Tên đã được sử dụng");

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("JTree");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("1       |   26/01/1999               |   30/01/1999              |   15.000.000               |  15.000.000               |");
        javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("blue");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("violet");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("red");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("yellow");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("2       |   26/01/1999              |   30/01/1999               |   15.000.000               |  15.000.000                |");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("basketball");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("soccer");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("football");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("hockey");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("3       |   26/01/1999               |   30/01/1999              |   15.000.000               |  15.000.000               |");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("blue");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("violet");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("red");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("yellow");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("101       |   26/01/1999               |   30/01/1999              |   15.000.000               |  15.000.000               |");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("blue");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("violet");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("red");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("yellow");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeSale.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        treeSale.setRootVisible(false);
        jScrollPane10.setViewportView(treeSale);

        javax.swing.GroupLayout panelCusInforLayout = new javax.swing.GroupLayout(panelCusInfor);
        panelCusInfor.setLayout(panelCusInforLayout);
        panelCusInforLayout.setHorizontalGroup(
            panelCusInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCusInforLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5))
            .addGroup(panelCusInforLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCusDel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCusSave, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCusCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCusInforLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel15)
                .addGap(220, 220, 220))
            .addGroup(panelCusInforLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(panelCusInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel33)
                    .addComponent(jLabel32)
                    .addComponent(jLabel37)
                    .addComponent(jLabel18)
                    .addComponent(jLabel17)
                    .addComponent(jLabel16))
                .addGroup(panelCusInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCusInforLayout.createSequentialGroup()
                        .addGroup(panelCusInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelCusInforLayout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addGroup(panelCusInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCusNameBank, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCusBankID, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCusAddr, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCusPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCusID, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(panelCusInforLayout.createSequentialGroup()
                                        .addComponent(txtCusName, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblCusExist))))
                            .addGroup(panelCusInforLayout.createSequentialGroup()
                                .addGap(189, 189, 189)
                                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCusBank, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCusInforLayout.createSequentialGroup()
                        .addGap(380, 380, 380)
                        .addComponent(btnCusEdit))))
            .addGroup(panelCusInforLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane10))
        );
        panelCusInforLayout.setVerticalGroup(
            panelCusInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCusInforLayout.createSequentialGroup()
                .addComponent(jLabel15)
                .addGap(18, 18, 18)
                .addGroup(panelCusInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCusInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16)
                        .addComponent(txtCusID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnCusEdit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCusInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCusName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(lblCusExist))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCusInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtCusPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCusInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(txtCusNameBank, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCusInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(txtCusBankID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35)
                    .addComponent(txtCusBank, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCusInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(txtCusAddr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelCusInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(btnCusCancel)
                    .addComponent(btnCusSave)
                    .addComponent(btnCusDel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        editSearchCustomer.setToolTipText("Nhận tên nguồn hàng");
        editSearchCustomer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                editSearchCustomerKeyReleased(evt);
            }
        });

        btnAddCustomer.setText("Thêm");
        btnAddCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCustomerActionPerformed(evt);
            }
        });

        listCustomer.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listCustomer.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listCustomerValueChanged(evt);
            }
        });
        jScrollPane6.setViewportView(listCustomer);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(editSearchCustomer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddCustomer)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelCusInfor, javax.swing.GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(panelCusInfor, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
                        .addGap(16, 16, 16))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(editSearchCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAddCustomer))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        jTabbedPane1.addTab("Khách hàng", jPanel4);

        listSupplier.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listSupplier.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listSupplierValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(listSupplier);

        editSearchSupplier.setToolTipText("Nhận tên nguồn hàng");
        editSearchSupplier.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                editSearchSupplierKeyReleased(evt);
            }
        });

        panelSupInfor.setPreferredSize(new java.awt.Dimension(618, 541));

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel2.setText("THÔNG TIN NGUỒN CUNG CẤP HÀNG");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel1.setText("ID:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel3.setText("Tên:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel4.setText("Số điện thoại:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel5.setText("Địa chỉ:");

        txtSupID.setBackground(new java.awt.Color(240, 240, 240));
        txtSupID.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtSupID.setBorder(null);
        txtSupID.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtSupID.setEnabled(false);
        txtSupID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSupIDActionPerformed(evt);
            }
        });

        txtSupName.setBackground(new java.awt.Color(240, 240, 240));
        txtSupName.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtSupName.setBorder(null);
        txtSupName.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtSupName.setEnabled(false);
        txtSupName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSupNameFocusGained(evt);
            }
        });

        txtSupPhone.setBackground(new java.awt.Color(240, 240, 240));
        txtSupPhone.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtSupPhone.setBorder(null);
        txtSupPhone.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtSupPhone.setEnabled(false);
        txtSupPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSupPhoneActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel6.setText("Tên tài khoản:");

        txtSupBankID.setBackground(new java.awt.Color(240, 240, 240));
        txtSupBankID.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtSupBankID.setBorder(null);
        txtSupBankID.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtSupBankID.setEnabled(false);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel7.setText("Ngân hàng:");

        txtSupBank.setBackground(new java.awt.Color(240, 240, 240));
        txtSupBank.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtSupBank.setBorder(null);
        txtSupBank.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtSupBank.setEnabled(false);

        tableImportInvoice.setAutoCreateRowSorter(true);
        tableImportInvoice.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tableImportInvoice);

        btnSupCancel.setText("Huỷ bỏ");
        btnSupCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSupCancelActionPerformed(evt);
            }
        });

        btnSupSave.setText("Lưu");
        btnSupSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSupSaveActionPerformed(evt);
            }
        });

        btnSupEdit.setText("Chỉnh sửa thông tin");
        btnSupEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSupEditActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel26.setText("Lịch sử nhập hàng");

        txtSupAddr.setBackground(new java.awt.Color(240, 240, 240));
        txtSupAddr.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtSupAddr.setBorder(null);
        txtSupAddr.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtSupAddr.setEnabled(false);
        txtSupAddr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSupAddrActionPerformed(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel29.setText("Tài khoản:");

        txtSupNameBank.setBackground(new java.awt.Color(240, 240, 240));
        txtSupNameBank.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtSupNameBank.setBorder(null);
        txtSupNameBank.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtSupNameBank.setEnabled(false);
        txtSupNameBank.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSupNameBankActionPerformed(evt);
            }
        });

        lblSupExist.setForeground(new java.awt.Color(255, 0, 0));
        lblSupExist.setText("* Tên đã được sử dụng");

        btnSupDel.setText("Xoá");
        btnSupDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSupDelActionPerformed(evt);
            }
        });

        treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("JTree");
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("1       |   26/01/1999               |   30/01/1999              |   15.000.000               |  15.000.000               |");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("blue");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("violet");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("red");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("yellow");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("2       |   26/01/1999              |   30/01/1999               |   15.000.000               |  15.000.000                |");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("basketball");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("soccer");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("football");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("hockey");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("3       |   26/01/1999               |   30/01/1999              |   15.000.000               |  15.000.000               |");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("blue");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("violet");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("red");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("yellow");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("101       |   26/01/1999               |   30/01/1999              |   15.000.000               |  15.000.000               |");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("blue");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("violet");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("red");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("yellow");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeImport.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        treeImport.setRootVisible(false);
        jScrollPane9.setViewportView(treeImport);

        javax.swing.GroupLayout panelSupInforLayout = new javax.swing.GroupLayout(panelSupInfor);
        panelSupInfor.setLayout(panelSupInforLayout);
        panelSupInforLayout.setHorizontalGroup(
            panelSupInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSupInforLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSupInforLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(164, 164, 164))
            .addGroup(panelSupInforLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSupDel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSupSave, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSupCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(panelSupInforLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(panelSupInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel29)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5))
                .addGroup(panelSupInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSupInforLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(panelSupInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelSupInforLayout.createSequentialGroup()
                                .addComponent(txtSupID, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnSupEdit))
                            .addGroup(panelSupInforLayout.createSequentialGroup()
                                .addGroup(panelSupInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtSupPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSupNameBank, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSupBankID, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSupAddr, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(panelSupInforLayout.createSequentialGroup()
                                        .addComponent(txtSupName, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblSupExist)))
                                .addContainerGap(26, Short.MAX_VALUE))))
                    .addGroup(panelSupInforLayout.createSequentialGroup()
                        .addGap(189, 189, 189)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSupBank, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(panelSupInforLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9))
        );
        panelSupInforLayout.setVerticalGroup(
            panelSupInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSupInforLayout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(panelSupInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSupInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txtSupID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnSupEdit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelSupInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSupName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(lblSupExist))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelSupInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtSupPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelSupInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtSupNameBank, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelSupInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(txtSupBankID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtSupBank, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelSupInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtSupAddr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelSupInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSupCancel)
                    .addComponent(btnSupSave)
                    .addComponent(jLabel26)
                    .addComponent(btnSupDel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btnAddSupplier.setText("Thêm");
        btnAddSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddSupplierActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(editSearchSupplier)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddSupplier)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelSupInfor, javax.swing.GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(panelSupInfor, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
                        .addGap(16, 16, 16))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(editSearchSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAddSupplier))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        jTabbedPane1.addTab("Nguồn hàng", jPanel3);

        listFriend.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listFriend.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listFriendValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(listFriend);

        editSearchFriend.setToolTipText("Nhận tên nguồn hàng");
        editSearchFriend.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                editSearchFriendKeyReleased(evt);
            }
        });

        panelFrdInfor.setPreferredSize(new java.awt.Dimension(618, 541));

        jLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel8.setText("THÔNG TIN ĐẠI LÝ ĐỐI TÁC");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel9.setText("ID:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel10.setText("Tên:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel11.setText("Số điện thoại:");

        txtFrdID.setBackground(new java.awt.Color(240, 240, 240));
        txtFrdID.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtFrdID.setText("00001");
        txtFrdID.setBorder(null);
        txtFrdID.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtFrdID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFrdIDActionPerformed(evt);
            }
        });

        txtFrdName.setBackground(new java.awt.Color(240, 240, 240));
        txtFrdName.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtFrdName.setText("Kiên");
        txtFrdName.setBorder(null);
        txtFrdName.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtFrdName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtFrdNameFocusGained(evt);
            }
        });

        txtFrdPhone.setBackground(new java.awt.Color(240, 240, 240));
        txtFrdPhone.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtFrdPhone.setText("0986414972");
        txtFrdPhone.setBorder(null);
        txtFrdPhone.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        tableLoanInvoice.setAutoCreateRowSorter(true);
        tableLoanInvoice.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableLoanInvoice.setAutoscrolls(false);
        jScrollPane4.setViewportView(tableLoanInvoice);

        btnFrdCancel.setText("Huỷ bỏ");
        btnFrdCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFrdCancelActionPerformed(evt);
            }
        });

        btnFrdSave.setText("Lưu");
        btnFrdSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFrdSaveActionPerformed(evt);
            }
        });

        btnFrdEdit.setText("Chỉnh sửa thông tin");
        btnFrdEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFrdEditActionPerformed(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel27.setText("Lịch sử giao dịch");

        jLabel38.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel38.setText("Tên tài khoản:");

        txtFrdNameBank.setBackground(new java.awt.Color(240, 240, 240));
        txtFrdNameBank.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtFrdNameBank.setText("LAI DAC KIEN");
        txtFrdNameBank.setBorder(null);
        txtFrdNameBank.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtFrdNameBank.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFrdNameBankActionPerformed(evt);
            }
        });

        jLabel39.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel39.setText("Tài khoản:");

        txtFrdBankID.setBackground(new java.awt.Color(240, 240, 240));
        txtFrdBankID.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtFrdBankID.setText("786 438 532 432");
        txtFrdBankID.setBorder(null);
        txtFrdBankID.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel40.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel40.setText("Ngân hàng:");

        txtFrdBank.setBackground(new java.awt.Color(240, 240, 240));
        txtFrdBank.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtFrdBank.setText("VietinBank Nghệ An");
        txtFrdBank.setBorder(null);
        txtFrdBank.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel41.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel41.setText("Địa chỉ:");

        txtFrdAddr.setBackground(new java.awt.Color(240, 240, 240));
        txtFrdAddr.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtFrdAddr.setText("Thôn Gì Đó, xã Gì Đó, huyện Gì Đó, tỉnh Gì đó ");
        txtFrdAddr.setBorder(null);
        txtFrdAddr.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtFrdAddr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFrdAddrActionPerformed(evt);
            }
        });

        btnFrdDel.setText("Xoá");
        btnFrdDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFrdDelActionPerformed(evt);
            }
        });

        lblFrdExist.setForeground(new java.awt.Color(255, 0, 0));
        lblFrdExist.setText("* Tên đã được sử dụng");

        treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("JTree");
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("1       |   26/01/1999               |   30/01/1999              |   15.000.000               |  15.000.000               |");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("blue");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("violet");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("red");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("yellow");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("2       |   26/01/1999              |   30/01/1999               |   15.000.000               |  15.000.000                |");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("basketball");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("soccer");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("football");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("hockey");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("3       |   26/01/1999               |   30/01/1999              |   15.000.000               |  15.000.000               |");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("blue");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("violet");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("red");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("yellow");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("101       |   26/01/1999               |   30/01/1999              |   15.000.000               |  15.000.000               |");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("blue");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("violet");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("red");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("yellow");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeLoan.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        treeLoan.setRootVisible(false);
        jScrollPane8.setViewportView(treeLoan);

        javax.swing.GroupLayout panelFrdInforLayout = new javax.swing.GroupLayout(panelFrdInfor);
        panelFrdInfor.setLayout(panelFrdInforLayout);
        panelFrdInforLayout.setHorizontalGroup(
            panelFrdInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFrdInforLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFrdInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFrdInforLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(205, 205, 205))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFrdInforLayout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnFrdDel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnFrdSave, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnFrdCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelFrdInforLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(panelFrdInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel39)
                            .addComponent(jLabel38)
                            .addComponent(jLabel41)
                            .addComponent(jLabel11)
                            .addComponent(jLabel10)
                            .addComponent(jLabel9))
                        .addGroup(panelFrdInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelFrdInforLayout.createSequentialGroup()
                                .addGroup(panelFrdInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelFrdInforLayout.createSequentialGroup()
                                        .addGap(23, 23, 23)
                                        .addGroup(panelFrdInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtFrdNameBank, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtFrdBankID, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtFrdAddr, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtFrdPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtFrdID, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(panelFrdInforLayout.createSequentialGroup()
                                                .addComponent(txtFrdName, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(lblFrdExist))))
                                    .addGroup(panelFrdInforLayout.createSequentialGroup()
                                        .addGap(189, 189, 189)
                                        .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtFrdBank, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFrdInforLayout.createSequentialGroup()
                                .addGap(380, 380, 380)
                                .addComponent(btnFrdEdit))))
                    .addComponent(jScrollPane8)))
        );
        panelFrdInforLayout.setVerticalGroup(
            panelFrdInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFrdInforLayout.createSequentialGroup()
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addGroup(panelFrdInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFrdInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(txtFrdID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnFrdEdit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFrdInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFrdName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(lblFrdExist))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFrdInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtFrdPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFrdInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(txtFrdNameBank, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFrdInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(txtFrdBankID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40)
                    .addComponent(txtFrdBank, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFrdInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(txtFrdAddr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelFrdInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFrdSave)
                    .addComponent(btnFrdCancel)
                    .addComponent(jLabel27)
                    .addComponent(btnFrdDel))
                .addGap(13, 13, 13)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        btnAddFriend.setText("Thêm");
        btnAddFriend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddFriendActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addComponent(editSearchFriend)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddFriend)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelFrdInfor, javax.swing.GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(panelFrdInfor, javax.swing.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE)
                        .addGap(16, 16, 16))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(editSearchFriend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAddFriend))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Đối tác", jPanel5);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 845, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Hoá đơn", jPanel8);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 865, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 560, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Thống kê", jPanel6);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 870, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 590, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSupplierActionPerformed
        // TODO add your handling code here:
        listSupplier.setSelectedIndex(-1);

        setEditingMode(true);
        btnSupDel.setVisible(false);
        isAdding = true;
        btnSupSave.setText("Thêm");

        txtSupName.setEnabled(true);
        txtSupName.setBackground(Color.WHITE);
        txtSupName.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        txtSupName.setText("");

        txtSupPhone.setEnabled(true);
        txtSupPhone.setBackground(Color.WHITE);
        txtSupPhone.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        txtSupPhone.setText("");

        txtSupNameBank.setEnabled(true);
        txtSupNameBank.setBackground(Color.WHITE);
        txtSupNameBank.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        txtSupNameBank.setText("");

        txtSupBankID.setEnabled(true);
        txtSupBankID.setBackground(Color.WHITE);
        txtSupBankID.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        txtSupBankID.setText("");

        txtSupBank.setEnabled(true);
        txtSupBank.setBackground(Color.WHITE);
        txtSupBank.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        txtSupBank.setText("");

        txtSupAddr.setEnabled(true);
        txtSupAddr.setBackground(Color.WHITE);
        txtSupAddr.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        txtSupAddr.setText("");

    }//GEN-LAST:event_btnAddSupplierActionPerformed

    private void txtSupIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSupIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSupIDActionPerformed

    private void txtFrdIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFrdIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFrdIDActionPerformed

    private void btnAddFriendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddFriendActionPerformed
        // TODO add your handling code here:
        listFriend.setSelectedIndex(-1);

        setEditingMode(true);
        btnFrdDel.setVisible(false);
        isAdding = true;
        btnFrdSave.setText("Thêm");

        txtFrdName.setEnabled(true);
        txtFrdName.setBackground(Color.WHITE);
        txtFrdName.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        txtFrdName.setText("");

        txtFrdPhone.setEnabled(true);
        txtFrdPhone.setBackground(Color.WHITE);
        txtFrdPhone.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        txtFrdPhone.setText("");

        txtFrdNameBank.setEnabled(true);
        txtFrdNameBank.setBackground(Color.WHITE);
        txtFrdNameBank.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        txtFrdNameBank.setText("");

        txtFrdBankID.setEnabled(true);
        txtFrdBankID.setBackground(Color.WHITE);
        txtFrdBankID.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        txtFrdBankID.setText("");

        txtFrdBank.setEnabled(true);
        txtFrdBank.setBackground(Color.WHITE);
        txtFrdBank.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        txtFrdBank.setText("");

        txtFrdAddr.setEnabled(true);
        txtFrdAddr.setBackground(Color.WHITE);
        txtFrdAddr.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        txtFrdAddr.setText("");
    }//GEN-LAST:event_btnAddFriendActionPerformed

    private void txtCusIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCusIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCusIDActionPerformed

    private void btnAddCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCustomerActionPerformed
        // TODO add your handling code here:
        listCustomer.setSelectedIndex(-1);

        setEditingMode(true);
        btnCusDel.setVisible(false);
        isAdding = true;
        btnCusSave.setText("Thêm");

        txtCusName.setEnabled(true);
        txtCusName.setBackground(Color.WHITE);
        txtCusName.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        txtCusName.setText("");

        txtCusPhone.setEnabled(true);
        txtCusPhone.setBackground(Color.WHITE);
        txtCusPhone.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        txtCusPhone.setText("");

        txtCusNameBank.setEnabled(true);
        txtCusNameBank.setBackground(Color.WHITE);
        txtCusNameBank.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        txtCusNameBank.setText("");

        txtCusBankID.setEnabled(true);
        txtCusBankID.setBackground(Color.WHITE);
        txtCusBankID.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        txtCusBankID.setText("");

        txtCusBank.setEnabled(true);
        txtCusBank.setBackground(Color.WHITE);
        txtCusBank.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        txtCusBank.setText("");

        txtCusAddr.setEnabled(true);
        txtCusAddr.setBackground(Color.WHITE);
        txtCusAddr.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        txtCusAddr.setText("");
    }//GEN-LAST:event_btnAddCustomerActionPerformed

    private void txtSupAddrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSupAddrActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSupAddrActionPerformed

    private void btnAddProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddProductActionPerformed
        // TODO add your handling code here:
        ProductViewUI productViewUI = new ProductViewUI(this, rootPaneCheckingEnabled);
        System.out.println("Thực hiện code này nè");
        if (productViewUI.isDataChange) {
            showProductTable();
        }
    }//GEN-LAST:event_btnAddProductActionPerformed

    private void btnDelProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelProductActionPerformed
        // TODO add your handling code here:
        try {
            int[] removeIndex = tableProduct.getSelectedRows();
            System.out.println(removeIndex[0]);
            //            System.out.println(removeIndex == null);
            int check = JOptionPane.showConfirmDialog(rootPane, "Bạn Chắc chắn muốn xoá", "Xoá sản phẩm", JOptionPane.YES_NO_OPTION);
            //            System.out.println(check);
            if (check == 0) {
                int index = removeIndex[0];
                List<Product> rmProducts = new ArrayList<>();
                for (int i : removeIndex) {
                    System.out.println("i = " + i);
                    Product p = productsList.get(index);
                    rmProducts.add(p);
                    EntityManager.productDAO.removeProduct(p);
                }
                productsList.removeAll(rmProducts);
                showProductTable(productsList);
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(rootPane, "Chọn một sản phẩm để xóa!");
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnDelProductActionPerformed

    private void btnEditProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditProductActionPerformed
        // TODO add your handling code here:
        System.out.println(Arrays.toString(tableProduct.getSelectedRows()));
        if (tableProduct.getSelectedRow() != -1) {
            int select = tableProduct.getSelectedRow();
            Product p = productsList.get(select);
            System.out.println(select);
            ProductViewUI editproduct = new ProductViewUI(EntityManager.mainUI, rootPaneCheckingEnabled, p);
            if (editproduct.isDataChange) {
                showProductTable();
            }
        }
    }//GEN-LAST:event_btnEditProductActionPerformed

    private void btnSearchProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchProductActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSearchProductActionPerformed

    private void editSearchProductKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_editSearchProductKeyReleased
        // TODO add your handling code here:
        dataProductChanged();
    }//GEN-LAST:event_editSearchProductKeyReleased

    private void cboxSituationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboxSituationActionPerformed
        // TODO add your handling code here:
        if (cboxSituation.getSelectedItem().equals("Sắp đến")) {
            lblTimeInProduct.setVisible(true);
            txtTimeInProduct.setVisible(true);
            txtTimeInProduct.setText(LocalDate.now().format(formatter));
        } else {
            lblTimeInProduct.setVisible(false);
            txtTimeInProduct.setVisible(false);
        }
        dataProductChanged();
    }//GEN-LAST:event_cboxSituationActionPerformed

    private void cboxSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboxSupplierActionPerformed
        // TODO add your handling code here:
        dataProductChanged();
    }//GEN-LAST:event_cboxSupplierActionPerformed

    private void txtTimeInProductKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimeInProductKeyReleased
        // TODO add your handling code here:
        dataProductChanged();
    }//GEN-LAST:event_txtTimeInProductKeyReleased

    private void cboxCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboxCategoryActionPerformed
        // TODO add your handling code here:
        dataProductChanged();
    }//GEN-LAST:event_cboxCategoryActionPerformed

    private void editSearchSupplierKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_editSearchSupplierKeyReleased
        // TODO add your handling code here:
        Set<String> searchSet = new HashSet();
        String[] keyISO = editSearchSupplier.getText().toLowerCase().trim().split(" ");
        for (String pn
                : supplierNameList) {
            for (String text : keyISO) {
                if (pn.toLowerCase().replaceAll(".,", "").contains(text)) {
                    searchSet.add(pn);
                }
            }
        }
        supplierNameModel.clear();
        supplierNameModel.addAll(searchSet);
//        lis
    }//GEN-LAST:event_editSearchSupplierKeyReleased

    private void txtSupNameBankActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSupNameBankActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSupNameBankActionPerformed

    private void txtCusNameBankActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCusNameBankActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCusNameBankActionPerformed

    private void txtCusAddrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCusAddrActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCusAddrActionPerformed

    private void txtFrdNameBankActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFrdNameBankActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFrdNameBankActionPerformed

    private void txtFrdAddrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFrdAddrActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFrdAddrActionPerformed

    private void txtSupPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSupPhoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSupPhoneActionPerformed

    private void btnSupEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSupEditActionPerformed
        // TODO add your handling code here:
        setEditingMode(true);

        supId = EntityManager.supplierDAO.getSupplierIdbyName(txtSupName.getText());

        btnSupSave.setText("Lưu");

        txtSupName.setEnabled(true);
        txtSupName.setBackground(Color.WHITE);
        txtSupName.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        txtSupPhone.setEnabled(true);
        txtSupPhone.setBackground(Color.WHITE);
        txtSupPhone.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        txtSupNameBank.setEnabled(true);
        txtSupNameBank.setBackground(Color.WHITE);
        txtSupNameBank.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        txtSupBankID.setEnabled(true);
        txtSupBankID.setBackground(Color.WHITE);
        txtSupBankID.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        txtSupBank.setEnabled(true);
        txtSupBank.setBackground(Color.WHITE);
        txtSupBank.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        txtSupAddr.setEnabled(true);
        txtSupAddr.setBackground(Color.WHITE);
        txtSupAddr.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

    }//GEN-LAST:event_btnSupEditActionPerformed

    private void btnSupCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSupCancelActionPerformed
        // TODO add your handling code here:
        setEditingMode(false);

        showSupplier(listSupplier.getSelectedValue());

        txtSupName.setEnabled(false);
        txtSupName.setBackground(new Color(240, 240, 240));
        txtSupName.setBorder(BorderFactory.createEmptyBorder());

        txtSupPhone.setEnabled(false);
        txtSupPhone.setBackground(new Color(240, 240, 240));
        txtSupPhone.setBorder(BorderFactory.createEmptyBorder());

        txtSupNameBank.setEnabled(false);
        txtSupNameBank.setBackground(new Color(240, 240, 240));
        txtSupNameBank.setBorder(BorderFactory.createEmptyBorder());

        txtSupBankID.setEnabled(false);
        txtSupBankID.setBackground(new Color(240, 240, 240));
        txtSupBankID.setBorder(BorderFactory.createEmptyBorder());

        txtSupBank.setEnabled(false);
        txtSupBank.setBackground(new Color(240, 240, 240));
        txtSupBank.setBorder(BorderFactory.createEmptyBorder());

        txtSupAddr.setEnabled(false);
        txtSupAddr.setBackground(new Color(240, 240, 240));
        txtSupAddr.setBorder(BorderFactory.createEmptyBorder());
    }//GEN-LAST:event_btnSupCancelActionPerformed

    private void btnSupSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSupSaveActionPerformed
        // TODO add your handling code here:
        if (!isAdding) {
            try {
                EntityManager.supplierDAO.updateSupplier(supId, txtSupName.getText(), txtSupPhone.getText(), txtSupAddr.getText(), txtSupNameBank.getText(), txtSupBankID.getText(), txtSupBank.getText());
                txtSupName.setEnabled(false);
                txtSupName.setBackground(new Color(240, 240, 240));
                txtSupName.setBorder(BorderFactory.createEmptyBorder());

                txtSupPhone.setEnabled(false);
                txtSupPhone.setBackground(new Color(240, 240, 240));
                txtSupPhone.setBorder(BorderFactory.createEmptyBorder());

                txtSupNameBank.setEnabled(false);
                txtSupNameBank.setBackground(new Color(240, 240, 240));
                txtSupNameBank.setBorder(BorderFactory.createEmptyBorder());

                txtSupBankID.setEnabled(false);
                txtSupBankID.setBackground(new Color(240, 240, 240));
                txtSupBankID.setBorder(BorderFactory.createEmptyBorder());

                txtSupBank.setEnabled(false);
                txtSupBank.setBackground(new Color(240, 240, 240));
                txtSupBank.setBorder(BorderFactory.createEmptyBorder());

                txtSupAddr.setEnabled(false);
                txtSupAddr.setBackground(new Color(240, 240, 240));
                txtSupAddr.setBorder(BorderFactory.createEmptyBorder());

                setEditingMode(false);

                supplierNameList = EntityManager.supplierDAO.getSuppliersName();
                supplierNameModel.removeAllElements();
                supplierNameModel.addAll(supplierNameList);
//            listSupplier.setModel(supplierNameModel);
                listSupplier.setSelectedValue(txtSupName.getText(), true);

            } catch (SQLException ex) {
                Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
                lblSupExist.setVisible(true);
            }
        } else {
            try {
                EntityManager.supplierDAO.insertProduct(txtSupName.getText(), txtSupPhone.getText(), txtSupAddr.getText(), txtSupNameBank.getText(), txtSupBankID.getText(), txtSupBank.getText());
                txtSupName.setEnabled(false);
                txtSupName.setBackground(new Color(240, 240, 240));
                txtSupName.setBorder(BorderFactory.createEmptyBorder());

                txtSupPhone.setEnabled(false);
                txtSupPhone.setBackground(new Color(240, 240, 240));
                txtSupPhone.setBorder(BorderFactory.createEmptyBorder());

                txtSupNameBank.setEnabled(false);
                txtSupNameBank.setBackground(new Color(240, 240, 240));
                txtSupNameBank.setBorder(BorderFactory.createEmptyBorder());

                txtSupBankID.setEnabled(false);
                txtSupBankID.setBackground(new Color(240, 240, 240));
                txtSupBankID.setBorder(BorderFactory.createEmptyBorder());

                txtSupBank.setEnabled(false);
                txtSupBank.setBackground(new Color(240, 240, 240));
                txtSupBank.setBorder(BorderFactory.createEmptyBorder());

                txtSupAddr.setEnabled(false);
                txtSupAddr.setBackground(new Color(240, 240, 240));
                txtSupAddr.setBorder(BorderFactory.createEmptyBorder());

                setEditingMode(false);

                supplierNameList = EntityManager.supplierDAO.getSuppliersName();
                supplierNameModel.removeAllElements();
                supplierNameModel.addAll(supplierNameList);

                listSupplier.setSelectedValue(txtSupName.getText(), true);

            } catch (SQLException ex) {
                Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
                lblSupExist.setVisible(true);
            }
        }
    }//GEN-LAST:event_btnSupSaveActionPerformed

    private void txtSupNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSupNameFocusGained
        // TODO add your handling code here:
        lblSupExist.setVisible(false);
    }//GEN-LAST:event_txtSupNameFocusGained

    private void btnCusDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCusDelActionPerformed
        try {
            // TODO add your handling code here:
            EntityManager.customerDAO.removeCustomer(cusId);

            customersNameList = EntityManager.customerDAO.getCustomersName();
            customerNameModel.removeAllElements();
            customerNameModel.addAll(customersNameList);
            txtCusName.setEnabled(false);
            txtCusName.setBackground(new Color(240, 240, 240));
            txtCusName.setBorder(BorderFactory.createEmptyBorder());

            txtCusPhone.setEnabled(false);
            txtCusPhone.setBackground(new Color(240, 240, 240));
            txtCusPhone.setBorder(BorderFactory.createEmptyBorder());

            txtCusNameBank.setEnabled(false);
            txtCusNameBank.setBackground(new Color(240, 240, 240));
            txtCusNameBank.setBorder(BorderFactory.createEmptyBorder());

            txtCusBankID.setEnabled(false);
            txtCusBankID.setBackground(new Color(240, 240, 240));
            txtCusBankID.setBorder(BorderFactory.createEmptyBorder());

            txtCusBank.setEnabled(false);
            txtCusBank.setBackground(new Color(240, 240, 240));
            txtCusBank.setBorder(BorderFactory.createEmptyBorder());

            txtCusAddr.setEnabled(false);
            txtCusAddr.setBackground(new Color(240, 240, 240));
            txtCusAddr.setBorder(BorderFactory.createEmptyBorder());
            setEditingMode(false);
            showCustomer(null);
        } catch (Exception ex) {
            Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(rootPane, "Khách hàng đã có hoá đơn, không thể xoá");
        }
    }//GEN-LAST:event_btnCusDelActionPerformed

    private void btnSupDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSupDelActionPerformed
        try {
            // TODO add your handling code here:
            EntityManager.supplierDAO.removeSupplier(supId);

            supplierNameList = EntityManager.supplierDAO.getSuppliersName();
            supplierNameModel.removeAllElements();
            supplierNameModel.addAll(supplierNameList);
            setEditingMode(false);
            txtSupName.setEnabled(false);
            txtSupName.setBackground(new Color(240, 240, 240));
            txtSupName.setBorder(BorderFactory.createEmptyBorder());

            txtSupPhone.setEnabled(false);
            txtSupPhone.setBackground(new Color(240, 240, 240));
            txtSupPhone.setBorder(BorderFactory.createEmptyBorder());

            txtSupNameBank.setEnabled(false);
            txtSupNameBank.setBackground(new Color(240, 240, 240));
            txtSupNameBank.setBorder(BorderFactory.createEmptyBorder());

            txtSupBankID.setEnabled(false);
            txtSupBankID.setBackground(new Color(240, 240, 240));
            txtSupBankID.setBorder(BorderFactory.createEmptyBorder());

            txtSupBank.setEnabled(false);
            txtSupBank.setBackground(new Color(240, 240, 240));
            txtSupBank.setBorder(BorderFactory.createEmptyBorder());

            txtSupAddr.setEnabled(false);
            txtSupAddr.setBackground(new Color(240, 240, 240));
            txtSupAddr.setBorder(BorderFactory.createEmptyBorder());
            showSupplier(null);
        } catch (Exception ex) {
            Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(rootPane, "Nhà cung cấp đã có hoá đơn, không thể xoá");
        }

    }//GEN-LAST:event_btnSupDelActionPerformed

    private void editSearchCustomerKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_editSearchCustomerKeyReleased
        // TODO add your handling code here:
        Set<String> searchSet = new HashSet();
        String[] keyISO = editSearchCustomer.getText().toLowerCase().trim().split(" ");
        for (String pn
                : customersNameList) {
            for (String text : keyISO) {
                if (pn.toLowerCase().replaceAll(".,", "").contains(text)) {
                    searchSet.add(pn);
                }
            }
        }
        customerNameModel.clear();
        customerNameModel.addAll(searchSet);
    }//GEN-LAST:event_editSearchCustomerKeyReleased

    private void editSearchFriendKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_editSearchFriendKeyReleased
        // TODO add your handling code here:
        Set<String> searchSet = new HashSet<String>();
        String[] keyISO = editSearchFriend.getText().toLowerCase().trim().split(" ");
        for (String pn
                : friendsNameList) {
            for (String text : keyISO) {
                if (pn.toLowerCase().replaceAll(".,", "").contains(text)) {
                    searchSet.add(pn);
                }
            }
        }
        friendsNameModel.clear();
        friendsNameModel.addAll(new ArrayList<String>(searchSet));
    }//GEN-LAST:event_editSearchFriendKeyReleased

    private void btnCusEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCusEditActionPerformed
        // TODO add your handling code here:
        setEditingMode(true);

        cusId = EntityManager.customerDAO.getCustomerIdbyName(txtCusName.getText());

        btnCusSave.setText("Lưu");

        txtCusName.setEnabled(true);
        txtCusName.setBackground(Color.WHITE);
        txtCusName.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        txtCusPhone.setEnabled(true);
        txtCusPhone.setBackground(Color.WHITE);
        txtCusPhone.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        txtCusNameBank.setEnabled(true);
        txtCusNameBank.setBackground(Color.WHITE);
        txtCusNameBank.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        txtCusBankID.setEnabled(true);
        txtCusBankID.setBackground(Color.WHITE);
        txtCusBankID.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        txtCusBank.setEnabled(true);
        txtCusBank.setBackground(Color.WHITE);
        txtCusBank.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        txtCusAddr.setEnabled(true);
        txtCusAddr.setBackground(Color.WHITE);
        txtCusAddr.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
    }//GEN-LAST:event_btnCusEditActionPerformed

    private void btnFrdEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFrdEditActionPerformed
        // TODO add your handling code here:
        setEditingMode(true);

        friendId = EntityManager.friendAgencyDAO.getFriendIdbyName(txtFrdName.getText());

        btnFrdSave.setText("Lưu");

        txtFrdName.setEnabled(true);
        txtFrdName.setBackground(Color.WHITE);
        txtFrdName.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        txtFrdPhone.setEnabled(true);
        txtFrdPhone.setBackground(Color.WHITE);
        txtFrdPhone.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        txtFrdNameBank.setEnabled(true);
        txtFrdNameBank.setBackground(Color.WHITE);
        txtFrdNameBank.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        txtFrdBankID.setEnabled(true);
        txtFrdBankID.setBackground(Color.WHITE);
        txtFrdBankID.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        txtFrdBank.setEnabled(true);
        txtFrdBank.setBackground(Color.WHITE);
        txtFrdBank.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        txtFrdAddr.setEnabled(true);
        txtFrdAddr.setBackground(Color.WHITE);
        txtFrdAddr.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
    }//GEN-LAST:event_btnFrdEditActionPerformed

    private void btnFrdDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFrdDelActionPerformed
        try {
            // TODO add your handling code here:
            EntityManager.friendAgencyDAO.removeFriend(friendId);

            friendsNameList = EntityManager.friendAgencyDAO.getFriendAgencysName();
            friendsNameModel.removeAllElements();
            friendsNameModel.addAll(friendsNameList);
            txtFrdName.setEnabled(false);
            txtFrdName.setBackground(new Color(240, 240, 240));
            txtFrdName.setBorder(BorderFactory.createEmptyBorder());

            txtFrdPhone.setEnabled(false);
            txtFrdPhone.setBackground(new Color(240, 240, 240));
            txtFrdPhone.setBorder(BorderFactory.createEmptyBorder());

            txtFrdNameBank.setEnabled(false);
            txtFrdNameBank.setBackground(new Color(240, 240, 240));
            txtFrdNameBank.setBorder(BorderFactory.createEmptyBorder());

            txtFrdBankID.setEnabled(false);
            txtFrdBankID.setBackground(new Color(240, 240, 240));
            txtFrdBankID.setBorder(BorderFactory.createEmptyBorder());

            txtFrdBank.setEnabled(false);
            txtFrdBank.setBackground(new Color(240, 240, 240));
            txtFrdBank.setBorder(BorderFactory.createEmptyBorder());

            txtFrdAddr.setEnabled(false);
            txtFrdAddr.setBackground(new Color(240, 240, 240));
            txtFrdAddr.setBorder(BorderFactory.createEmptyBorder());
            setEditingMode(false);
            showFriend(null);
        } catch (Exception ex) {
            Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(rootPane, "Đại lý bạn đã có hoá đơn, không thể xoá");
        }
    }//GEN-LAST:event_btnFrdDelActionPerformed

    private void btnCusSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCusSaveActionPerformed
        // TODO add your handling code here:
        if (!isAdding) {
            try {
                EntityManager.customerDAO.updateCustomer(cusId, txtCusName.getText(), txtCusPhone.getText(), txtCusAddr.getText(), txtCusNameBank.getText(), txtCusBankID.getText(), txtCusBank.getText());
                txtCusName.setEnabled(false);
                txtCusName.setBackground(new Color(240, 240, 240));
                txtCusName.setBorder(BorderFactory.createEmptyBorder());

                txtCusPhone.setEnabled(false);
                txtCusPhone.setBackground(new Color(240, 240, 240));
                txtCusPhone.setBorder(BorderFactory.createEmptyBorder());

                txtCusNameBank.setEnabled(false);
                txtCusNameBank.setBackground(new Color(240, 240, 240));
                txtCusNameBank.setBorder(BorderFactory.createEmptyBorder());

                txtCusBankID.setEnabled(false);
                txtCusBankID.setBackground(new Color(240, 240, 240));
                txtCusBankID.setBorder(BorderFactory.createEmptyBorder());

                txtCusBank.setEnabled(false);
                txtCusBank.setBackground(new Color(240, 240, 240));
                txtCusBank.setBorder(BorderFactory.createEmptyBorder());

                txtCusAddr.setEnabled(false);
                txtCusAddr.setBackground(new Color(240, 240, 240));
                txtCusAddr.setBorder(BorderFactory.createEmptyBorder());

                setEditingMode(false);

                customersNameList = EntityManager.customerDAO.getCustomersName();
                customerNameModel.removeAllElements();
                customerNameModel.addAll(customersNameList);
                listCustomer.setSelectedValue(txtCusName.getText(), true);

            } catch (SQLException ex) {
                Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
                lblCusExist.setVisible(true);
            }
        } else {
            try {
                EntityManager.customerDAO.insertCustomer(txtCusName.getText(), txtCusPhone.getText(), txtCusAddr.getText(), txtCusNameBank.getText(), txtCusBankID.getText(), txtCusBank.getText());
                txtCusName.setEnabled(false);
                txtCusName.setBackground(new Color(240, 240, 240));
                txtCusName.setBorder(BorderFactory.createEmptyBorder());

                txtCusPhone.setEnabled(false);
                txtCusPhone.setBackground(new Color(240, 240, 240));
                txtCusPhone.setBorder(BorderFactory.createEmptyBorder());

                txtCusNameBank.setEnabled(false);
                txtCusNameBank.setBackground(new Color(240, 240, 240));
                txtCusNameBank.setBorder(BorderFactory.createEmptyBorder());

                txtCusBankID.setEnabled(false);
                txtCusBankID.setBackground(new Color(240, 240, 240));
                txtCusBankID.setBorder(BorderFactory.createEmptyBorder());

                txtCusBank.setEnabled(false);
                txtCusBank.setBackground(new Color(240, 240, 240));
                txtCusBank.setBorder(BorderFactory.createEmptyBorder());

                txtCusAddr.setEnabled(false);
                txtCusAddr.setBackground(new Color(240, 240, 240));
                txtCusAddr.setBorder(BorderFactory.createEmptyBorder());

                setEditingMode(false);

                customersNameList = EntityManager.customerDAO.getCustomersName();
                customerNameModel.removeAllElements();
                customerNameModel.addAll(customersNameList);
                listCustomer.setSelectedValue(txtCusName.getText(), true);

            } catch (SQLException ex) {
                Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
                lblCusExist.setVisible(true);
            }
        }
    }//GEN-LAST:event_btnCusSaveActionPerformed

    private void txtCusNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCusNameFocusGained
        // TODO add your handling code here:
        lblCusExist.setVisible(false);
    }//GEN-LAST:event_txtCusNameFocusGained

    private void txtFrdNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFrdNameFocusGained
        // TODO add your handling code here:
        lblFrdExist.setVisible(false);
    }//GEN-LAST:event_txtFrdNameFocusGained

    private void btnCusCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCusCancelActionPerformed
        // TODO add your handling code here:
        setEditingMode(false);

        showCustomer(listCustomer.getSelectedValue());

        txtCusName.setEnabled(false);
        txtCusName.setBackground(new Color(240, 240, 240));
        txtCusName.setBorder(BorderFactory.createEmptyBorder());

        txtCusPhone.setEnabled(false);
        txtCusPhone.setBackground(new Color(240, 240, 240));
        txtCusPhone.setBorder(BorderFactory.createEmptyBorder());

        txtCusNameBank.setEnabled(false);
        txtCusNameBank.setBackground(new Color(240, 240, 240));
        txtCusNameBank.setBorder(BorderFactory.createEmptyBorder());

        txtCusBankID.setEnabled(false);
        txtCusBankID.setBackground(new Color(240, 240, 240));
        txtCusBankID.setBorder(BorderFactory.createEmptyBorder());

        txtCusBank.setEnabled(false);
        txtCusBank.setBackground(new Color(240, 240, 240));
        txtCusBank.setBorder(BorderFactory.createEmptyBorder());

        txtCusAddr.setEnabled(false);
        txtCusAddr.setBackground(new Color(240, 240, 240));
        txtCusAddr.setBorder(BorderFactory.createEmptyBorder());
    }//GEN-LAST:event_btnCusCancelActionPerformed

    private void btnFrdCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFrdCancelActionPerformed
        // TODO add your handling code here:
        setEditingMode(false);

        showFriend(listFriend.getSelectedValue());

        txtFrdName.setEnabled(false);
        txtFrdName.setBackground(new Color(240, 240, 240));
        txtFrdName.setBorder(BorderFactory.createEmptyBorder());

        txtFrdPhone.setEnabled(false);
        txtFrdPhone.setBackground(new Color(240, 240, 240));
        txtFrdPhone.setBorder(BorderFactory.createEmptyBorder());

        txtFrdNameBank.setEnabled(false);
        txtFrdNameBank.setBackground(new Color(240, 240, 240));
        txtFrdNameBank.setBorder(BorderFactory.createEmptyBorder());

        txtFrdBankID.setEnabled(false);
        txtFrdBankID.setBackground(new Color(240, 240, 240));
        txtFrdBankID.setBorder(BorderFactory.createEmptyBorder());

        txtFrdBank.setEnabled(false);
        txtFrdBank.setBackground(new Color(240, 240, 240));
        txtFrdBank.setBorder(BorderFactory.createEmptyBorder());

        txtFrdAddr.setEnabled(false);
        txtFrdAddr.setBackground(new Color(240, 240, 240));
        txtFrdAddr.setBorder(BorderFactory.createEmptyBorder());
    }//GEN-LAST:event_btnFrdCancelActionPerformed

    private void btnFrdSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFrdSaveActionPerformed
        // TODO add your handling code here:
        if (!isAdding) {
            try {
                EntityManager.friendAgencyDAO.updateFriend(friendId, txtFrdName.getText(), txtFrdPhone.getText(), txtFrdAddr.getText(), txtFrdNameBank.getText(), txtFrdBankID.getText(), txtFrdBank.getText());
                txtFrdName.setEnabled(false);
                txtFrdName.setBackground(new Color(240, 240, 240));
                txtFrdName.setBorder(BorderFactory.createEmptyBorder());

                txtFrdPhone.setEnabled(false);
                txtFrdPhone.setBackground(new Color(240, 240, 240));
                txtFrdPhone.setBorder(BorderFactory.createEmptyBorder());

                txtFrdNameBank.setEnabled(false);
                txtFrdNameBank.setBackground(new Color(240, 240, 240));
                txtFrdNameBank.setBorder(BorderFactory.createEmptyBorder());

                txtFrdBankID.setEnabled(false);
                txtFrdBankID.setBackground(new Color(240, 240, 240));
                txtFrdBankID.setBorder(BorderFactory.createEmptyBorder());

                txtFrdBank.setEnabled(false);
                txtFrdBank.setBackground(new Color(240, 240, 240));
                txtFrdBank.setBorder(BorderFactory.createEmptyBorder());

                txtFrdAddr.setEnabled(false);
                txtFrdAddr.setBackground(new Color(240, 240, 240));
                txtFrdAddr.setBorder(BorderFactory.createEmptyBorder());

                setEditingMode(false);

                friendsNameList = EntityManager.friendAgencyDAO.getFriendAgencysName();
                friendsNameModel.removeAllElements();
                friendsNameModel.addAll(friendsNameList);
                listFriend.setSelectedValue(txtFrdName.getText(), true);

            } catch (SQLException ex) {
                Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
                lblFrdExist.setVisible(true);
            }
        } else {
            try {
                EntityManager.friendAgencyDAO.insertFriend(txtFrdName.getText(), txtFrdPhone.getText(), txtFrdAddr.getText(), txtFrdNameBank.getText(), txtFrdBankID.getText(), txtFrdBank.getText());
                txtFrdName.setEnabled(false);
                txtFrdName.setBackground(new Color(240, 240, 240));
                txtFrdName.setBorder(BorderFactory.createEmptyBorder());

                txtFrdPhone.setEnabled(false);
                txtFrdPhone.setBackground(new Color(240, 240, 240));
                txtFrdPhone.setBorder(BorderFactory.createEmptyBorder());

                txtFrdNameBank.setEnabled(false);
                txtFrdNameBank.setBackground(new Color(240, 240, 240));
                txtFrdNameBank.setBorder(BorderFactory.createEmptyBorder());

                txtFrdBankID.setEnabled(false);
                txtFrdBankID.setBackground(new Color(240, 240, 240));
                txtFrdBankID.setBorder(BorderFactory.createEmptyBorder());

                txtFrdBank.setEnabled(false);
                txtFrdBank.setBackground(new Color(240, 240, 240));
                txtFrdBank.setBorder(BorderFactory.createEmptyBorder());

                txtFrdAddr.setEnabled(false);
                txtFrdAddr.setBackground(new Color(240, 240, 240));
                txtFrdAddr.setBorder(BorderFactory.createEmptyBorder());

                setEditingMode(false);

                friendsNameList = EntityManager.friendAgencyDAO.getFriendAgencysName();
                friendsNameModel.removeAllElements();
                friendsNameModel.addAll(friendsNameList);
                listFriend.setSelectedValue(txtFrdName.getText(), true);

            } catch (SQLException ex) {
                Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
                lblFrdExist.setVisible(true);
            }
        }
    }//GEN-LAST:event_btnFrdSaveActionPerformed

    private void listCustomerValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listCustomerValueChanged
        // TODO add your handling code here:
        String custumerName = listCustomer.getSelectedValue();
        showCustomer(custumerName);
        showSalesInvoiceTree(EntityManager.customerDAO.getCustomerIdbyName(custumerName));
    }//GEN-LAST:event_listCustomerValueChanged

    private void listSupplierValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listSupplierValueChanged
        // TODO add your handling code here:
        String supplierName = listSupplier.getSelectedValue();
        showSupplier(supplierName);
        showImportInvoiceTree(EntityManager.supplierDAO.getSupplierIdbyName(supplierName));
    }//GEN-LAST:event_listSupplierValueChanged

    private void listFriendValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listFriendValueChanged
        // TODO add your handling code here:
        String friendName = listFriend.getSelectedValue();
        showFriend(friendName);
        showLoanInvoiceTree(EntityManager.friendAgencyDAO.getFriendIdbyName(friendName));
    }//GEN-LAST:event_listFriendValueChanged
    public void showProductTable() {
        productsList = EntityManager.productDAO.getAllProducts();
        showProductTable(productsList);
    }

    public void showProductTable(List<Product> eList) {
        clearProductTable();
        for (int i = 0; i < eList.size(); i++) {
            Product p = eList.get(i);
            if ((p.getImportPrice() >= Double.parseDouble(String.valueOf(lblMinPrice.getText())))
                    && (p.getImportPrice() <= Double.parseDouble(String.valueOf(lblMaxPrice.getText())))) {
                Vector vctRow = new Vector();
                vctRow.add(p.getId());
                vctRow.add(p.getName());
                vctRow.add(p.getCategory());
                vctRow.add(p.getType());
                vctRow.add(p.getQuantity());
                vctRow.add(p.getUnit());
                vctRow.add(EntityManager.supplierDAO.getSupplierNameById(p.getSupplierId()));
                vctRow.add(p.getImportPrice());
                vctRow.add(p.getRetailPrice());
                vctRow.add(new Date(p.getImportDate().getTime()).toLocalDate().format(formatter));
                vctProductsData.add(vctRow);
            }
        }
        productsModel = new ProductTableModel(vctProductsData, vctProductsHeader) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // or a condition at your choice with row and column
            }
        };
        tableProduct.setModel(productsModel);
        TableColumnModel columnModel = tableProduct.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(50);
        columnModel.getColumn(4).setMaxWidth(60);
        columnModel.getColumn(5).setMaxWidth(70);
        columnModel.getColumn(9).setMinWidth(120);

    }

    public void clearProductTable() {
        productsModel.getDataVector().removeAllElements();
        productsModel.fireTableDataChanged();
    }

    public void showImportInvoiceTree(int supplierid) {
        importInvoicesList = EntityManager.invoiceDAO.getImportInvoices(supplierid);
        showImportInvoiceTree(importInvoicesList);
    }

    public void showSalesInvoiceTree(int customerid) {
        salesInvoicesList = EntityManager.invoiceDAO.getSalesInvoices(customerid);
        showSalesInvoiceTree(salesInvoicesList);
    }

    public void showLoanInvoiceTree(int friend_agencyid) {
        loanInvoicesList = EntityManager.invoiceDAO.getLoanInvoices(friend_agencyid);
        showLoanInvoiceTree(loanInvoicesList);
    }

    public void showImportInvoiceTree(List<ImportInvoice> invoiceList) {
        clearTree(importRoot);
        ImportInvoice detail;
        DefaultMutableTreeNode invoiceNode;
        DefaultMutableTreeNode productNode;
        for (int i = 0; i < invoiceList.size(); i++) {
            detail = EntityManager.invoiceDAO.getImportInvoiceDetail(invoiceList.get(i).getId());
            invoiceNode = new DefaultMutableTreeNode(detail);
            for (Product p : detail.getSoldProducts()) {
                ShowProductModel show = new ShowProductModel(p.getId(), p.getName(), p.getQuantity(), p.getQuantity() * p.getRetailPrice());
                productNode = new DefaultMutableTreeNode(show);
                invoiceNode.add(productNode);
            }
            importRoot.add(invoiceNode);
        }
        treeImportModel.reload();
    }

    public void showLoanInvoiceTree(List<LoanInvoice> invoiceList) {
        clearTree(loanRoot);
        LoanInvoice detail;
        DefaultMutableTreeNode invoiceNode;
        DefaultMutableTreeNode productNode;
        for (int i = 0; i < invoiceList.size(); i++) {
            detail = EntityManager.invoiceDAO.getLoanInvoiceDetail(invoiceList.get(i).getId());
            invoiceNode = new DefaultMutableTreeNode(detail);
            for (Product p : detail.getSoldProducts()) {
                ShowProductModel show = new ShowProductModel(p.getId(), p.getName(), p.getQuantity(), p.getQuantity() * p.getRetailPrice());
                productNode = new DefaultMutableTreeNode(show);
                invoiceNode.add(productNode);
            }
            loanRoot.add(invoiceNode);
        }
        treeLoanModel.reload();
    }

    public void showSalesInvoiceTree(List<SalesInvoice> invoiceList) {
        clearTree(saleRoot);
        SalesInvoice detail;
        DefaultMutableTreeNode invoiceNode;
        DefaultMutableTreeNode productNode;
        for (int i = 0; i < invoiceList.size(); i++) {
            detail = EntityManager.invoiceDAO.getSalesInvoiceDetail(invoiceList.get(i).getId());
            invoiceNode = new DefaultMutableTreeNode(detail);
            for (Product p : detail.getSoldProducts()) {
                ShowProductModel show = new ShowProductModel(p.getId(), p.getName(), p.getQuantity(), p.getQuantity() * p.getRetailPrice());
                productNode = new DefaultMutableTreeNode(show);
                invoiceNode.add(productNode);
            }
            saleRoot.add(invoiceNode);
        }
        treeSaleModel.reload();
    }

    public void clearTable(DefaultTableModel table) {
        table.getDataVector().removeAllElements();
        table.fireTableDataChanged();
    }

    public void clearTree(DefaultMutableTreeNode root) {
        root.removeAllChildren();
    }

    public void showResult() {
        productsModel = new ProductTableModel(vctProductsData, vctProductsHeader);
        tableProduct.setModel(productsModel);

        showProductTable();

        salesInvoicesModel = new DefaultTableModel(null, vctSalesInvoicesHeader);
        tableSalesInvoice.setModel(salesInvoicesModel);
        TableColumnModel columnModelCus = tableSalesInvoice.getColumnModel();
        columnModelCus.getColumn(0).setMaxWidth(50);

        importInvoicesModel = new DefaultTableModel(null, vctImportInvoicesHeader);
        tableImportInvoice.setModel(importInvoicesModel);
        TableColumnModel columnModelSup = tableImportInvoice.getColumnModel();
        columnModelSup.getColumn(0).setMaxWidth(50);

        loanInvoicesModel = new DefaultTableModel(null, vctLoanInvoicesHeader);
        tableLoanInvoice.setModel(loanInvoicesModel);
        TableColumnModel columnModelFrd = tableLoanInvoice.getColumnModel();
        columnModelFrd.getColumn(0).setMaxWidth(50);

    }

    public void dataProductChanged() {
        String category = null;
        String supplier = null;
        Date date = null;
        boolean isAvailable = false;
        if (!cboxSituation.getSelectedItem().equals("Tất cả")) {
            isAvailable = true;
            if (cboxSituation.getSelectedItem().equals("Trong kho")) {
                date = Date.valueOf(LocalDate.now());
            } else {
                date = Date.valueOf(LocalDate.parse(txtTimeInProduct.getText(), formatter));
            }
        }
        if (!cboxCategory.getSelectedItem().equals("Tất cả")) {
            category = (String) cboxCategory.getSelectedItem();
        }
        if (!cboxSupplier.getSelectedItem().equals("Tất cả")) {
            supplier = (String) cboxSupplier.getSelectedItem();
        }
        productsList = EntityManager.productDAO.getAllProductsByKey(editSearchProduct.getText().trim(), supplier, category, date, isAvailable);
        showProductTable(productsList);
    }

    void setEditingMode(boolean isEditting) {
        listCustomer.setEnabled(!isEditting);
        listFriend.setEnabled(!isEditting);
        listSupplier.setEnabled(!isEditting);
        btnAddSupplier.setEnabled(!isEditting);
        btnAddCustomer.setEnabled(!isEditting);
        btnAddFriend.setEnabled(!isEditting);
        editSearchSupplier.setEditable(!isEditting);
        editSearchCustomer.setEditable(!isEditting);
        editSearchFriend.setEditable(!isEditting);

        btnSupSave.setVisible(isEditting);
        btnSupCancel.setVisible(isEditting);
        btnSupDel.setVisible(isEditting);
        btnSupEdit.setVisible(!isEditting);

        btnCusSave.setVisible(isEditting);
        btnCusCancel.setVisible(isEditting);
        btnCusDel.setVisible(isEditting);
        btnCusEdit.setVisible(!isEditting);

        btnFrdSave.setVisible(isEditting);
        btnFrdCancel.setVisible(isEditting);
        btnFrdDel.setVisible(isEditting);
        btnFrdEdit.setVisible(!isEditting);

    }

    void showSupplier(int id) {
        Supplier s = EntityManager.supplierDAO.getSupplier(id);
        txtSupID.setText(Integer.toString(s.getId()));
        txtSupName.setText(s.getName());
        txtSupPhone.setText(s.getPhoneNumbers().get(0));
        txtSupNameBank.setText(s.getPayments().get(0).getName());
        txtSupBankID.setText(s.getPayments().get(0).getBankID());
        txtSupBank.setText(s.getPayments().get(0).getBank());
        txtSupAddr.setText(s.getAddresses().get(0).toString());
    }

    void showSupplier(String supplierName) {
        if (supplierName == null) {
            txtSupID.setText("");
            txtSupName.setText("");
            txtSupPhone.setText("");
            txtSupNameBank.setText("");
            txtSupBankID.setText("");
            txtSupBank.setText("");
            txtSupAddr.setText("");
            return;
        }
        Supplier s = EntityManager.supplierDAO.getSupplier(supplierName);
        txtSupID.setText(Integer.toString(s.getId()));
        txtSupName.setText(s.getName());
        txtSupPhone.setText(s.getPhoneNumbers().get(0));
        txtSupNameBank.setText(s.getPayments().get(0).getName());
        txtSupBankID.setText(s.getPayments().get(0).getBankID());
        txtSupBank.setText(s.getPayments().get(0).getBank());
        txtSupAddr.setText(s.getAddresses().get(0).toString());
    }

    void showCustomer(int id) {
        Customer s = EntityManager.customerDAO.getCustomer(id);
        txtCusID.setText(Integer.toString(s.getId()));
        txtCusName.setText(s.getName());
        txtCusPhone.setText(s.getPhoneNumbers().get(0));
        txtCusNameBank.setText(s.getPayments().get(0).getName());
        txtCusBankID.setText(s.getPayments().get(0).getBankID());
        txtCusBank.setText(s.getPayments().get(0).getBank());
        txtCusAddr.setText(s.getAddresses().get(0).toString());
    }

    void showCustomer(String customerName) {
        if (customerName == null) {
            txtCusID.setText("");
            txtCusName.setText("");
            txtCusPhone.setText("");
            txtCusNameBank.setText("");
            txtCusBankID.setText("");
            txtCusBank.setText("");
            txtCusAddr.setText("");
            return;
        }
        Customer s = EntityManager.customerDAO.getCustomer(customerName);
        txtCusID.setText(Integer.toString(s.getId()));
        txtCusName.setText(s.getName());
        txtCusPhone.setText(s.getPhoneNumbers().get(0));
        txtCusNameBank.setText(s.getPayments().get(0).getName());
        txtCusBankID.setText(s.getPayments().get(0).getBankID());
        txtCusBank.setText(s.getPayments().get(0).getBank());
        txtCusAddr.setText(s.getAddresses().get(0).toString());
    }

    void showFriend(int id) {
        FriendAgency s = EntityManager.friendAgencyDAO.getFriendAgency(id);
        txtFrdID.setText(Integer.toString(s.getId()));
        txtFrdName.setText(s.getName());
        txtFrdPhone.setText(s.getPhoneNumbers().get(0));
        txtFrdNameBank.setText(s.getPayments().get(0).getName());
        txtFrdBankID.setText(s.getPayments().get(0).getBankID());
        txtFrdBank.setText(s.getPayments().get(0).getBank());
        txtFrdAddr.setText(s.getAddresses().get(0).toString());
    }

    void showFriend(String friendName) {
        if (friendName == null) {
            txtFrdID.setText("");
            txtFrdName.setText("");
            txtFrdPhone.setText("");
            txtFrdNameBank.setText("");
            txtFrdBankID.setText("");
            txtFrdBank.setText("");
            txtFrdAddr.setText("");
            return;
        }
        FriendAgency s = EntityManager.friendAgencyDAO.getFriendAgency(friendName);
        txtFrdID.setText(Integer.toString(s.getId()));
        txtFrdName.setText(s.getName());
        txtFrdPhone.setText(s.getPhoneNumbers().get(0));
        txtFrdNameBank.setText(s.getPayments().get(0).getName());
        txtFrdBankID.setText(s.getPayments().get(0).getBankID());
        txtFrdBank.setText(s.getPayments().get(0).getBank());
        txtFrdAddr.setText(s.getAddresses().get(0).toString());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddCustomer;
    private javax.swing.JButton btnAddFriend;
    private javax.swing.JButton btnAddProduct;
    private javax.swing.JButton btnAddSupplier;
    private javax.swing.JButton btnCusCancel;
    private javax.swing.JButton btnCusDel;
    private javax.swing.JButton btnCusEdit;
    private javax.swing.JButton btnCusSave;
    private javax.swing.JButton btnDelProduct;
    private javax.swing.JButton btnEditProduct;
    private javax.swing.JButton btnFrdCancel;
    private javax.swing.JButton btnFrdDel;
    private javax.swing.JButton btnFrdEdit;
    private javax.swing.JButton btnFrdSave;
    private javax.swing.JButton btnSearchProduct;
    private javax.swing.JButton btnSupCancel;
    private javax.swing.JButton btnSupDel;
    private javax.swing.JButton btnSupEdit;
    private javax.swing.JButton btnSupSave;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboxCategory;
    private javax.swing.JComboBox<String> cboxSituation;
    private javax.swing.JComboBox<String> cboxSupplier;
    private javax.swing.JTextField editSearchCustomer;
    private javax.swing.JTextField editSearchFriend;
    private javax.swing.JTextField editSearchProduct;
    private javax.swing.JTextField editSearchSupplier;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel lblCusExist;
    private javax.swing.JLabel lblFrdExist;
    private javax.swing.JLabel lblMaxPrice;
    private javax.swing.JLabel lblMinPrice;
    private javax.swing.JLabel lblSupExist;
    private javax.swing.JLabel lblTimeInProduct;
    private javax.swing.JList<String> listCustomer;
    private javax.swing.JList<String> listFriend;
    private javax.swing.JList<String> listSupplier;
    private javax.swing.JPanel panelCusInfor;
    private javax.swing.JPanel panelFrdInfor;
    private javax.swing.JPanel panelSupInfor;
    private javax.swing.JPanel sliderPane;
    private javax.swing.JTable tableImportInvoice;
    private javax.swing.JTable tableLoanInvoice;
    private javax.swing.JTable tableProduct;
    private javax.swing.JTable tableSalesInvoice;
    private javax.swing.JTree treeImport;
    private javax.swing.JTree treeLoan;
    private javax.swing.JTree treeSale;
    private javax.swing.JTextField txtCusAddr;
    private javax.swing.JTextField txtCusBank;
    private javax.swing.JTextField txtCusBankID;
    private javax.swing.JTextField txtCusID;
    private javax.swing.JTextField txtCusName;
    private javax.swing.JTextField txtCusNameBank;
    private javax.swing.JTextField txtCusPhone;
    private javax.swing.JTextField txtFrdAddr;
    private javax.swing.JTextField txtFrdBank;
    private javax.swing.JTextField txtFrdBankID;
    private javax.swing.JTextField txtFrdID;
    private javax.swing.JTextField txtFrdName;
    private javax.swing.JTextField txtFrdNameBank;
    private javax.swing.JTextField txtFrdPhone;
    private javax.swing.JTextField txtSupAddr;
    private javax.swing.JTextField txtSupBank;
    private javax.swing.JTextField txtSupBankID;
    private javax.swing.JTextField txtSupID;
    private javax.swing.JTextField txtSupName;
    private javax.swing.JTextField txtSupNameBank;
    private javax.swing.JTextField txtSupPhone;
    private javax.swing.JTextField txtTimeInProduct;
    // End of variables declaration//GEN-END:variables
}
