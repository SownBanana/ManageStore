/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sownbanana.controller;

import com.sownbanana.connection.EntityManager;
import com.sownbanana.model.Product;
import com.sownbanana.view.ProductViewUI;

/**
 *
 * @author son.ph173344
 */
public class ProductController {
    public void editProduct(Product product){
        ProductViewUI editproduct = new ProductViewUI(EntityManager.mainUI, true, product);
        editproduct.isEdit = true;
    }
}
