package com.example.myapplication;

public class AutocompleteObj {

    private String description;
    private String displaySymbol;
    private String symbol;
    private String type;

    public AutocompleteObj(String description, String displaySymbol, String symbol, String type) {
        this.description = description;
        this.displaySymbol = displaySymbol;
        this.symbol = symbol;
        this.type = type;
    }

    public AutocompleteObj() {
    }

    @Override
    public String toString() {
        return "AutocompleteObj{" +
                "description='" + description + '\'' +
                ", displaySymbol='" + displaySymbol + '\'' +
                ", symbol='" + symbol + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplaySymbol() {
        return displaySymbol;
    }

    public void setDisplaySymbol(String displaySymbol) {
        this.displaySymbol = displaySymbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
