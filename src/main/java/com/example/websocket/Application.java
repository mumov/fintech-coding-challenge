package com.example.websocket;

import com.example.websocket.input.IncomingStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@SpringBootApplication
public class Application {
    @Value("${websocket.url}")
    private String url;
    @Value("${websocket.port}")
    private String port;
    @Value("${candlestick.length.millies}")
    private int length;
    @Value("${hot.instrument.interval}")
    int interval;
    @Value("${hot.instrument.percentage}")
    double percentage;
    @Value("${caching.duration}")
    int caching;
    @Value("${page.size}")
    int page;

    @Bean
    LoggerConfig getLogger(){
        log.info("### PARAMETERS ###");
        log.info("CandleStick length {}", formatTime(length));
        log.info("Hot Instrument comparison interval {}",formatTime(interval *length));
        log.info("Hot Instrument threshold: {}%", (int)(percentage*100));
        log.info("### FRONTEND ###");
        log.info("Caching duration {}", formatTime(caching));
        log.info("Page size {}", page);
        return new LoggerConfig();
    }

    @Bean
    IncomingStream incomingStream(){
        return new IncomingStream(url+":"+port);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    public static String formatTime(int time){
        return new SimpleDateFormat("mm:ss").format(new Date(time));
    }

    class LoggerConfig {
    }
}
