package com.chessdemon.Chess.Demon.Controller;

import com.chessdemon.Chess.Demon.Exception.IllegalMoveException;
import com.chessdemon.Chess.Demon.Model.Crud.GameCrud;
import com.chessdemon.Chess.Demon.Model.Game;
import com.chessdemon.Chess.Demon.Model.GameView;
import com.chessdemon.Chess.Demon.Service.DBService;
import com.github.bhlangonijr.chesslib.Board;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@Slf4j
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

    @GetMapping("/games")
    public ResponseEntity<List<GameView>> games(@RequestParam String discordId){
        log.info(String.format("Received games request from '%s'", discordId));
        List<GameView> games = dbService.findGames(discordId);
        if (games.size() >= 1){
            return new ResponseEntity<>(games, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/move")
    public ResponseEntity<Game> move(@RequestParam String discordId, @RequestParam String move){
        GameCrud game = dbService.findActiveGame(discordId);
        String san = "";
        if (game.getSan() == null){
            san = move;
        } else{
            san = String.format("%s %s", game.getSan(), move);
        }
        Board board = new Board();
        board.loadFromFen(game.getCurrentFen());
        try {
            if (!board.isMated()){
                board.doMove(move);
                if(board.isMated()){
                    dbService.checkMateGame(discordId, move, san, board.getFen(), board.getSideToMove().name());
                    dbService.removeActiveGame(discordId);
                }
            }
        } catch (Exception e){
            log.warn(String.format("User '%s' made illegal move"), discordId);
            throw new IllegalMoveException(move);
        }
        dbService.updatePosition(discordId, board.getFen(), san);
        Game returnGame = new Game(board.getFen(), board.getSideToMove().name(), discordId, move, san);
        returnGame.setMated(board.isMated());
        returnGame.setPosition(board.getFen());
        return new ResponseEntity<>(returnGame, HttpStatus.OK);
    }

    @GetMapping("/newgame")
    public ResponseEntity<Game> newGame(@RequestParam String discordId, @RequestParam String threadName) throws SQLException, ClassNotFoundException {
        if (dbService.userExist(discordId)) {
            dbService.newUser(discordId, new Date(System.currentTimeMillis()), 1, 1);
        }
        dbService.newGame(discordId, threadName);
        Board board = new Board();
        System.out.println(String.format("Completed request from '%s' to create new game", discordId));
        Game returnGame = new Game(board.getFen(), board.getSideToMove().name(), discordId);
        return new ResponseEntity<>(returnGame, HttpStatus.OK);
    }

    @GetMapping("/getThread")
    public ResponseEntity<String> getThread(String discordId){
        String threadName = dbService.findThreadOfActiveGame(discordId);
        if (threadName == null){
            return new ResponseEntity<>("User has no active game", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(threadName, HttpStatus.OK);
        }
    }
}
