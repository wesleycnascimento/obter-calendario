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

        response.put("day", today.getDayOfMonth());
        response.put("month", today.getMonthValue());
        response.put("year", today.getYear());

        return response;
    }
}