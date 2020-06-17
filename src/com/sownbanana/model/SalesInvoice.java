/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sownbanana.model;

import java.util.Date;
import java.util.List;

/**
 *
 * @author son.ph173344
 */
public class SalesInvoice extends Invoice{
    private Date shipDate;

    public SalesInvoice() {
    }

    public SalesInvoice(Date shipDate, int id, List<Product> soldProducts, Date createDate, int status, String note) {
        super(id, soldProducts, createDate, status, note);
        this.shipDate = shipDate;
    }
    

    public Date getShipDate() {
        return shipDate;
    }

    public void setShipDate(Date shipDate) {
        this.shipDate = shipDate;
    }
    
}
