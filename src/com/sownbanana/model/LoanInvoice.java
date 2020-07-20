/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sownbanana.model;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import com.sownbanana.controller.UIController;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        if(isIsImport()){
            return "  I " + UIController.center(this.getId(), 10) + "|" + 
                UIController.center(this.getCreateDate(), 26) + "|" +UIController.center(this.getReturnDate(), 26) + "|" +
                UIController.center(this.getTotalCost(), 33) + "|" +UIController.center(this.getPaid(), 33) + "|" +
                UIController.center(super.getNote(), 23);
        }
        else{
            return "  O" + UIController.center(this.getId(), 9) + "|" + 
                UIController.center(this.getCreateDate(), 26) + "|" +UIController.center(this.getReturnDate(), 26) + "|" +
                UIController.center(this.getTotalCost(), 33) + "|" +UIController.center(this.getPaid(), 33) + "|" +
                UIController.center(super.getNote(), 23);
        }
    }
    
    public void addFriendReference(Document layoutDocument, Partner partner) {
        layoutDocument.add(new Paragraph("Ten doi tac: " + partner.getName()).setTextAlignment(TextAlignment.LEFT).setMultipliedLeading(0.2f));
        layoutDocument.add(new Paragraph("Dia chi: " + partner.getAddresses().get(0).toString()).setMultipliedLeading(.2f).setMultipliedLeading(1));
        layoutDocument.add(new Paragraph("So dien thoai: " + partner.getPhoneNumbers().get(0)).setMultipliedLeading(.2f));
        layoutDocument.add(new Paragraph(" "));
    }
    
    public void addSign(Document layoutDocument){
        layoutDocument.add(new Paragraph("Chu ky doi tac                                                                                     Chu ky chu cua hang").setMultipliedLeading(2));
    }

    public void toPdf(Partner partner) {
        try {
            PdfDocument pdfDocument = new PdfDocument(new PdfWriter("P:/Code4Life/Project2/ManageStore/invoice/loans/"+ super.getId() + partner.getName() +"LoanInvoice.pdf"));
            Document layoutDocument = new Document(pdfDocument);
            
            super.addTitle(layoutDocument, "HOA DON VAY HANG");
            this.addFriendReference(layoutDocument, partner);
            super.addTable(layoutDocument);
            this.addSign(layoutDocument);
            layoutDocument.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SalesInvoice.class.getName()).log(Level.SEVERE, null, ex);
            File file = new File("P:/Code4Life/Project2/ManageStore/invoice/loans/");
            file.mkdir();
            toPdf(partner);
        }
    }
    
}
