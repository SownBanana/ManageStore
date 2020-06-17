/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sownbanana.model;

import java.util.Date;

/**
 *
 * @author son.ph173344
 */
public class LoanInvoice extends Invoice{
    private int paymenMethod; //0-cash 1-bank
    private Date returDate;

    public LoanInvoice() {
    }

    public LoanInvoice(Date returDate) {
        this.returDate = returDate;
    }

    public int getPaymenMethod() {
        return paymenMethod;
    }

    public void setPaymenMethod(int paymenMethod) {
        this.paymenMethod = paymenMethod;
    }

    public Date getReturDate() {
        return returDate;
    }

    public void setReturDate(Date returDate) {
        this.returDate = returDate;
    }
    
}
