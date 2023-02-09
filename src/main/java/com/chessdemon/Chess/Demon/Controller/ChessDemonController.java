package com.chessdemon.Chess.Demon.Controller;

import com.chessdemon.Chess.Demon.Model.Crud.GameCrud;
import com.chessdemon.Chess.Demon.Service.DBService;
import com.github.bhlangonijr.chesslib.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.sql.SQLException;

@RestController
public class ChessDemonController {
    @Autowired
    DBService dbService;
    @Value("${spring.application.name}")
    String appName;

    @GetMapping("/ping")
    public String ping() {
        return "pong " + appName;
    }

    @GetMapping("/move")
    public String move(@RequestParam String discordId, @RequestParam String move){
        GameCrud game = dbService.findActiveGame(discordId);
        Board board = new Board();
        board.loadFromFen(game.getCurrentFen());
        board.doMove(move);
        dbService.updatePosition(discordId, board.getFen());
        return board.getFen();
    }

    @GetMapping("/newgame")
    public String newGame(@RequestParam String discordId) throws SQLException, ClassNotFoundException {
        if (dbService.userExist(discordId)) {
            dbService.newUser(discordId, new Date(System.currentTimeMillis()), 1, 1);
        }
        dbService.newGame(discordId);
        Board board = new Board();
        System.out.println(String.format("Completed request from '%s' to create new game", discordId));
        return board.getFen();
    }
}
