package com.example.websocket;

import com.example.websocket.internal.InstrumentStore;
import com.example.websocket.model.incoming.*;
import com.example.websocket.model.internal.AggregatedInstrument;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InstrumentStoreTest {

    HashMap<String, AggregatedInstrument> map = new HashMap<>();
    @InjectMocks
    InstrumentStore instrumentStore;

    @Before
    public void setup(){
        ReflectionTestUtils.setField(instrumentStore, "map", map);

    }
    @Test
    public void testAddInstrumentAndDeleteInstrument(){
        Instrument instrument = new Instrument();
        InstrumentData data = new InstrumentData();
        data.setIsin("123");
        data.setDescription("test");
        instrument.setData(data);

        instrument.setType(Type.ADD);
        instrumentStore.addInstrument(instrument);
        Assert.assertTrue(map.containsKey("123"));

        instrument.setType(Type.DELETE);
        instrumentStore.deleteInstrument(instrument);
        Assert.assertFalse(map.containsKey("123"));
    }

    @Test
    public void testDeleteInstrumentAndAddInstrumentTwice(){
        Instrument instrument = new Instrument();
        InstrumentData data = new InstrumentData();
        data.setIsin("123");
        data.setDescription("test");
        instrument.setData(data);

        instrument.setType(Type.DELETE);
        instrumentStore.deleteInstrument(instrument);
        Assert.assertFalse(map.containsKey("123"));

        instrument.setType(Type.ADD);
        instrumentStore.addInstrument(instrument);
        Assert.assertTrue(map.containsKey("123"));

        instrument.setType(Type.ADD);
        instrumentStore.addInstrument(instrument);
        Assert.assertTrue(map.containsKey("123"));
    }

    @Test
    public void testUpdateQuote(){
        AggregatedInstrument instrument = new AggregatedInstrument();
        map.put("123", instrument);
        Quote quote = new Quote();
        QuoteData data = new QuoteData();
        data.setIsin("123");
        data.setPrice(100.0);
        quote.setData(data);
        instrumentStore.updateQuote(quote);
        Assert.assertEquals(map.get("123").getPrice(), 100.0, 0000.1);
    }
}
