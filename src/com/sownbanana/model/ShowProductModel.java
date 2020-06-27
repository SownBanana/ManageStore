/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sownbanana.model;

import com.sownbanana.controller.UIController;

/**
 *
 * @author son.ph173344
 */
public class ShowProductModel {
    public int id;
    String productName;
    Double quantity;
    Double cost;

    public ShowProductModel(int id, String productName, Double quantity, Double cost) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return  UIController.center(this.productName, 30) + "|" + UIController.center(this.quantity, 30) + "|"
                + UIController.center(this.cost, 30);
    }
    
    
}
