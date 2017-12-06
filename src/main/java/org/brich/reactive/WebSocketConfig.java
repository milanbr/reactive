package org.brich.reactive;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.brich.reactive.user.UserReactiveRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Milan Brich (cen76482).
 */
@Configuration
public class WebSocketConfig {

    @Bean
    public HandlerMapping handlerMapping(UserReactiveRepository userReactiveRepository, ObjectMapper mapper) {
        Map<String, WebSocketHandler> map = new HashMap<>();
        map.put("/socket/api/users", new MyWebSocketHandler(userReactiveRepository, mapper));

        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setUrlMap(map);
        mapping.setOrder(-1); // before annotated controllers
        return mapping;
    }

    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }
}
