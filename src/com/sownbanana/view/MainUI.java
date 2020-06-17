/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sownbanana.view;

import com.sownbanana.connection.EntityManager;
import com.sownbanana.controller.ProductController;
import com.sownbanana.model.Customer;
import com.sownbanana.model.ImportInvoice;
import com.sownbanana.model.LoanInvoice;
import com.sownbanana.model.Product;
import com.sownbanana.model.ProductTableModel;
import com.sownbanana.model.SalesInvoice;
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
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;

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
    List<SalesInvoice> salesInvoicesList;
    DefaultTableModel salesInvoicesModel;
    Vector vctSalesInvoicesHeader = new Vector();
    Vector vctSalesInvoicesData = new Vector();

    //Supplier
    DefaultListModel<String> supplierNameModel;
    List<Supplier> suppliersList;
    List<String> supplierNameList;
    List<ImportInvoice> importInvoicesList;
    DefaultTableModel importInvoicesModel;
    Vector vctImportInvoicesHeader = new Vector();
    Vector vctImportInvoicesData = new Vector();
    
    

    //Friend Agency
    List<Product> friendsList;
    List<LoanInvoice> loanInvoicesList;
    DefaultTableModel loanInvoicesModel;
    Vector vctLoanInvoicesHeader = new Vector();
    Vector vctLoanInvoicesData = new Vector();

    /**
     * Creates new form MainUI
     */
    public MainUI() {
        initComponents();

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

        //Supplier Tab Init
        vctImportInvoicesHeader.add("ID");
        vctImportInvoicesHeader.add("Ngày đặt hàng");
        vctImportInvoicesHeader.add("Ngày giao hàng");
        vctImportInvoicesHeader.add("Tổng chi");
        vctImportInvoicesHeader.add("Đã chi");
        vctImportInvoicesHeader.add("Ghi chú");

        //Friend Agency Tab Init
        vctLoanInvoicesHeader.add("ID");
        vctLoanInvoicesHeader.add("Ngày đặt hàng");
        vctLoanInvoicesHeader.add("Ngày giao hàng");
        vctLoanInvoicesHeader.add("Tổng giá");
        vctLoanInvoicesHeader.add("Đã thanh toán");
        vctLoanInvoicesHeader.add("Ghi chú");

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
        
        //List supplier
        supplierNameList = EntityManager.supplierDAO.getSuppliersName();
        supplierNameModel = new DefaultListModel<>();
        supplierNameModel.addAll(supplierNameList);
        listSupplier.setModel(supplierNameModel);
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
        jLabel19 = new javax.swing.JLabel();
        txtCusID = new javax.swing.JTextField();
        txtCusName = new javax.swing.JTextField();
        txtCusPhone = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtCusBankID = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtCusBank = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableSalesInvoice = new javax.swing.JTable();
        btnCusCancel = new javax.swing.JButton();
        btnCusSave = new javax.swing.JButton();
        btnCusEdit = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        editSearchCustomer = new javax.swing.JTextField();
        btnAllCustomer = new javax.swing.JButton();
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
        txtNameBank = new javax.swing.JTextField();
        btnAllSupplier = new javax.swing.JButton();
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
        jLabel12 = new javax.swing.JLabel();
        txtFrdID = new javax.swing.JTextField();
        txtFrdName = new javax.swing.JTextField();
        txtFrdPhone = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtFrdBankID = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtFrdBank = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableLoanInvoice = new javax.swing.JTable();
        btnFrdCancel = new javax.swing.JButton();
        btnFrdSave = new javax.swing.JButton();
        btnFrdEdit = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        btnAllFriend = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
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
            .addGap(0, 563, Short.MAX_VALUE)
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

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel19.setText("Địa chỉ:");

        txtCusID.setBackground(new java.awt.Color(240, 240, 240));
        txtCusID.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtCusID.setText("00001");
        txtCusID.setBorder(null);
        txtCusID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCusIDActionPerformed(evt);
            }
        });

        txtCusName.setBackground(new java.awt.Color(240, 240, 240));
        txtCusName.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtCusName.setText("Kiên");
        txtCusName.setBorder(null);

        txtCusPhone.setBackground(new java.awt.Color(240, 240, 240));
        txtCusPhone.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtCusPhone.setText("0986414972");
        txtCusPhone.setBorder(null);

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel20.setText("Tài khoản:");

        txtCusBankID.setBackground(new java.awt.Color(240, 240, 240));
        txtCusBankID.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtCusBankID.setText("786 438 532 432");
        txtCusBankID.setBorder(null);

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel21.setText("Ngân hàng:");

        txtCusBank.setBackground(new java.awt.Color(240, 240, 240));
        txtCusBank.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtCusBank.setText("VietinBank Nghệ An");
        txtCusBank.setBorder(null);

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

        btnCusSave.setText("Lưu");

        btnCusEdit.setText("Chỉnh sửa thông tin");

        jLabel25.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel25.setText("Lịch sử mua hàng");

        jTextField3.setBackground(new java.awt.Color(240, 240, 240));
        jTextField3.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jTextField3.setText("Thôn Gì Đó, xã Gì Đó, huyện Gì Đó, tỉnh Gì đó ");
        jTextField3.setBorder(null);

        javax.swing.GroupLayout panelCusInforLayout = new javax.swing.GroupLayout(panelCusInfor);
        panelCusInfor.setLayout(panelCusInforLayout);
        panelCusInforLayout.setHorizontalGroup(
            panelCusInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCusInforLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(panelCusInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18)
                    .addComponent(jLabel20)
                    .addComponent(jLabel19))
                .addGap(23, 23, 23)
                .addGroup(panelCusInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCusInforLayout.createSequentialGroup()
                        .addGroup(panelCusInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelCusInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(panelCusInforLayout.createSequentialGroup()
                                    .addComponent(txtCusBankID, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtCusBank, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(txtCusName)
                                .addComponent(txtCusPhone, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE))
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(29, Short.MAX_VALUE))
                    .addGroup(panelCusInforLayout.createSequentialGroup()
                        .addComponent(txtCusID, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCusEdit))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCusInforLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5))
            .addGroup(panelCusInforLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCusSave, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCusCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCusInforLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel15)
                .addGap(220, 220, 220))
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
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCusInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtCusPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCusInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtCusBankID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(txtCusBank, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCusInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(panelCusInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(btnCusCancel)
                    .addComponent(btnCusSave))
                .addGap(7, 7, 7)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE))
        );

        editSearchCustomer.setToolTipText("Nhận tên nguồn hàng");

        btnAllCustomer.setText("Tất cả");
        btnAllCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAllCustomerActionPerformed(evt);
            }
        });

        listCustomer.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
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
                        .addComponent(btnAllCustomer)))
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
                            .addComponent(btnAllCustomer))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 512, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        jTabbedPane1.addTab("Khách hàng", jPanel4);

        listSupplier.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listSupplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listSupplierMouseClicked(evt);
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
        txtSupID.setText("00001");
        txtSupID.setBorder(null);
        txtSupID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSupIDActionPerformed(evt);
            }
        });

        txtSupName.setBackground(new java.awt.Color(240, 240, 240));
        txtSupName.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtSupName.setText("Kiên");
        txtSupName.setBorder(null);

        txtSupPhone.setBackground(new java.awt.Color(240, 240, 240));
        txtSupPhone.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtSupPhone.setText("0986414972");
        txtSupPhone.setBorder(null);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel6.setText("Tên tài khoản:");

        txtSupBankID.setBackground(new java.awt.Color(240, 240, 240));
        txtSupBankID.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtSupBankID.setText("786 438 532 432");
        txtSupBankID.setBorder(null);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel7.setText("Ngân hàng:");

        txtSupBank.setBackground(new java.awt.Color(240, 240, 240));
        txtSupBank.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtSupBank.setText("VietinBank Nghệ An");
        txtSupBank.setBorder(null);

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

        btnSupSave.setText("Lưu");

        btnSupEdit.setText("Chỉnh sửa thông tin");

        jLabel26.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel26.setText("Lịch sử nhập hàng");

        txtSupAddr.setBackground(new java.awt.Color(240, 240, 240));
        txtSupAddr.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtSupAddr.setText("Thôn Gì Đó, xã Gì Đó, huyện Gì Đó, tỉnh Gì đó ");
        txtSupAddr.setBorder(null);
        txtSupAddr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSupAddrActionPerformed(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel29.setText("Tài khoản:");

        txtNameBank.setBackground(new java.awt.Color(240, 240, 240));
        txtNameBank.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtNameBank.setText("LAI DAC KIEN");
        txtNameBank.setBorder(null);
        txtNameBank.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameBankActionPerformed(evt);
            }
        });

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
                                    .addGroup(panelSupInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtSupName)
                                        .addComponent(txtSupPhone, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE))
                                    .addComponent(txtNameBank, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSupBankID, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSupAddr, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(26, Short.MAX_VALUE))))
                    .addGroup(panelSupInforLayout.createSequentialGroup()
                        .addGap(189, 189, 189)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSupBank, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
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
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelSupInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtSupPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelSupInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtNameBank, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(jLabel26))
                .addGap(7, 7, 7)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE))
        );

        btnAllSupplier.setText("Tất cả");
        btnAllSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAllSupplierActionPerformed(evt);
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
                        .addComponent(btnAllSupplier)))
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
                            .addComponent(btnAllSupplier))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 512, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        jTabbedPane1.addTab("Nguồn hàng", jPanel3);

        listFriend.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(listFriend);

        editSearchFriend.setToolTipText("Nhận tên nguồn hàng");

        panelFrdInfor.setPreferredSize(new java.awt.Dimension(618, 541));

        jLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel8.setText("THÔNG TIN ĐẠI LÝ ĐỐI TÁC");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel9.setText("ID:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel10.setText("Tên:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel11.setText("Số điện thoại:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel12.setText("Địa chỉ:");

        txtFrdID.setBackground(new java.awt.Color(240, 240, 240));
        txtFrdID.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtFrdID.setText("00001");
        txtFrdID.setBorder(null);
        txtFrdID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFrdIDActionPerformed(evt);
            }
        });

        txtFrdName.setBackground(new java.awt.Color(240, 240, 240));
        txtFrdName.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtFrdName.setText("Kiên");
        txtFrdName.setBorder(null);

        txtFrdPhone.setBackground(new java.awt.Color(240, 240, 240));
        txtFrdPhone.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtFrdPhone.setText("0986414972");
        txtFrdPhone.setBorder(null);

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel13.setText("Tài khoản:");

        txtFrdBankID.setBackground(new java.awt.Color(240, 240, 240));
        txtFrdBankID.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtFrdBankID.setText("786 438 532 432");
        txtFrdBankID.setBorder(null);

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel14.setText("Ngân hàng:");

        txtFrdBank.setBackground(new java.awt.Color(240, 240, 240));
        txtFrdBank.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtFrdBank.setText("VietinBank Nghệ An");
        txtFrdBank.setBorder(null);

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
        jScrollPane4.setViewportView(tableLoanInvoice);

        btnFrdCancel.setText("Huỷ bỏ");

        btnFrdSave.setText("Lưu");

        btnFrdEdit.setText("Chỉnh sửa thông tin");

        jLabel27.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel27.setText("Lịch sử giao dịch");

        jTextField1.setBackground(new java.awt.Color(240, 240, 240));
        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jTextField1.setText("Thôn Gì Đó, xã Gì Đó, huyện Gì Đó, tỉnh Gì đó ");
        jTextField1.setBorder(null);

        javax.swing.GroupLayout panelFrdInforLayout = new javax.swing.GroupLayout(panelFrdInfor);
        panelFrdInfor.setLayout(panelFrdInforLayout);
        panelFrdInforLayout.setHorizontalGroup(
            panelFrdInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFrdInforLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFrdInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelFrdInforLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(panelFrdInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel13)
                            .addComponent(jLabel12))
                        .addGap(23, 23, 23)
                        .addGroup(panelFrdInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelFrdInforLayout.createSequentialGroup()
                                .addComponent(txtFrdID, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnFrdEdit))
                            .addGroup(panelFrdInforLayout.createSequentialGroup()
                                .addGroup(panelFrdInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(panelFrdInforLayout.createSequentialGroup()
                                        .addComponent(txtFrdBankID, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtFrdBank, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtFrdName)
                                    .addComponent(txtFrdPhone, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
                                    .addComponent(jTextField1))
                                .addContainerGap(29, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFrdInforLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(205, 205, 205))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFrdInforLayout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnFrdSave, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnFrdCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))))
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
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFrdInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtFrdPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFrdInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtFrdBankID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(txtFrdBank, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFrdInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(panelFrdInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFrdSave)
                    .addComponent(btnFrdCancel)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btnAllFriend.setText("Tất cả");
        btnAllFriend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAllFriendActionPerformed(evt);
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
                        .addComponent(btnAllFriend)))
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
                            .addComponent(btnAllFriend))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 865, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 563, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Đối tác", jPanel5);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 865, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 563, Short.MAX_VALUE)
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
            .addGap(0, 563, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Thống kê", jPanel6);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 870, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 590, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAllSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAllSupplierActionPerformed
        // TODO add your handling code here:
//        listSupplier.
    }//GEN-LAST:event_btnAllSupplierActionPerformed

    private void txtSupIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSupIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSupIDActionPerformed

    private void txtFrdIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFrdIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFrdIDActionPerformed

    private void btnAllFriendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAllFriendActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAllFriendActionPerformed

    private void txtCusIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCusIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCusIDActionPerformed

    private void btnAllCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAllCustomerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAllCustomerActionPerformed

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

    private void listSupplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listSupplierMouseClicked
        // TODO add your handling code here:
        String supplierName = listSupplier.getSelectedValue();
        Supplier s = EntityManager.supplierDAO.getSupplier(supplierName);
        txtSupID.setText(Integer.toString(s.getId()));
        txtSupName.setText(s.getName());
        txtSupPhone.setText(s.getPhoneNumbers().get(0));
        txtNameBank.setText(s.getPayments().get(0).getName());
        txtSupBankID.setText(s.getPayments().get(0).getBankID());
        txtSupBank.setText(s.getPayments().get(0).getBank());
        txtSupAddr.setText(s.getAddresses().get(0).toString());
    }//GEN-LAST:event_listSupplierMouseClicked

    private void editSearchSupplierKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_editSearchSupplierKeyReleased
        // TODO add your handling code here:
        List<String> searchList = new ArrayList<>();
        String[] keyISO = editSearchSupplier.getText().toLowerCase().trim().split(" ");
        for (String pn
                : supplierNameList) {
            for (String text : keyISO) {
                if (pn.toLowerCase().replaceAll(".,", "").contains(text)) {
                    searchList.add(pn);
                }
            }
        }
        supplierNameModel.clear();
        supplierNameModel.addAll(searchList);
