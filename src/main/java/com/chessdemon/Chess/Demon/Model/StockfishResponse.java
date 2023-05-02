package com.chessdemon.Chess.Demon.Model;

import lombok.Data;

@Data
public class StockfishResponse {

    public StockfishResponse(String move){
        this.move = move;
    }
    public String move;
}
