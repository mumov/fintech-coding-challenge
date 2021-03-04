package com.example.websocket.output.rest;

import com.example.websocket.internal.InstrumentStore;
import com.example.websocket.model.internal.AggregatedInstrument;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class InstrumentBuffer {

    @Autowired
    InstrumentStore instrumentStore;
    @Value("${caching.duration}")
    int duration;
    @Value("${page.size}")
    int size;
    List<AggregatedInstrument> buffer = new ArrayList<>();
    long lastCached;


    public List<AggregatedInstrument> getAggregatedInstruments(){
        if(lastCached + duration > System.currentTimeMillis()){
            log.debug("Instruments Cached til {}", new Timestamp(lastCached + duration));
            return buffer;
        }
        log.debug("Instruments Retrieved");
        List<AggregatedInstrument> result = instrumentStore.getInstruments();
        lastCached = System.currentTimeMillis();
        buffer = result;
        return result;
    }

    public List<AggregatedInstrument> getAggregatedInstrumentsPaged(int id) {
        buffer = getAggregatedInstruments();
        List<AggregatedInstrument> result = new ArrayList<>();
        for(int i = id*size; i < Math.min((id*size)+size, buffer.size()); i++)
            result.add(buffer.get(i));
        return result;
    }
}
