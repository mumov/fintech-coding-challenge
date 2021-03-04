package com.example.websocket.internal;

import com.example.websocket.model.incoming.Instrument;
import com.example.websocket.model.incoming.Quote;
import com.example.websocket.model.internal.AggregatedInstrument;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Component
public class InstrumentStore {
    @Autowired
    CandleStickStore candles;

    private HashMap<String, AggregatedInstrument> map = new HashMap<>();

    public List<AggregatedInstrument> getInstruments(){
        return new ArrayList<>(map.values());
    }

    public void addInstrument(Instrument instrument){
        AggregatedInstrument aggregatedInstrument = new AggregatedInstrument();
        aggregatedInstrument.setDescription(instrument.getData().getDescription());
        aggregatedInstrument.setIsin(instrument.getData().getIsin());
        map.put(instrument.getData().getIsin(), aggregatedInstrument);
    }

    public void deleteInstrument(Instrument instrument){
        map.remove(instrument.getData().getIsin());
    }

    public void updateQuote(Quote quote){
        if(map.containsKey(quote.getData().getIsin()))
            map.get(quote.getData().getIsin()).setPrice(quote.getData().getPrice());
    }

    public AggregatedInstrument getInstrumentByIsin(String isin) {
        return map.get(isin);
    }

}
