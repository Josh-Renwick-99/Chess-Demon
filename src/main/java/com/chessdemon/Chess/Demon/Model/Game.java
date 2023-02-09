package com.chessdemon.Chess.Demon.Model;

import com.github.bhlangonijr.chesslib.Board;
import lombok.Data;

@Data
public class Game {
    String position;
    String turn;
    String discordId;
    String move;

    String san;

    public Game(String position, String turn, String discordId, String move, String san){
        this.position = position;
        this.turn = turn;
        this.discordId = discordId;
        this.move = move;
        this.san = san;
    }

    public Game(String position, String turn, String discordId){
        this.position = position;
        this.turn = turn;
        this.discordId = discordId;
    }
}
