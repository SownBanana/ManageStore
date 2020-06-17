/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sownbanana.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author son.ph173344
 */
public abstract class Partner {
    private int id;
    private String name;
    private List<String> phoneNumbers;
    private List<Address> addresses;
    private List<Payment> payments;

    public Partner() {
        this.phoneNumbers = new ArrayList<String>();
        this.addresses = new ArrayList<Address>();
        this.payments = new ArrayList<Payment>();
    }

    public Partner(int id, String name, List<String> phoneNumbers, List<Address> addresses, List<Payment> payments) {
        this.id = id;
        this.name = name;
        this.phoneNumbers = phoneNumbers;
        this.addresses = addresses;
        this.payments = payments;
    }

    public Partner(int id, String name) {
        this.id = id;
        this.name = name;
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

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
    
    public void addPhoneNumber(String phoneNumber){
        this.phoneNumbers.add(phoneNumber);
    }
    public void addAddress(Address address){
        this.addresses.add(address);
    }
    public void addPayment(Payment payment){
        this.payments.add(payment);
    }

    @Override
    public String toString() {
        return "Partner{" + "id=" + id + ", name=" + name + ", phoneNumbers=" + phoneNumbers + ", addresses=" + addresses + ", payments=" + payments + '}';
    }
    
    
}
