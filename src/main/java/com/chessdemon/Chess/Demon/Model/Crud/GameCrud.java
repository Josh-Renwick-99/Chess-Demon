package com.chessdemon.Chess.Demon.Model.Crud;

import lombok.Data;

@Data
public class GameCrud {
    String moveHistory;
    String winner;
    String lastMove;
    String currentFen;

    public GameCrud(String moveHistory, String winner, String lastMove, String currentFen){
        this.moveHistory = moveHistory;
        this.winner = winner;
        this.lastMove = lastMove;
        this.currentFen = currentFen;
    }
}
