package com.chessdemon.Chess.Demon;

import com.chessdemon.Chess.Demon.Configuration.ChessDemonConfigurationProperties;
import com.chessdemon.Chess.Demon.Service.DBService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;

@SpringBootTest
class ChessDemonApplicationTests {

	@Autowired
	ChessDemonConfigurationProperties config;
	@Test
	void testNewUser() {
		DBService dbService = new DBService(config);
		try {
			dbService.newUser("new", new Date(System.currentTimeMillis()), 1, 1);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Test
	void testNewGame() {
		DBService dbService = new DBService(config);
		try {
			dbService.newGame("new", "newnew", "");
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	@Test
	void testUpdateGame() {
		DBService dbService = new DBService(config);
		try {
			dbService.updateGame("testUser", "", "E4");
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
