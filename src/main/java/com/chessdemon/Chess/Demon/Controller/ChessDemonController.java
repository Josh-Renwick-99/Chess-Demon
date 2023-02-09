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

    @GetMapping("/NewGame")
    public void newGame(@RequestParam String discordId){
        dbService.newGame(discordId);
    }
}
