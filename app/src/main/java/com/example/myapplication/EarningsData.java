package com.example.myapplication;

public class EarningsData {
    private double actual;
    private double estimate;
    private String period;
    private double surprise;
    private double surprisePercent;
    private String symbol;

    public EarningsData() {
    }

    @Override
    public String toString() {
        return "EarningsData{" +
                "actual=" + actual +
                ", estimate=" + estimate +
                ", period='" + period + '\'' +
                ", surprise=" + surprise +
                ", surprisePercent=" + surprisePercent +
                ", symbol='" + symbol + '\'' +
                '}';
    }

    public double getActual() {
        return actual;
    }

    public void setActual(double actual) {
        this.actual = actual;
    }

    public double getEstimate() {
        return estimate;
    }

    public void setEstimate(double estimate) {
        this.estimate = estimate;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public double getSurprise() {
        return surprise;
    }

    public void setSurprise(double surprise) {
        this.surprise = surprise;
    }

    public double getSurprisePercent() {
        return surprisePercent;
    }

    public void setSurprisePercent(double surprisePercent) {
        this.surprisePercent = surprisePercent;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
