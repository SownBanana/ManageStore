/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sownbanana.main;

import com.sownbanana.connection.EntityManager;
import com.sownbanana.connection.ProductDAO;
import com.sownbanana.connection.SupplierDAO;
import com.sownbanana.model.Product;
import com.sownbanana.model.Supplier;
import com.sownbanana.view.MainUI;
import java.util.List;
import javax.swing.JFrame;
import javax.xml.transform.stax.StAXResult;

/**
 *
 * @author son.ph173344
 */
public class ManageStore {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
         /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {

        } catch (InstantiationException ex) {

        } catch (IllegalAccessException ex) {

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {

        }
        //</editor-fold>
        /* Create and display the form */
        EntityManager.mainUI = new MainUI();
        EntityManager.mainUI.show();
        EntityManager.mainUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        EntityManager.mainUI.setLocationRelativeTo(null);
    }
    
}
