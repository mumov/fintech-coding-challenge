package com.example.websocket.model.internal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TimedQuoteBuilder {

    @Value("${candlestick.length.millies}")
    private int duration;

    public TimedQuote buildTimedQuote(double price){
        return new TimedQuote(price, duration);
    }

    public long getIntervalStart(long timeStamp){
        return timeStamp - (timeStamp % duration);
    }

    public long getIntervalEnd(long timeStamp){
        return timeStamp - (timeStamp % duration) + duration;
    }
}
