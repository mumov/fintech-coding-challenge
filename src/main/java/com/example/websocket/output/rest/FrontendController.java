package com.example.websocket.output.rest;

import com.example.websocket.model.internal.AggregatedInstrument;
import com.example.websocket.model.internal.CandleStick;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FrontendController {

    @Autowired
    InstrumentBuffer instrumentBuffer;
    @Autowired
    CandleStickBuffer candleStickBuffer;

    @GetMapping(value = "/instruments", produces = "application/json")
    public List<AggregatedInstrument> getInstrument(){
        return instrumentBuffer.getAggregatedInstruments();
    }

    @GetMapping(value="/instruments/{id}", produces = "application/json")
    public List<CandleStick> getPriceHistory(@PathVariable String id){
        return candleStickBuffer.getCandleStickCharts(id);
    }

    @RequestMapping(value = "/client", method = RequestMethod.GET)
    public Resource webClient() {
        return new ClassPathResource("browserClient.html");
    }

    @GetMapping(value = "/page/{id}", produces = "application/json")
    public List<AggregatedInstrument> getInstrumentPaged(@PathVariable int id){
        return instrumentBuffer.getAggregatedInstrumentsPaged(id);
    }
}
