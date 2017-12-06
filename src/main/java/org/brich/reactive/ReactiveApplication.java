package org.brich.reactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReactiveApplication {

	private static final String PROXY_SERVER = "127.0.0.1";
	private static final String PROXY_PORT = "3128";

	public static void main(String[] args) {
//		System.setProperty("http.proxyHost", PROXY_SERVER);
//		System.setProperty("http.proxyPort", PROXY_PORT);
//		System.setProperty("https.proxyHost", PROXY_SERVER);
//		System.setProperty("https.proxyPort", PROXY_PORT);

		SpringApplication.run(ReactiveApplication.class, args);
	}
}
