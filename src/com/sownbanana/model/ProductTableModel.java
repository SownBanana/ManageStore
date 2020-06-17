/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sownbanana.model;

import java.time.LocalDate;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author son.ph173344
 */
public class ProductTableModel extends DefaultTableModel {

    public ProductTableModel(Vector<? extends Vector> data, Vector<?> columnNames) {
        super(data, columnNames);
    }


    @Override
    public Class getColumnClass(int column) {
        if (column == 0 || column == 4 || column == 7 || column == 8) {
            return Double.class;
        }
        else if(column == 9){
            return LocalDate.class;
        }
        else {
            return String.class;
        }
    }

}
