package com.chessdemon.Chess.Demon.Service;

import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class TestService {
    public void printMessage(String[] args) {
        System.out.println("Started with arguments: ");
        Arrays.stream(args).forEach(System.out::println);
    }
}
