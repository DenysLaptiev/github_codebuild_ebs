package com.springboot.https.ms_1.controller.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/ms-1")
public class Ms1RestController {

    @GetMapping("/test")
    public ResponseEntity<?> testGet(){
        log.info("Hello World from ms-1");

        String result = "Hello World from ms-1";
        return ResponseEntity.ok(result);
    }

    @PostMapping("/conn")
    public ResponseEntity<?> testPost(){
        log.info("Hello World from ms-1 testPost");

        String result = "Message from ms-1";
        return ResponseEntity.ok(result);
    }
}
