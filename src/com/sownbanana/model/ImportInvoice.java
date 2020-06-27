/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sownbanana.model;

import com.sownbanana.controller.UIController;
import java.util.Date;
import java.util.List;

/**
 *
 * @author son.ph173344
 */
public class ImportInvoice extends Invoice {


    public ImportInvoice() {
    }

    public ImportInvoice(Date receiveDate, int id, List<Product> soldProducts, Date createDate, int status, String note) {
        super(id, soldProducts, createDate, status, note);
        super.setSecondDate(receiveDate);
    }

    public Date getReceiveDate() {
        return super.getSecondDate();
    }

    public void setReceiveDate(Date receiveDate) {
      super.setSecondDate(receiveDate);
    }

    
    @Override
    public String toString() {
        return UIController.center(this.getId(), 15) + "|"
                + UIController.center(this.getCreateDate(), 26) + "|" + UIController.center(this.getReceiveDate(), 26) + "|"
                + UIController.center(this.getTotalCost(), 33) + "|" + UIController.center(this.getPaid(), 33) + "|"
                + UIController.center(super.getNote(), 23);
    }

}
