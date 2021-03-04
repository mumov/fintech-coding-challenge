package com.example.websocket;

import com.example.websocket.model.internal.CandleStick;
import com.example.websocket.model.internal.CandleStickChart;
import com.example.websocket.model.internal.TimedQuote;
import com.example.websocket.model.internal.Velocity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.ArrayList;

import static org.springframework.test.util.ReflectionTestUtils.setField;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CandleStickChartTest {

    CandleStickChart candleStickChart = new CandleStickChart(60000, 0.1);
    int interval = 5;
    double percentage = 0.1;
    long[] millis = {1609455600000L, 1609455660000L, 1609455720000L, 1609455780000L, 1609455840000L, 1609455900000L, 1609455960000L, 1609456020000L, 1609456080000L, 1609456140000L, 1609456200000L};
    @Test
    public void testRise(){
        setField(candleStickChart, "percentage", percentage);
        double result;
        CandleStick first, second;

        //Test no rise
        first = generateCandle(0, 100.0, 100.0, 101.0, 99.0);
        second = generateCandle(1, 100.0, 100.0, 101.0, 99.0);
        setField(candleStickChart, "velocity", Velocity.NONE);
        result = candleStickChart.percentageRise(first, second);
        Assert.assertTrue(result < percentage);

        //Test rise
        first = generateCandle(2, 100.0, 100.0, 101.0, 99.0);
        second = generateCandle(3, 100.0, 100.0, 110.0, 99.0);
        setField(candleStickChart, "velocity", Velocity.NONE);
        result = candleStickChart.percentageRise(first, second);
        Assert.assertTrue(result > percentage);

        //Test rise with RISING velocity
        first = generateCandle(4, 100.0, 100.0, 101.0, 99.0);
        second = generateCandle(5, percentage, 100.0, 110.0, 99.0);
        setField(candleStickChart, "velocity", Velocity.RISING);
        result = candleStickChart.percentageRise(first, second);
        Assert.assertTrue(result < percentage);

    }

    @Test
    public void testFall(){
        setField(candleStickChart, "percentage", percentage);
        double result;
        CandleStick first, second;

        //Test no fall
        first = generateCandle(0, 100.0, 100.0, 101.0, 99.0);
        second = generateCandle(1, 100.0, 100.0, 101.0, 99.0);
        setField(candleStickChart, "velocity", Velocity.NONE);
        result = candleStickChart.percentageFall(first, second);
        Assert.assertTrue(result > -percentage);

        //Test fall
        first = generateCandle(2, 100.0, 100.0, 101.0, 99.0);
        second = generateCandle(3, 100.0, 100.0, 101.0, 90.0);
        setField(candleStickChart, "velocity", Velocity.NONE);
        result = candleStickChart.percentageFall(first, second);
        Assert.assertTrue(result < -percentage);

        //Test fall with FALLING velocity
        first = generateCandle(4, 100.0, 100.0, 101.0, 99.0);
        second = generateCandle(5, 100.0, 100.0, 101.0, 90.0);
        setField(candleStickChart, "velocity", Velocity.FALLING);
        result = candleStickChart.percentageFall(first, second);
        Assert.assertTrue(result > -percentage);
    }

    private CandleStick generateCandle(int count, double open, double close, double high, double low){
        ArrayList<TimedQuote> list = new ArrayList<>();
        list.add(new TimedQuote(open, interval));
        list.add(new TimedQuote(high, interval));
        list.add(new TimedQuote(low, interval));
        list.add(new TimedQuote(close, interval));
        return new CandleStick(list, new Timestamp(millis[count]), new Timestamp(millis[count+1]));
    }
}
