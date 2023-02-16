package com.chessdemon.Chess.Demon.Model;

import jakarta.annotation.Nullable;
import lombok.Data;

import java.util.Date;

@Data
public class GameView {
    Integer gameId;
    String position;
    @Nullable
    Date date;
    @Nullable
    String san;
    public GameView(Integer id, String position, String san, Date date){
        this.position = position;
        this.date = date;
        this.gameId = id;
        this.san = san;
    }
}
