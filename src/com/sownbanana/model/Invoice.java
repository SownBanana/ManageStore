/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sownbanana.model;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

import java.util.ArrayList;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author son.ph173344
 */
public class Invoice {

    private int id;
    private List<Product> soldProducts;
    private Date createDate;
    private Date secondDate;
    private int status;
    private String note;
    private Double paid;
    private boolean isImport;

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    NumberFormat doubleFormatter = new DecimalFormat("#0.00");

    public Invoice() {
        this.soldProducts = new ArrayList<Product>();
    }

    public Invoice(int id, List<Product> soldProducts, Date createDate, Date secondDate, int status, String note, Double paid, boolean isImport) {
        this.id = id;
        this.soldProducts = soldProducts;
        this.createDate = createDate;
        this.secondDate = secondDate;
        this.status = status;
        this.note = note;
        this.paid = paid;
        this.isImport = isImport;
    }

    public Invoice(int id, List<Product> soldProducts, Date createDate, int status, String note, boolean isImport) {
        this.id = id;
        this.soldProducts = soldProducts;
        this.createDate = createDate;
        this.status = status;
        this.note = note;
        this.isImport = isImport;
    }

    public boolean isIsImport() {
        return isImport;
    }

    public void setIsImport(boolean isImport) {
        this.isImport = isImport;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Product> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<Product> soldProducts) {
        this.soldProducts = soldProducts;
    }

    public void addSoldProducts(Product soldProduct) {
        this.soldProducts.add(soldProduct);
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Double getPaid() {
        return paid;
    }

    public void setPaid(Double paid) {
        this.paid = paid;
    }

    public Date getSecondDate() {
        return secondDate;
    }

    public void setSecondDate(Date secondDate) {
        this.secondDate = secondDate;
    }

    public Double getTotalCost() {
        Double sum = 0.0;
        for (Product p : this.getSoldProducts()) {
            sum += p.getRetailPrice() * p.getQuantity();
        }
        return sum;
    }

    @Override
    public String toString() {
        return "Invoice{" + "id=" + id + ", soldProducts=" + soldProducts + ", createDate=" + createDate + ", status=" + status + ", note=" + note + ", paid=" + paid + '}';
    }

    public void addTitle(Document layoutDocument, String title) {

        layoutDocument.add(new Paragraph("Cua hang vat lieu XYZ").setTextAlignment(TextAlignment.RIGHT).setMultipliedLeading(0.2f).setMultipliedLeading(1));
        layoutDocument.add(new Paragraph("Dia chi: An Cu, Duc Xuong").setTextAlignment(TextAlignment.RIGHT).setMultipliedLeading(.2f).setMultipliedLeading(1));
        layoutDocument.add(new Paragraph("So dien thoai: 0966389332").setTextAlignment(TextAlignment.RIGHT).setMultipliedLeading(.2f));
        layoutDocument.add(new Paragraph(" "));
        layoutDocument.add(new Paragraph(title).setBold().setUnderline().setTextAlignment(TextAlignment.CENTER));
        layoutDocument.add(new Paragraph(" "));
    }

    public void addTable(Document layoutDocument) {
        Table table = new Table(UnitValue.createPointArray(new float[]{30f, 200f, 100f, 120f, 100f, 100f, 200f, 200f}));
        // headers
        table.addCell(new Paragraph("STT").setBold());
        table.addCell(new Paragraph("Ten mat hang").setBold());
        table.addCell(new Paragraph("Loai").setBold());
        table.addCell(new Paragraph("Thuoc tinh").setBold());
        table.addCell(new Paragraph("Don vi").setBold());
        table.addCell(new Paragraph("So luong").setBold());
        table.addCell(new Paragraph("Gia don vi").setBold());
        table.addCell(new Paragraph("Thanh tien").setBold());
        int index = 1;
        // items
        double totalPrice = 0;
        for (Product p : this.soldProducts) {
            table.addCell(new Paragraph(String.valueOf(index++)));
            table.addCell(new Paragraph(p.getName()));
            table.addCell(new Paragraph(p.getCategory()));
            if (p.getType() != null) {
                table.addCell(new Paragraph(p.getType()));
            } else {
                table.addCell(new Paragraph(""));
            }
            table.addCell(new Paragraph(p.getUnit()));
            table.addCell(new Paragraph(doubleFormatter.format(p.getQuantity())));
            table.addCell(new Paragraph(NumberToMoney.currencyFormat(p.getRetailPrice())));
            table.addCell(new Paragraph(NumberToMoney.currencyFormat(p.getQuantity() * p.getRetailPrice())));
            totalPrice += p.getQuantity() * p.getRetailPrice();
        }
        Cell totalNumber = new Cell(1, 7).add(new Paragraph("Tong tien phai tra: ")).setTextAlignment(TextAlignment.RIGHT);
        table.addCell(totalNumber);
        table.addCell(new Paragraph(NumberToMoney.currencyFormat(totalPrice)));
        Cell totalChar = new Cell(2, 8).add(new Paragraph("Tong tien bang chu: " + NumberToMoney.toMoney(NumberToMoney.currencyFormat(totalPrice)))).setTextAlignment(TextAlignment.LEFT);
        table.addCell(totalChar);
        layoutDocument.add(table);
        layoutDocument.add(new Paragraph("Ngay dat hang: " + this.getCreateDate().toLocalDate().format(dateFormatter)).setMultipliedLeading(1));
        layoutDocument.add(new Paragraph("Ten khach hang: " + this.getSecondDate().toLocalDate().format(dateFormatter)));
    }

}
