package com.chessdemon.Chess.Demon;

import com.chessdemon.Chess.Demon.Configuration.ChessDemonConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChessDemonApplication implements CommandLineRunner {
	@Autowired
	ChessDemonConfigurationProperties config;

	public static void main(String[] args) {
		SpringApplication.run(ChessDemonApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(config.getName());
	}
}
