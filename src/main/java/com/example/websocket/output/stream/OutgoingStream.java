package com.example.websocket.output.stream;

import com.example.websocket.model.outgoing.HotInstrument;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.*;

@Slf4j
@Component
public class OutgoingStream implements WebSocketHandler {

    private static final ObjectMapper json = new ObjectMapper();
    private List<HotInstrument> list = new ArrayList<>();
    public FluxEventListener<String> eventListener = () -> {log.debug("Do Nothing");};

    public void sendHotInstrument(HotInstrument instrument){
        this.list.add(instrument);
        eventListener.onEvent();
    }

    private final Flux<String> bridge = Flux.push(sink -> {
        eventListener = () -> {
            if(list.size()>5)
                log.debug("Add {} Instrument", list.size());
            else for(HotInstrument a : list)
                log.debug("Stream {}", a.getInstrument().getIsin());
            while(list.size()>0){
                try {
                    sink.next(json.writeValueAsString(list.get(0)));
                    list.remove(0);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        };
        if(list.size()>0)
            eventListener.onEvent();
    });

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session.send(bridge.map(session::textMessage));
    }
}
