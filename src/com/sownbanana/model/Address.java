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
public class Address {

    private int id;
    private String detail;
    private String village;
    private String commune;
    private String town;
    private String provine;

    public Address() {
        this.detail = "";
        this.village = "";
        this.commune = "";
        this.town = "";
        this.provine = "";
    }

    public Address(int id, String detail, String village, String commune, String town, String provine) {
        this.id = id;
        this.detail = detail;
        this.village = village;
        this.commune = commune;
        this.town = town;
        this.provine = provine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getProvine() {
        return provine;
    }

    public void setProvine(String provine) {
        this.provine = provine;
    }

//    @Override
//    public String toString() {
//        String rs = "";
//        if (detail == null) {
//        }
//        else if (detail.trim().equals("")) {
//        } else {
//            rs += detail + ", ";
//        }
//        if (village != null && !village.trim().equals("")) {
//            rs += "Thôn " + village + ", ";
//        }
//        if (commune != null && !commune.trim().equals("")) {
//            rs += "Xã " + commune + ", ";
//        }
//        if (town != null && !town.trim().equals("")) {
//            rs += "Huyện " + town + ", ";
//        }
//        if (provine != null && !provine.trim().equals("")) {
//            rs += "Tỉnh " + provine;
//        }
//        return rs;
//    }

    @Override
    public String toString() {
        return provine;
    }
    
    

}
