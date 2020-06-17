/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sownbanana.model;

/**
 *
 * @author son.ph173344
 */
public class Payment {
    private int id;
    private String name;
    private String bankID;
    private String bank;

    public Payment() {
    }

    public Payment(int id, String name, String bankID, String bank) {
        this.id = id;
        this.name = name;
        this.bankID = bankID;
        this.bank = bank;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankID() {
        return bankID;
    }

    public void setBankID(String bankID) {
        this.bankID = bankID;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    @Override
    public String toString() {
        return "Payment{" + "id=" + id + ", name=" + name + ", bankID=" + bankID + ", bank=" + bank + '}';
    }
    
    
}
