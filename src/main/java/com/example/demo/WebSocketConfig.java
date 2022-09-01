package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

// Reference documentation https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#websocket
// Spring does support websocket in addition to SockJS and STOMP sub-protocol. This demo is for the minimal barebone websocket.
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
	private final WebSocketHandler webSocketHandler;
	@Bean
	public ServletServerContainerFactoryBean createWebSocketContainer() {
		// Given that Spring Boot uses Tomcat by default (unless we tell it to use another servlet), we can use the following to configure
		//  the desired behaviour of the websocket
		final ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
		container.setMaxTextMessageBufferSize(8192);
		container.setMaxBinaryMessageBufferSize(8192);
		container.setMaxSessionIdleTimeout(1_000_000L); // Wait for 1,000,000 milliseconds before time out
		return container;
	}
	public WebSocketConfig(final WebSocketHandler webSocketHandler) {
		this.webSocketHandler = webSocketHandler;
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(this.webSocketHandler, "/websocket");
	}
}
