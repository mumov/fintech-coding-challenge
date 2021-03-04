package com.example.websocket.model.incoming;


public class Instrument {

    InstrumentData data;
    Type type;

    public InstrumentData getData() {
        return data;
    }

    public void setData(InstrumentData data) {
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
        return "Instrument{" +
                "data=" + data +
                ", type=" + type +
                '}';
    }
}
