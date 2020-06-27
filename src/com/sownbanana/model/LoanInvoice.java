/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sownbanana.model;

import com.sownbanana.controller.UIController;
import java.util.Date;

/**
 *
 * @author son.ph173344
 */
public class LoanInvoice extends Invoice{
    private int paymenMethod; //0-cash 1-bank

    public LoanInvoice() {
    }


    public int getPaymenMethod() {
        return paymenMethod;
    }

    public void setPaymenMethod(int paymenMethod) {
        this.paymenMethod = paymenMethod;
    }

    public Date getReturnDate() {
        return super.getSecondDate();
    }

    public void setReturnDate(Date returnDate) {
        super.setSecondDate(returnDate);
    }

    @Override
    public String toString() {
        return UIController.center(this.getId(), 15) + "|" + 
                UIController.center(this.getCreateDate(), 26) + "|" +UIController.center(this.getReturnDate(), 26) + "|" +
                UIController.center(this.getTotalCost(), 33) + "|" +UIController.center(this.getPaid(), 33) + "|" +
                UIController.center(super.getNote(), 23);
    }
    
    
    
}
