package com.enrique_gabriel.empresa_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

    @GetMapping("/informacion")
    public String info() {
        return "Hola mundo";
    }
}