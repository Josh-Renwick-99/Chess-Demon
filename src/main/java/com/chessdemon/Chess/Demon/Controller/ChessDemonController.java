package com.chessdemon.Chess.Demon.Controller;

import com.chessdemon.Chess.Demon.Exception.IllegalMoveException;
import com.chessdemon.Chess.Demon.Model.Crud.GameCrud;
import com.chessdemon.Chess.Demon.Model.Game;
import com.chessdemon.Chess.Demon.Model.GameView;
import com.chessdemon.Chess.Demon.Model.StockfishRequest;
import com.chessdemon.Chess.Demon.Model.StockfishResponse;
import com.chessdemon.Chess.Demon.Service.DBService;
import com.chessdemon.Chess.Demon.Service.StockfishService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.bhlangonijr.chesslib.Board;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@RestController
public class ChessDemonController {
    @Autowired
    DBService dbService;

    @Autowired
    StockfishService stockfishService;

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
    public ResponseEntity<Game> move(@RequestParam String discordId, @RequestParam String move) throws JsonProcessingException {
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
        StockfishRequest request = new StockfishRequest(san, 5, 5);
        StockfishResponse response = stockfishService.getNextMove(request);
        dbService.updatePosition(discordId, response.getFen(), response.getLAN());
        Game returnGame = new Game(response.getFen(), board.getSideToMove().name(), discordId, move, response.getLAN());
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

    @GetMapping
    public Game swapGame(@RequestParam String discordId, @RequestParam Integer gameId){
        GameCrud game = dbService.findActiveGame(discordId);
        String san = "";
        Board board = new Board();
        board.loadFromFen(game.getCurrentFen());
        dbService.updatePosition(discordId, board.getFen(), san);
        dbService.setActiveGame(discordId, gameId);
        Game returnGame = new Game(board.getFen(), board.getSideToMove().name(), discordId, null, san);
        returnGame.setPosition(board.getFen());
        return returnGame;
    }

    @DeleteMapping
    public boolean deleteUser(@RequestParam String discordId) throws Exception {
        try {
            dbService.deleteUser(discordId);
            return true;
        } catch (Exception e){
            throw new Exception(e);
        }
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
