package com.example.websocket.internal;

import com.example.websocket.model.internal.TimedQuoteBuilder;
import com.example.websocket.model.internal.CandleStick;
import com.example.websocket.model.internal.CandleStickChart;
import com.example.websocket.model.internal.TimedQuote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.*;

@Component
public class CandleStickStore {
    HashMap<String, ArrayList<TimedQuote>> map;
    HashMap<String, CandleStickChart> chart;

    @Value("${hot.instrument.interval}")
    int interval;
    @Value("${hot.instrument.percentage}")
    double percentage;
    @Autowired
    TimedQuoteBuilder timedQuoteBuilder;

    public CandleStickStore(){
        map = new HashMap<>();
        chart = new HashMap<>();
    }

    public double addPrice(String isin, double price){
        double isVolatile = 0.0;
        TimedQuote quote = timedQuoteBuilder.buildTimedQuote(price);
        if(map.containsKey(isin)){
            if(quote.getInterval()<map.get(isin).get(0).getInterval()){
                isVolatile = createCandleStick(isin, map.get(isin).get(0).getTimestamp());
                map.get(isin).clear();
            }
            map.get(isin).add(0, quote);
        } else {
            ArrayList<TimedQuote> list = new ArrayList<>();
            list.add(quote);
            map.put(isin, list);
        }
        return isVolatile;
    }

    private double createCandleStick(String isin, long timeStamp) {
        Timestamp last = new Timestamp(timedQuoteBuilder.getIntervalStart(timeStamp));
        Timestamp next = new Timestamp(timedQuoteBuilder.getIntervalEnd(timeStamp));
        return createCandleStick(isin, last, next);
    }

    private double createCandleStick(String isin, Timestamp last, Timestamp next){
        CandleStick candle = new CandleStick(map.get(isin), last, next);
        return addCandle(isin, candle);
    }

    private double addCandle(String isin, CandleStick candle) {
        double isVolatile;
        if(chart.containsKey(isin)){
            isVolatile = chart.get(isin).updateChart(candle);
        }else{
            CandleStickChart candleStickChart = new CandleStickChart(interval, percentage);
            isVolatile = candleStickChart.updateChart(candle);
            chart.put(isin, candleStickChart);
        }
        return isVolatile;
    }

    public void removeIsin(String isin) {
        map.remove(isin);
        chart.remove(isin);
    }

    public LinkedList<CandleStick> getCharts(String isin){
        if(chart.containsKey(isin))
            return chart.get(isin).getChart();
        else return new LinkedList<>();
    }
}
