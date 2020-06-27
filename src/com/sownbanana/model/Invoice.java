/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sownbanana.model;

import com.sownbanana.controller.UIController;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author son.ph173344
 */
public class Invoice{

    private int id;
    private List<Product> soldProducts;
    private Date createDate;
    private Date secondDate;
    private int status;
    private String note;
    private Double paid;

    public Invoice() {
        this.soldProducts = new ArrayList<Product>();
    }

    public Invoice(int id, List<Product> soldProducts, Date createDate, Date secondDate, int status, String note, Double paid) {
        this.id = id;
        this.soldProducts = soldProducts;
        this.createDate = createDate;
        this.secondDate = secondDate;
        this.status = status;
        this.note = note;
        this.paid = paid;
    }

    
    public Invoice(int id, List<Product> soldProducts, Date createDate, int status, String note) {
        this.id = id;
        this.soldProducts = soldProducts;
        this.createDate = createDate;
        this.status = status;
        this.note = note;
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

}
