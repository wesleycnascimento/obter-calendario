package com.example.obtercalendario;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ObterCalendarioController {

    @GetMapping("/obterCalendario")
    public Map<String, Integer> obterCalendario() {
        LocalDate today = LocalDate.now();

        Map<String, Integer> response = new HashMap<>();

        //Setando os valores de dia, mes e ano V1
        response.put("dia", today.getDayOfMonth());
        response.put("mes", today.getMonthValue());
        response.put("ano", today.getYear());

        return response;
    }
}