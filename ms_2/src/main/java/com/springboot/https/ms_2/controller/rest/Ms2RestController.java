package com.springboot.https.ms_2.controller.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@Slf4j
@RequestMapping("/ms-2")
public class Ms2RestController {


    @Value("${ms1.server.baseurl}")
    private String MS1_BASE_URL;

    private final WebClient webClient;

    @Autowired
    public Ms2RestController(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping("/test")
    public ResponseEntity<?> testGet(){
        log.info("Hello World from ms-2");

        String result = "Hello World from ms-2";
        return ResponseEntity.ok(result);
    }


    @PostMapping("/connect")
    public ResponseEntity<?> testConnect(){
        log.info("testConnect");

        String url = MS1_BASE_URL + "/ms-1/conn";

        String result = webClient
                .post()
                .uri(url)
                //.contentType(MediaType.MULTIPART_FORM_DATA)
                //.body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(String.class).block();

        log.info("result=" + result);

        return ResponseEntity.ok(result);
    }
}
