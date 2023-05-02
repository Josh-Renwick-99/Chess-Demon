package com.chessdemon.Chess.Demon.Model;

public class StockfishRequest {

    public StockfishRequest(String fen, Integer skillLevel, Integer depth){
        this.fen = fen;
        this.skillLevel = skillLevel;
        this.depth = depth;
    }

    public String fen;
    public Integer skillLevel;
    public Integer depth;
}


