package com.example.websocket.model.incoming;

public class QuoteData {

    double price;
    String isin;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    @Override
    public String toString() {
        return "QuoteData{" +
                "price='" + price + '\'' +
                ", isin='" + isin + '\'' +
                '}';
    }
}
