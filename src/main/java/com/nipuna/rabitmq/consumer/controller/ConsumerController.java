package com.nipuna.rabitmq.consumer.controller;

import com.nipuna.rabitmq.consumer.service.ConsumerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/consumer")
@RequiredArgsConstructor
public class ConsumerController {

    private final ConsumerService service;

    @GetMapping("/see")
    public ResponseEntity<Object> recievedMessage(){
        List<String> message=service.recievedMessages();
        return ResponseEntity.ok(message);

    }
}
