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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author son.ph173344
 */
public class ImportInvoice extends Invoice {


    public ImportInvoice() {
        super.setIsImport(true);
    }

    public ImportInvoice(Date receiveDate, int id, List<Product> soldProducts, Date createDate, int status, String note) {
        super(id, soldProducts, createDate, status, note, true);
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
    
    public void addSupplierReference(Document layoutDocument, Partner partner) {
        layoutDocument.add(new Paragraph("Ten nguon hang: " + partner.getName()).setTextAlignment(TextAlignment.LEFT).setMultipliedLeading(0.2f));
        layoutDocument.add(new Paragraph("Dia chi: " + partner.getAddresses().get(0).toString()).setMultipliedLeading(.2f).setMultipliedLeading(1));
        layoutDocument.add(new Paragraph("So dien thoai: " + partner.getPhoneNumbers().get(0)).setMultipliedLeading(.2f));
        layoutDocument.add(new Paragraph(" "));
    }
    
    public void addSign(Document layoutDocument){
        layoutDocument.add(new Paragraph("Chu ky khach hang                                                                                     Chu ky chu cua hang").setMultipliedLeading(2));
    }

    public void toPdf(Partner partner) {
        try {
            PdfDocument pdfDocument = new PdfDocument(new PdfWriter("P:/Code4Life/Project2/ManageStore/invoice/imports/"+ super.getId() + partner.getName() +"ImportInvoice.pdf"));
            Document layoutDocument = new Document(pdfDocument);
            
            super.addTitle(layoutDocument, "PHIEU NHAP KHO");
            this.addSupplierReference(layoutDocument, partner);
            super.addTable(layoutDocument);
            this.addSign(layoutDocument);
            layoutDocument.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SalesInvoice.class.getName()).log(Level.SEVERE, null, ex);
            File file = new File("P:/Code4Life/Project2/ManageStore/invoice/imports/");
            file.mkdir();
            toPdf(partner);
        }
    }
}
