package com.example.websocket.model.incoming;

public class Quote {

    QuoteData data;
    Type type;

    public QuoteData getData() {
        return data;
    }

    public void setData(QuoteData data) {
        this.data = data;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "data=" + data +
                ", type=" + type +
                '}';
    }
}
