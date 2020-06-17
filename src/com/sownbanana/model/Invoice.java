/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sownbanana.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author son.ph173344
 */
public abstract class Invoice {
    private int id;
    private List<Product> soldProducts;
    private Date createDate;
    private int status;
    private String note;

    public Invoice() {
        this.soldProducts = new ArrayList<Product>();
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
    
}
