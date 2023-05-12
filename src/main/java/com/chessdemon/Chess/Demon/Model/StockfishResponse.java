package com.chessdemon.Chess.Demon.Model;

import lombok.Data;

@Data
public class StockfishResponse {

    public StockfishResponse(String move){
        this.move = move;
    }

    public StockfishResponse(Boolean bool) {this.ilegal = bool; }
    public String move;
    public boolean ilegal;
}
