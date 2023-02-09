package com.chessdemon.Chess.Demon.Model;

import com.github.bhlangonijr.chesslib.Board;

public class Game {
    Board board;
    boolean whiteTurn;
    String discordId;
    String move;

    public Game(Board board, boolean whiteTurn, String discordId, String move){
        this.board = board;
        this.whiteTurn = whiteTurn;
        this.discordId = discordId;
        this.move = move;
    }
}
