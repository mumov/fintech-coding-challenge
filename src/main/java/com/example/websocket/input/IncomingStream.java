package com.example.websocket.input;

import com.example.websocket.internal.StreamBroker;
import com.example.websocket.model.incoming.Instrument;
import com.example.websocket.model.incoming.Quote;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;

import java.net.URI;
import java.util.Timer;
import java.util.TimerTask;

@Slf4j
public class IncomingStream {

    @Autowired
    StreamBroker  streamBroker;
    private String instruments = "/instruments";
    private String quotes = "/quotes";

    public IncomingStream(String url) {
        getStream(url + instruments, Instrument.class);
        getStream(url + quotes, Quote.class);
    }

    private <T> void getStream(String uri, Class<T> modelType){
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                    try{
                        connect(uri, modelType);
                    } catch(RuntimeException e){
                        log.error("Couldn't establish connection to {}", uri);
                    }}
        };
        timer.schedule(task,1000L, 5000L);
    }

    private <T> void connect(String uri, Class<T> modelType){
        WebSocketClient client = new ReactorNettyWebSocketClient();
        ObjectMapper mapper = new ObjectMapper();
        client.execute(
                URI.create(uri),
                session -> session.receive()
                        .map(message -> {
                            try {
                                T model = mapper.readValue(message.getPayloadAsText(), modelType);
                                log.debug(model.toString());
                                streamBroker.updateMap(model);
                                return model;
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                                return "ERROR";
                            }
                        })
                        .then())
                .block();
    }
}
