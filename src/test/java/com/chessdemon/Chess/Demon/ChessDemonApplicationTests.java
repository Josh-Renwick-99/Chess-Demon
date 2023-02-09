package com.chessdemon.Chess.Demon;

import com.chessdemon.Chess.Demon.Configuration.ChessDemonConfigurationProperties;
import com.chessdemon.Chess.Demon.Model.Crud.GameCrud;
import com.chessdemon.Chess.Demon.Service.DBService;
import com.github.bhlangonijr.chesslib.Board;
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
	void findUserGames() {
		DBService dbService = new DBService(config);
		try {
			System.out.println(dbService.findGames("testUser"));
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Test
	void findActiveGame() {
		DBService dbService = new DBService(config);
		try {
			GameCrud game = dbService.findActiveGame("new");
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Test
	void testMove(){
		Board board = new Board();
		System.out.println(board.getFen());
		board.doMove("e4");
		System.out.println(board.getFen());
	}
}
