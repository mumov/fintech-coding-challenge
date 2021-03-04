package com.example.websocket.output.stream;

import java.util.EventListener;

public interface FluxEventListener<T> extends EventListener {
    void onEvent();
}
