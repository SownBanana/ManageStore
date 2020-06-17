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
public class ImportInvoice extends Invoice{
    private Date receiveDate;

    public ImportInvoice() {
    }

    public ImportInvoice(Date receiveDate, int id, List<Product> soldProducts, Date createDate, int status, String note) {
        super(id, soldProducts, createDate, status, note);
        this.receiveDate = receiveDate;
    }
    

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }
    
    
}