//        lis
    }//GEN-LAST:event_editSearchSupplierKeyReleased

    private void txtNameBankActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameBankActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameBankActionPerformed
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
        productsModel = new ProductTableModel(vctProductsData, vctProductsHeader);
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

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainUI().setVisible(true);
            }
        });
    }

    public void showResult() {
//        for (int i = 0; i < list.size(); i++) {
//            Vector vctRow = new Vector();
//            Word w = list.get(i);
//            vctRow.add(w.getWord());
//            vctRow.add(w.getIpa());
//            vctRow.add(w.getMean());
//            vctRow.add(w.getType());
//            vctRow.add(w.hashtagFancy());
//            vctRow.add(w.getDateModified().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
//            vctData.add(vctRow);
//
////            System.out.println(w.toString());
//        }

        //Product Tab Init
//        vctProductsHeader.add("ID");
//        vctProductsHeader.add("Tên");
//        vctProductsHeader.add("Loại");
//        vctProductsHeader.add("Số lượng");
//        vctProductsHeader.add("Kích cỡ");
//        vctProductsHeader.add("Đơn vị");
//        vctProductsHeader.add("Nguồn nhập");
//        vctProductsHeader.add("Giá nhập");
//        vctProductsHeader.add("Giá bán");
//        vctProductsHeader.add("Ngày nhập");
        productsModel = new ProductTableModel(vctProductsData, vctProductsHeader);
        tableProduct.setModel(productsModel);

        showProductTable();

        salesInvoicesModel = new DefaultTableModel(vctSalesInvoicesData, vctSalesInvoicesHeader);
        tableSalesInvoice.setModel(salesInvoicesModel);
        TableColumnModel columnModelCus = tableSalesInvoice.getColumnModel();
        columnModelCus.getColumn(0).setMaxWidth(50);

        importInvoicesModel = new DefaultTableModel(vctImportInvoicesData, vctImportInvoicesHeader);
        tableImportInvoice.setModel(importInvoicesModel);
        TableColumnModel columnModelSup = tableImportInvoice.getColumnModel();
        columnModelSup.getColumn(0).setMaxWidth(50);

        loanInvoicesModel = new DefaultTableModel(vctLoanInvoicesData, vctLoanInvoicesHeader);
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddProduct;
    private javax.swing.JButton btnAllCustomer;
    private javax.swing.JButton btnAllFriend;
    private javax.swing.JButton btnAllSupplier;
    private javax.swing.JButton btnCusCancel;
    private javax.swing.JButton btnCusEdit;
    private javax.swing.JButton btnCusSave;
    private javax.swing.JButton btnDelProduct;
    private javax.swing.JButton btnEditProduct;
    private javax.swing.JButton btnFrdCancel;
    private javax.swing.JButton btnFrdEdit;
    private javax.swing.JButton btnFrdSave;
    private javax.swing.JButton btnSearchProduct;
    private javax.swing.JButton btnSupCancel;
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
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
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
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JLabel lblMaxPrice;
    private javax.swing.JLabel lblMinPrice;
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
    private javax.swing.JTextField txtCusBank;
    private javax.swing.JTextField txtCusBankID;
    private javax.swing.JTextField txtCusID;
    private javax.swing.JTextField txtCusName;
    private javax.swing.JTextField txtCusPhone;
    private javax.swing.JTextField txtFrdBank;
    private javax.swing.JTextField txtFrdBankID;
    private javax.swing.JTextField txtFrdID;
    private javax.swing.JTextField txtFrdName;
    private javax.swing.JTextField txtFrdPhone;
    private javax.swing.JTextField txtNameBank;
    private javax.swing.JTextField txtSupAddr;
    private javax.swing.JTextField txtSupBank;
    private javax.swing.JTextField txtSupBankID;
    private javax.swing.JTextField txtSupID;
    private javax.swing.JTextField txtSupName;
    private javax.swing.JTextField txtSupPhone;
    private javax.swing.JTextField txtTimeInProduct;
    // End of variables declaration//GEN-END:variables
}
