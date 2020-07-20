/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sownbanana.view;

import com.mysql.jdbc.jmx.LoadBalanceConnectionGroupManager;
import com.sownbanana.connection.EntityManager;
import com.sownbanana.model.ImportInvoice;
import com.sownbanana.model.Invoice;
import com.sownbanana.model.LoanInvoice;
import com.sownbanana.model.NumberToMoney;
import com.sownbanana.model.Partner;
import com.sownbanana.model.Product;
import com.sownbanana.model.ProductTableModel;
import com.sownbanana.model.SalesInvoice;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.table.TableColumnModel;
import java.sql.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

/**
 *
 * @author son.ph173344
 */
public class AddInvoiceUI extends javax.swing.JDialog {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy"); 
    NumberFormat doubleFormatter = new DecimalFormat("#0.00");

    Invoice invoice;
    Partner partner;
    int type;
    boolean isEdit = false;

    ProductTableModel productsModel;
    Vector vctProductsHeader = new Vector();
    Vector vctProductsData = new Vector();
    
    JPopupMenu popup;

    public AddInvoiceUI(java.awt.Frame parent, boolean modal, Partner p, int type) {
        super(parent, modal);
        initComponents();
        partner = p;
        this.type = type;
        checkIsBorrow.setVisible(false);
        if (type == 3) {
            checkIsBorrow.setVisible(true);
        }
        txtName.setText(p.getName());
        txtOrderDate.setText(LocalDate.now().format(formatter));
        txtAddr.setText(p.getAddresses().get(0).toString());

        vctProductsHeader.add("ID");
        vctProductsHeader.add("Tên");
        vctProductsHeader.add("Loại");
        vctProductsHeader.add("Thuộc tính");
        vctProductsHeader.add("Số lượng");
        vctProductsHeader.add("Đơn vị");
        vctProductsHeader.add("Giá bán");

        productsModel = new ProductTableModel(null, vctProductsHeader) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // or a condition at your choice with row and column
            }
        };

        tbleOrderProduct.setModel(productsModel);
        TableColumnModel columnModel = tbleOrderProduct.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(50);

        tbleOrderProduct.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                System.out.println(Arrays.toString(tbleOrderProduct.getSelectedRows()));
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    int select = tbleOrderProduct.getSelectedRow();
                    Product p = invoice.getSoldProducts().get(select);
                    AddProductToInvoiceUI addProduct = new AddProductToInvoiceUI(EntityManager.mainUI, rootPaneCheckingEnabled, p);
                    addProduct.setLocationRelativeTo(null);
                    Product product = addProduct.showDialog();
                    if (product != null) {
                        invoice.getSoldProducts().remove(select);
                        invoice.getSoldProducts().add(product);
                        productsModel.removeRow(select);
                        Vector dataVector = new Vector();
                        dataVector.add(product.getId());
                        dataVector.add(product.getName());
                        dataVector.add(product.getCategory());
                        dataVector.add(product.getType());
                        dataVector.add(product.getQuantity());
                        dataVector.add(product.getUnit());
                        dataVector.add(NumberToMoney.currencyFormat(product.getRetailPrice()));
                        productsModel.addRow(dataVector);
                        Double oldMoney = 0.0;
                        for(Product pro : invoice.getSoldProducts()){
                            oldMoney += pro.getQuantity()*pro.getRetailPrice();
                        }
                        txtTotalPrice.setText(NumberToMoney.currencyFormat(oldMoney + p.getRetailPrice() * p.getQuantity()));
                    }
                }
            }
        });
        invoice = new Invoice();
    }
    
    public AddInvoiceUI(java.awt.Frame parent, boolean modal, Partner p, int type, Invoice iinvoice) {
        super(parent, modal);
        initComponents();
        partner = p;
        this.isEdit = true;
        this.type = type;
        checkIsBorrow.setVisible(false);
        if (type == 3) {
            checkIsBorrow.setVisible(true);
        }
        txtName.setText(p.getName());
        txtOrderDate.setText(LocalDate.now().format(formatter));
        txtAddr.setText(p.getAddresses().get(0).toString());

        vctProductsHeader.add("ID");
        vctProductsHeader.add("Tên");
        vctProductsHeader.add("Loại");
        vctProductsHeader.add("Thuộc tính");
        vctProductsHeader.add("Số lượng");
        vctProductsHeader.add("Đơn vị");
        vctProductsHeader.add("Giá bán");

        productsModel = new ProductTableModel(null, vctProductsHeader) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // or a condition at your choice with row and column
            }
        };

        tbleOrderProduct.setModel(productsModel);
        TableColumnModel columnModel = tbleOrderProduct.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(50);

        tbleOrderProduct.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                System.out.println(Arrays.toString(tbleOrderProduct.getSelectedRows()));
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    int select = tbleOrderProduct.getSelectedRow();
                    Product p = invoice.getSoldProducts().get(select);
                    AddProductToInvoiceUI addProduct = new AddProductToInvoiceUI(EntityManager.mainUI, rootPaneCheckingEnabled, p);
                    addProduct.setLocationRelativeTo(null);
                    Product product = addProduct.showDialog();
                    if (product != null) {
                        invoice.getSoldProducts().remove(select);
                        invoice.getSoldProducts().add(product);
                        productsModel.removeRow(select);
                        Vector dataVector = new Vector();
                        dataVector.add(product.getId());
                        dataVector.add(product.getName());
                        dataVector.add(product.getCategory());
                        dataVector.add(product.getType());
                        dataVector.add(product.getQuantity());
                        dataVector.add(product.getUnit());
                        dataVector.add(product.getRetailPrice());
                        productsModel.addRow(dataVector);
                        Double oldMoney = Double.parseDouble(txtTotalPrice.getText());
                        txtTotalPrice.setText(doubleFormatter.format(oldMoney + p.getRetailPrice() * p.getQuantity()));
                    }
                }
            }
        });
        invoice = iinvoice;
        txtFinishDate.setText(df.format(invoice.getSecondDate()));
        txtPaid.setText(doubleFormatter.format(invoice.getPaid()));
        txtNote.setText(invoice.getNote());
        for(Product pro : invoice.getSoldProducts()){
            Vector dataVector = new Vector();
            dataVector.add(pro.getId());
            dataVector.add(pro.getName());
            dataVector.add(pro.getCategory());
            dataVector.add(pro.getType());
            dataVector.add(pro.getQuantity());
            dataVector.add(pro.getUnit());
            dataVector.add(pro.getRetailPrice());
            productsModel.addRow(dataVector);
            Double oldMoney = Double.parseDouble(txtTotalPrice.getText());
            txtTotalPrice.setText(doubleFormatter.format(oldMoney + pro.getRetailPrice() * pro.getQuantity()));
        }
        checkIsBorrow.setSelected(!invoice.isIsImport());
        txtAddr.setEnabled(false);
        checkIsBorrow.setEnabled(false);
        btnAddProduct.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel12 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtOrderDate = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        txtTotalPrice = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbleOrderProduct = new javax.swing.JTable();
        btnCancel = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        txtAddr = new javax.swing.JTextField();
        txtFinishDate = new javax.swing.JTextField();
        txtPaid = new javax.swing.JTextField();
        btnAddProduct = new javax.swing.JButton();
        jLabel36 = new javax.swing.JLabel();
        txtNote = new javax.swing.JTextField();
        checkIsBorrow = new javax.swing.JCheckBox();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(8, 0), new java.awt.Dimension(8, 0), new java.awt.Dimension(8, 32767));
        btnPrintInvoice = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel12.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel12.setText("HOÁ ĐƠN BÁN HÀNG");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel17.setText("Tên khách hàng:");

        txtName.setEditable(false);
        txtName.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtName.setText("Kiên");
        txtName.setBorder(null);
        txtName.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel18.setText("Ngày đặt hàng:");

        txtOrderDate.setBackground(new java.awt.Color(240, 240, 240));
        txtOrderDate.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtOrderDate.setText("20/06/2020");
        txtOrderDate.setBorder(null);
        txtOrderDate.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtOrderDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOrderDateActionPerformed(evt);
            }
        });

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel32.setText("Ngày giao hàng:");

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel33.setText("Tổng tiền hàng:");

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel34.setText("Địa chỉ giao hàng:");

        txtTotalPrice.setEditable(false);
        txtTotalPrice.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtTotalPrice.setText("0");
        txtTotalPrice.setBorder(null);
        txtTotalPrice.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtTotalPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalPriceActionPerformed(evt);
            }
        });

        jLabel35.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel35.setText("Đã thanh toán:");

        tbleOrderProduct.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tbleOrderProduct);

        btnCancel.setText("Huỷ");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnSave.setText("Lưu");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        txtAddr.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        txtFinishDate.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        txtPaid.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        btnAddProduct.setText("Thêm hàng");
        btnAddProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddProductActionPerformed(evt);
            }
        });

        jLabel36.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel36.setText("Ghi chú:");

        txtNote.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        checkIsBorrow.setText("Đại lý bạn vay hàng");

        btnPrintInvoice.setText("In hoá đơn");
        btnPrintInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintInvoiceActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(224, 224, 224)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel33, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel34, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel36, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                                .addComponent(checkIsBorrow))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtOrderDate, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel32)
                                .addGap(18, 18, 18)
                                .addComponent(txtFinishDate))
                            .addComponent(txtNote)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel35)
                                .addGap(18, 18, 18)
                                .addComponent(txtPaid))
                            .addComponent(txtAddr)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnPrintInvoice)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAddProduct)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancel)))
                .addGap(2, 2, 2)
                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(checkIsBorrow))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtOrderDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32)
                    .addComponent(txtFinishDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(txtAddr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(txtTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35)
                    .addComponent(txtPaid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(txtNote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(177, 177, 177)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel)
                    .addComponent(btnSave)
                    .addComponent(btnAddProduct)
                    .addComponent(btnPrintInvoice))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtOrderDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOrderDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOrderDateActionPerformed

    private void txtTotalPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalPriceActionPerformed

    private void btnAddProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddProductActionPerformed
        // TODO add your handling code here:
        AddProductToInvoiceUI addProduct = new AddProductToInvoiceUI(EntityManager.mainUI, rootPaneCheckingEnabled);
        addProduct.setLocationRelativeTo(null);
        Product p = addProduct.showDialog();
        if (p != null) {
            invoice.addSoldProducts(p);
            Vector dataVector = new Vector();
            dataVector.add(p.getId());
            dataVector.add(p.getName());
            dataVector.add(p.getCategory());
            dataVector.add(p.getType());
            dataVector.add(p.getQuantity());
            dataVector.add(p.getUnit());
            dataVector.add(p.getRetailPrice());
            productsModel.addRow(dataVector);
            Double oldMoney = Double.parseDouble(txtTotalPrice.getText());
            txtTotalPrice.setText(doubleFormatter.format(oldMoney + p.getRetailPrice() * p.getQuantity()));
        }
    }//GEN-LAST:event_btnAddProductActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        try {
            LocalDate createDate = LocalDate.parse(txtOrderDate.getText(), formatter);
            LocalDate finishDate = LocalDate.parse(txtFinishDate.getText(), formatter);
            for (Product p : invoice.getSoldProducts()) {
                p.setImportDate(Date.valueOf(finishDate));
            }
            invoice.setCreateDate(Date.valueOf(createDate));
            invoice.setSecondDate(Date.valueOf(finishDate));
            invoice.setNote(txtNote.getText().trim());
            invoice.setPaid(Double.parseDouble(txtPaid.getText().trim()));
            
            if (type == 1) {
                if(!isEdit) EntityManager.invoiceDAO.insertInvoice(type, partner.getId(), invoice.getCreateDate(), invoice.getSecondDate(), invoice.getPaid(), invoice.getNote(), invoice.getSoldProducts(), true);
                else EntityManager.invoiceDAO.updateInvoice(invoice.getId(), invoice.getPaid(), invoice.getSecondDate(), invoice.getNote());
                EntityManager.mainUI.showImportInvoiceTree(partner.getId());
            } else if (type == 2) {
                if(!isEdit) EntityManager.invoiceDAO.insertInvoice(type, partner.getId(), invoice.getCreateDate(), invoice.getSecondDate(), invoice.getPaid(), invoice.getNote(), invoice.getSoldProducts(), false);
                else EntityManager.invoiceDAO.updateInvoice(invoice.getId(), invoice.getPaid(), invoice.getSecondDate(), invoice.getNote());
                EntityManager.mainUI.showSalesInvoiceTree(partner.getId());
            } else {
                if (checkIsBorrow.isSelected()) {
                    if(!isEdit) EntityManager.invoiceDAO.insertInvoice(type, partner.getId(), invoice.getCreateDate(), invoice.getSecondDate(), invoice.getPaid(), invoice.getNote(), invoice.getSoldProducts(), false);
                    else EntityManager.invoiceDAO.updateInvoice(invoice.getId(), invoice.getPaid(), invoice.getSecondDate(), invoice.getNote());
                } else {
                    if(!isEdit) EntityManager.invoiceDAO.insertInvoice(type, partner.getId(), invoice.getCreateDate(), invoice.getSecondDate(), invoice.getPaid(), invoice.getNote(), invoice.getSoldProducts(), true);
                    else EntityManager.invoiceDAO.updateInvoice(invoice.getId(), invoice.getPaid(), invoice.getSecondDate(), invoice.getNote());
                }
               EntityManager.mainUI.showLoanInvoiceTree(partner.getId());
            }
            EntityManager.mainUI.showIncomeHome(); 
            EntityManager.mainUI.showProductTable();
            this.dispose();
        } catch (Exception e) {

        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        invoice = null;
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnPrintInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintInvoiceActionPerformed
        // TODO add your handling code here:\
        try {
            if(type == 2){
            SalesInvoice salesInvoice = (SalesInvoice) invoice;
            salesInvoice.toPdf(partner);
        }else if(type == 1){
            ImportInvoice importInvoice = (ImportInvoice) invoice;
            importInvoice.toPdf(partner);
        }
        else{
            LoanInvoice loanInvoice = (LoanInvoice) invoice;
            loanInvoice.toPdf(partner);
        }
            JOptionPane.showMessageDialog(rootPane, "In hoá đơn thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra!");
        }
        
    }//GEN-LAST:event_btnPrintInvoiceActionPerformed

    Invoice showDialog() {
        this.setVisible(true);
        return invoice;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddProduct;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnPrintInvoice;
    private javax.swing.JButton btnSave;
    private javax.swing.JCheckBox checkIsBorrow;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbleOrderProduct;
    private javax.swing.JTextField txtAddr;
    private javax.swing.JTextField txtFinishDate;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtNote;
    private javax.swing.JTextField txtOrderDate;
    private javax.swing.JTextField txtPaid;
    private javax.swing.JTextField txtTotalPrice;
    // End of variables declaration//GEN-END:variables
}
