package com.nipuna.rabitmq.consumer.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Data
public class ConsumerService {

    public List<String>recievedMessages=new ArrayList<>();
    //in production level using in-memmory list is not recommended
    //recomended way is to use database to store the listner messages

//    @RabbitListener(queues = "${rabbitmq.queue.nipuna.name}")
//    public void seeRecievedMessage(String message){
//        log.info("recieved message: {}",message);
//        recievedMessages.add(message);
//
//    }


    public List<String> recievedMessages() {
        return recievedMessages;
    }
}
