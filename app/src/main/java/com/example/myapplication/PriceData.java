package com.example.myapplication;

public class PriceData {
    private Double c;
    private Double d;
    private Double dp;
    private Double h;
    private Double l;
    private Double o;
    private Double pc;
    private long t;
    private String ticker;

    @Override
    public String toString() {
        return "PriceData{" +
                "c=" + c +
                ", d=" + d +
                ", dp=" + dp +
                ", h=" + h +
                ", l=" + l +
                ", o=" + o +
                ", pc=" + pc +
                ", t=" + t +
                ", ticker='" + ticker + '\'' +
                '}';
    }

    public PriceData() {
    }

    public Double getC() {
        return c;
    }

    public void setC(Double c) {
        this.c = c;
    }

    public Double getD() {
        return d;
    }

    public void setD(Double d) {
        this.d = d;
    }

    public Double getDp() {
        return dp;
    }

    public void setDp(Double dp) {
        this.dp = dp;
    }

    public Double getH() {
        return h;
    }

    public void setH(Double h) {
        this.h = h;
    }

    public Double getL() {
        return l;
    }

    public void setL(Double l) {
        this.l = l;
    }

    public Double getO() {
        return o;
    }

    public void setO(Double o) {
        this.o = o;
    }

    public Double getPc() {
        return pc;
    }

    public void setPc(Double pc) {
        this.pc = pc;
    }

    public long getT() {
        return t;
    }

    public void setT(long t) {
        this.t = t;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
}
