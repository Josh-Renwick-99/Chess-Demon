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
        String url = String.format("https://www.chessdb.cn/cdb.php?action=querybest&board=%s", request.fen);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String responseBody = response.getBody();
        String nextMove = responseBody.split(":")[1].trim();
        StockfishResponse stockfishResponse = new StockfishResponse(nextMove);
        System.out.println(stockfishResponse);
        return stockfishResponse;
    }
}
