package com.example.websocket.output.rest;

import com.example.websocket.internal.CandleStickStore;
import com.example.websocket.model.internal.CandleStick;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class CandleStickBuffer {
    @Value("${candlestick.length.millies}")
    int cachingTime;

    @Autowired
    CandleStickStore candleStickStore;
    HashMap<String, List<CandleStick>> buffer = new HashMap<>();

    public List<CandleStick> getCandleStickCharts(String id){
        if(buffer.containsKey(id) && buffer.get(id).size()>0){
            if(buffer.get(id).get(0).getCloseTimestamp().getTime() + cachingTime > System.currentTimeMillis()){
                log.debug("Cached til {}", new Timestamp(buffer.get(id).get(0).getCloseTimestamp().getTime() + cachingTime));
                return buffer.get(id);
            }else
                log.debug("NOT cached since {}", new Timestamp(buffer.get(id).get(0).getCloseTimestamp().getTime() + cachingTime));
        }
        log.debug("Retrieved charts");
        List<CandleStick> list = candleStickStore.getCharts(id);
        buffer.put(id, list);
        return list;
    }
}
