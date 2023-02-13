package com.chessdemon.Chess.Demon;

public class IllegalMoveException extends IllegalArgumentException{
    public IllegalMoveException(String move){
        super(String.format("Move '%s' is not a valid move", move));
    }
}
