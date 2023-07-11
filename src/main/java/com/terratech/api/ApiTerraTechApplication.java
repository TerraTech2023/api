package com.terratech.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@SpringBootApplication
public class ApiTerraTechApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiTerraTechApplication.class, args);
    }

    @GetMapping
    public Map<String, String> hello() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "TerraTech");
        map.put("version", "1.0-alpha");
        map.put("description", "API TerraTech");
        return map;
    }

}
