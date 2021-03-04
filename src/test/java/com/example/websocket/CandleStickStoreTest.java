package com.example.websocket;

import com.example.websocket.internal.CandleStickStore;
import com.example.websocket.model.internal.TimedQuote;
import com.example.websocket.model.internal.TimedQuoteBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.HashMap;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CandleStickStoreTest {

    @Mock
    TimedQuoteBuilder timedQuoteBuilder;
    @InjectMocks
    CandleStickStore candleStickStore = new CandleStickStore();
    HashMap<String, ArrayList<TimedQuote>> map = new HashMap<>();

    @Before
    public void setup(){
        ReflectionTestUtils.setField(candleStickStore, "map", map);
    }

    @Test
    public void testAddPrice(){
        TimedQuote quote = new TimedQuote(110.0, 6000);
        doReturn(quote).when(timedQuoteBuilder).buildTimedQuote(anyDouble());
        candleStickStore.addPrice("123", 100.0);
        Assert.assertEquals(map.get("123").size(), 1);
        Assert.assertEquals(map.get("123").get(0), quote);

        // contains key
        candleStickStore.addPrice("123", 110.0);
        Assert.assertEquals(map.get("123").size(), 2);
        Assert.assertEquals(map.get("123").get(0), quote);
    }
}
