package com.example.websocket.model.internal;

import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

@Slf4j
public class CandleStick {

    Timestamp openTimeStamp;
    Timestamp closeTimestamp;
    double openPrice;
    double closePrice;
    double highPrice;
    double lowPrice;

    public CandleStick(List<TimedQuote> list, Timestamp start, Timestamp finish){
        openTimeStamp = start;
        closeTimestamp = finish;
        openPrice = list.get(0).getPrice();
        closePrice = list.get(list.size()-1).getPrice();
        highPrice = Collections.max(list).getPrice();
        lowPrice = Collections.min(list).getPrice();
        logCandle();
    }

    private void logCandle(){
        log.debug("-----------------------------------------");
        log.debug("openTimestamp: {}", openTimeStamp);
        log.debug("openPrice: {}", openPrice);
        log.debug("highPrice: {}", highPrice);
        log.debug("lowPrice: {}", lowPrice);
        log.debug("closePrice: {}", closePrice);
        log.debug("closeTimestamp: {}", closeTimestamp);
        log.debug("-----------------------------------------");
    }

    public Timestamp getOpenTimeStamp() {
        return openTimeStamp;
    }

    public void setOpenTimeStamp(Timestamp openTimeStamp) {
        this.openTimeStamp = openTimeStamp;
    }

    public Timestamp getCloseTimestamp() {
        return closeTimestamp;
    }

    public void setCloseTimestamp(Timestamp closeTimestamp) {
        this.closeTimestamp = closeTimestamp;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    public double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(double closePrice) {
        this.closePrice = closePrice;
    }

    public double getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(double highPrice) {
        this.highPrice = highPrice;
    }

    public double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(double lowPrice) {
        this.lowPrice = lowPrice;
    }

    @Override
    public String toString() {
        return "CandleStick{" +
                "openTimeStamp=" + openTimeStamp +
                ", closeTimestamp=" + closeTimestamp +
                ", openPrice='" + openPrice + '\'' +
                ", closePrice='" + closePrice + '\'' +
                ", highPrice='" + highPrice + '\'' +
                ", lowPrice='" + lowPrice + '\'' +
                '}';
    }
}
