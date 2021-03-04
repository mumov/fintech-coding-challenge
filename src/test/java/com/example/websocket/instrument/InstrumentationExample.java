package com.example.websocket.instrument;

import com.example.websocket.model.incoming.*;
import com.example.websocket.model.internal.AggregatedInstrument;
import com.example.websocket.model.internal.CandleStick;
import com.example.websocket.model.internal.TimedQuote;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * How to Run
 * Before running, execute following commands in instrument directory:
 * $ javac InstrumentAgent.java
 * $ jar cmf MANIFEST.MF InstrumentAgent.jar InstrumentAgent.class
 * Then run with following VM Options: -javaagent:"src/test/java/com/example/websocket/instrument/InstrumentAgent.jar"
 * **/
public class InstrumentationExample {

    public static void printObjectSize(Object object) {
        System.out.println("Object type: " + object.getClass() + ", size: " + InstrumentAgent.getObjectSize(object) + " bytes");
    }

    public static void printObjectsSize(Object object, int amount) {
        System.out.println("Object type: " + object.getClass() + ", amount: " + amount + ", size: " + (InstrumentAgent.getObjectSize(object) * amount)/1000000.0 + " megabytes");
    }

    public static void main(String[] argument) {
        InstrumentData iData = new InstrumentData();
        iData.setDescription("elementum eos accumsan orci constituto antiopam");
        iData.setIsin("LS342I184454");
        Instrument instrument = new Instrument();
        instrument.setData(iData);
        instrument.setType(Type.DELETE);

        QuoteData qData = new QuoteData();
        qData.setPrice(1365.25);
        qData.setIsin("LS342I184454");
        Quote quote = new Quote();
        quote.setData(qData);
        quote.setType(Type.QUOTE);

        ArrayList<TimedQuote> list = new ArrayList<>();
        for(int i = 0; i < 30; i++)
            list.add(new TimedQuote(Math.random()*1000, 60000));
        CandleStick candle = new CandleStick(list, new Timestamp(1609455600000L), new Timestamp(1609455660000L));

        AggregatedInstrument aInstrument = new AggregatedInstrument();
        aInstrument.setDescription(iData.getDescription());
        aInstrument.setIsin(iData.getDescription());
        aInstrument.setPrice(qData.getPrice());

        System.out.println("Size per Object");
        printObjectSize(instrument);
        printObjectSize(quote);
        printObjectSize(candle);
        printObjectSize(aInstrument);

        int amount = 50000;

        System.out.println("");
        System.out.println("Size per " + amount + " Objects");
        printObjectsSize(instrument, amount);
        printObjectsSize(quote, 3000000);
        printObjectsSize(candle, amount);
        printObjectsSize(aInstrument, amount);
    }
}
