package com.chessdemon.Chess.Demon.Model;

public class StockfishRequest {

    public StockfishRequest(String lan, Integer skillLevel, Integer depth){
        this.LAN = lan;
        this.skillLevel = skillLevel;
        this.depth = depth;
    }

    public String LAN;
    public Integer skillLevel;
    public Integer depth;
}


