package com.chessdemon.Chess.Demon.Controller;

import com.chessdemon.Chess.Demon.Exception.IllegalMoveException;
import com.github.bhlangonijr.chesslib.move.MoveConversionException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ResponseEntityExceptionHandler extends org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { IllegalMoveException.class, MoveConversionException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
