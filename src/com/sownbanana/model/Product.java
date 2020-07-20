/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sownbanana.model;

import com.sownbanana.connection.EntityManager;
import java.sql.Date;

/**
 *
 * @author son.ph173344
 */
public class Product {
    private int id;
    private String name;
    private String category;
    private String type;
    private double quantity;
    private String unit;
    private String size;
//    private Supplier supplier;
    private int supplierId;
    private double importPrice;
    private double retailPrice;
    private Date importDate;

    public Product() {
    }

    public Product(int id, String name, String category, String type, double quantity, String unit, String size, int supplier, double importPrice, double retailPrice, Date importDate) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.type = type;
        this.quantity = quantity;
        this.unit = unit;
        this.size = size;
        this.supplierId = supplier;
        this.importPrice = importPrice;
        this.retailPrice = retailPrice;
        this.importDate = importDate;
    }

    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public String getIdName() {
        return id + ". " + name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplier) {
        this.supplierId = supplier;
    }

    public double getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(double importPrice) {
        this.importPrice = importPrice;
    }

    public double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }
    public void setCategoryString(int id){
        this.category = EntityManager.categoryDAO.getCategoryNameById(id);
    }
//    public void setSupplierEntity(int id){
//        for (Supplier supplier : EntityManager.supliers) {
//            if(supplier.getId() == id) {
//                this.supplier = supplier;
//                return;
//            }
//        }
//        this.supplier = null;
//    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name=" + name + ", category=" + category + ", type=" + type + ", quantity=" + quantity + ", unit=" + unit + ", size=" + size + ", supplier=" + supplierId + ", importPrice=" + importPrice + ", retailPrice=" + retailPrice + ", importDate=" + importDate + '}';
    }

    
    
}
