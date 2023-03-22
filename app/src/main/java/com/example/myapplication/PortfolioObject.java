package com.example.myapplication;

import java.io.Serializable;

public class PortfolioObject implements Serializable {
    private String ticker;
    private Double price;
    private int shares;

    public PortfolioObject(String ticker, Double price, int shares, Double changeprice, Double percentageChange) {
        this.ticker = ticker;
        this.price = price;
        this.shares = shares;
        this.changeprice = changeprice;
        this.percentageChange = percentageChange;
    }

    private Double changeprice;
    private Double percentageChange;


    @Override
    public String toString() {
        return "PortfolioObject{" +
                "ticker='" + ticker + '\'' +
                ", price=" + price +
                ", shares=" + shares +
                ", changeprice=" + changeprice +
                ", percentageChange=" + percentageChange +
                '}';
    }

    public PortfolioObject() {
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

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
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
