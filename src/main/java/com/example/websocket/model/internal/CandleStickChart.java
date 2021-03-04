package com.example.websocket.model.internal;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

@Slf4j
public class CandleStickChart {


    private double percentage;
    private int interval;
    LinkedList<CandleStick> chart;
    Velocity velocity;

    public CandleStickChart(int interval, double percentage){
        chart = new LinkedList<>();
        this.interval = interval;
        this.percentage = percentage;
        velocity = Velocity.NONE;
    }

    public double updateChart(CandleStick candleStick){
        double result = 0.0;
        if(chart.size()>=30)
            chart.removeLast();
        if(chart.size()>(interval-1)){
            result = percentageRise(chart.get(interval-1), candleStick);
            if(result > percentage)
                return result;
            result = percentageFall(chart.get(interval-1), candleStick);
            if (result < -percentage)
                return result;
        }
        chart.add(0, candleStick);
        return result;
    }
    public LinkedList<CandleStick> getChart() {
        return chart;
    }

    public double percentageRise(CandleStick first, CandleStick second){
        if(velocity.equals(Velocity.RISING) || (first.getLowPrice() * (1.0 + percentage) > second.getHighPrice()))
            return 0.0;
        velocity = Velocity.RISING;
        log.debug("From {} til {}: Price rose from {} to {}, thus {}%",first.getOpenTimeStamp(), second.closeTimestamp, first.getLowPrice(), second.getHighPrice(), (int)(100*((second.getHighPrice()/first.getLowPrice())-1)));
        return (((second.getHighPrice()/first.getLowPrice())-1.0));
    }

    public double percentageFall(CandleStick first, CandleStick second){
        if(velocity.equals(Velocity.FALLING) || (first.getHighPrice() * (1.0 - percentage) < second.getLowPrice()))
            return 0.0;
        velocity = Velocity.FALLING;
        log.debug("From {} til {}: Price fell from {} to {}, thus {}%",first.getOpenTimeStamp(), second.closeTimestamp, first.getHighPrice(), second.getLowPrice(), (int)(100*(1-(second.getHighPrice()/first.getLowPrice()))));
        return second.getLowPrice()/first.getHighPrice()-1.0;
    }
}
