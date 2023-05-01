package com.chessdemon.Chess.Demon.Service;

import com.chessdemon.Chess.Demon.Model.StockfishRequest;
import com.chessdemon.Chess.Demon.Model.StockfishResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StockfishService {

    public StockfishResponse getNextMove(StockfishRequest request) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String url = "https://pchess.net/api/engine/stockfish";
        String requestBody = String.format("{\"movetext\": \"%s\",\"skillLevel\": \"%s\",\"depth\": \"%s\"}", request.LAN, request.skillLevel, request.depth);
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        ObjectMapper mapper = new ObjectMapper();
        StockfishResponse stockfishResponse = mapper.readValue(response.getBody(), StockfishResponse.class);
        System.out.println(response.getBody());
        return stockfishResponse;
    }
}
