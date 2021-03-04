package com.example.websocket.model.outgoing;

import com.example.websocket.model.internal.AggregatedInstrument;

public class HotInstrument {

    AggregatedInstrument instrument;
    double percentageChange;

    public AggregatedInstrument getInstrument() {
        return instrument;
    }

    public void setInstrument(AggregatedInstrument aggregatedInstrument) {
        this.instrument = aggregatedInstrument;
    }

    public double getPercentageChange() {
        return percentageChange;
    }

    public void setPercentageChange(double percentageChange) {
        this.percentageChange = 100*percentageChange;
    }

    @Override
    public String toString() {
        return "HotInstrument{" +
                "instrument=" + instrument +
                ", percentageChange=" + percentageChange +
                '}';
    }
}
