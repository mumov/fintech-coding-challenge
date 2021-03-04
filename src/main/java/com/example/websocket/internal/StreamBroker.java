package com.example.websocket.internal;

import com.example.websocket.model.incoming.Instrument;
import com.example.websocket.model.incoming.Quote;
import com.example.websocket.model.incoming.Type;
import com.example.websocket.model.internal.AggregatedInstrument;
import com.example.websocket.model.outgoing.HotInstrument;
import com.example.websocket.output.stream.OutgoingStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StreamBroker {

    @Autowired
    InstrumentStore instrumentStore;
    @Autowired
    CandleStickStore candleStickStore;
    @Autowired
    OutgoingStream outgoingStream;
    @Value("${hot.instrument.percentage}")
    double percentage;

    public <T> void updateMap(T model){
        if(model instanceof Instrument){
            updateInstrument((Instrument) model);
        } else if(model instanceof Quote){
            updateQuote((Quote) model);
        }
    }

    private void updateInstrument(Instrument instrument) {
        if (instrument.getType().equals(Type.ADD)) {
            instrumentStore.addInstrument(instrument);
        } else if (instrument.getType().equals(Type.DELETE)) {
            candleStickStore.removeIsin(instrument.getData().getIsin());
            instrumentStore.deleteInstrument(instrument);
        }
    }

    void updateQuote(Quote quote){
        instrumentStore.updateQuote(quote);
        double isVolatile = candleStickStore.addPrice(quote.getData().getIsin(), quote.getData().getPrice());
        if(isVolatile > percentage || isVolatile < -percentage){
            AggregatedInstrument instrument = instrumentStore.getInstrumentByIsin(quote.getData().getIsin());
            if(instrument!=null){
                HotInstrument hotInstrument = new HotInstrument();
                hotInstrument.setInstrument(instrument);
                hotInstrument.setPercentageChange(isVolatile);
                outgoingStream.sendHotInstrument(hotInstrument);
            }
        }
    }

}
