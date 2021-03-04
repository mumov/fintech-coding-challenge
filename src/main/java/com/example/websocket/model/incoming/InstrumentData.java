package com.example.websocket.model.incoming;
public class InstrumentData {

    String description;
    String isin;

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

    @Override
    public String toString() {
        return "InstrumentData{" +
                "description='" + description + '\'' +
                ", isin='" + isin + '\'' +
                '}';
    }
}
