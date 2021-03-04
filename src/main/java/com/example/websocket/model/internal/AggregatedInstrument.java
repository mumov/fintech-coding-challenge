package com.example.websocket.model.internal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = "chart")
public class AggregatedInstrument {

    String description;
    String isin;
    Double price;

    public AggregatedInstrument() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "AggregatedInstrument{" +
                "description='" + description + '\'' +
                ", isin='" + isin + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
