package com.nipuna.rabitmq.producer.controller;

import com.nipuna.rabitmq.dto.JsonMessageDto;
import com.nipuna.rabitmq.dto.MessageDto;
import com.nipuna.rabitmq.producer.service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/producer")
@RequiredArgsConstructor
public class ProducerController {

    private final ProducerService service;

    @GetMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody MessageDto messageDto){
        service.sendMessage(messageDto);
        return ResponseEntity.ok("Successfully send the message");
    }

    @GetMapping("/send-json")
    public ResponseEntity<Object> sendJsonMessage(@RequestBody JsonMessageDto jsonMessageDto){
        return ResponseEntity.ok(service.sendJsonMessage(jsonMessageDto));
    }
}
