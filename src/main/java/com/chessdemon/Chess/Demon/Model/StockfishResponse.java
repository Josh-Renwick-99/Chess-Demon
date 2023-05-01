package com.chessdemon.Chess.Demon.Model;

import lombok.Data;

@Data
public class StockfishResponse {
    public String move;
    public String fen;
    public boolean isCheck;
    public boolean isMate;
    public String LAN;
}
