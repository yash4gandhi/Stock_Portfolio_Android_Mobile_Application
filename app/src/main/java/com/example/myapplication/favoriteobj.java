package com.example.myapplication;

import java.io.Serializable;

public class favoriteobj implements Serializable {
    private String ticker;
    private Double price;
    private String companyname;
    private Double changeprice;
    private Double percentageChange;

    public favoriteobj(String ticker, Double price, String companyname, Double changeprice, Double percentageChange) {
        this.ticker = ticker;
        this.price = price;
        this.companyname = companyname;
        this.changeprice = changeprice;
        this.percentageChange = percentageChange;
    }




    @Override
    public String toString() {
        return "favoriteobj{" +
                "ticker='" + ticker + '\'' +
                ", price=" + price +
                ", companyname=" + companyname +
                ", changeprice=" + changeprice +
                ", percentageChange=" + percentageChange +
                '}';
    }

    public favoriteobj() {
    }


    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
    public String getTicker() {
        return ticker;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getcompanyname() {
        return companyname;
    }

    public void setShares(String companyname) {
        this.companyname = companyname;
    }

    public Double getChangeprice() {
        return changeprice;
    }

    public void setChangeprice(Double changeprice) {
        this.changeprice = changeprice;
    }

    public Double getPercentageChange() {
        return percentageChange;
    }

    public void setPercentageChange(Double percentageChange) {
        this.percentageChange = percentageChange;
    }



}
