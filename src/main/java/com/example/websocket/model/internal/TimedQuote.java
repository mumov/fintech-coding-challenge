package com.example.websocket.model.internal;

public class TimedQuote implements Comparable {

    private final double price;
    private final long timestamp;
    private final int duration;

    public TimedQuote(double price, int duration){
        this.price = price;
        this.timestamp = System.currentTimeMillis();
        this.duration = duration;
    }

    public double getPrice() {
        return price;
    }

    public int getInterval(){
        return (int) (timestamp % duration);
    }

    public long getTimestamp(){
        return timestamp;
    }

    @Override
    public int compareTo(Object quote) {
        return Double.compare(price, ((TimedQuote)quote).getPrice());
    }
}
