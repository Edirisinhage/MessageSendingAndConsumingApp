package com.nipuna.rabitmq.producer.service;

import com.nipuna.rabitmq.dto.JsonMessageDto;
import com.nipuna.rabitmq.dto.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProducerService {

    @Value("${rabbitmq.queue.nipuna.name}")
    private String queueName;

    @Value("${rabbitmq.exchange.nipuna.name}")
    private String exhangeName;

    @Value("${rabbitmq.routingkey.nipuna.name}")
    private String routingKey;

    @Value("${rabbitmq.exchange.kasun.name}")
    private String exhangeKasunName;

    @Value("${rabbitmq.routingkey.kasun.name}")
    private String routingKasunKey;

//    @Value("${rabbitmq.exchange.json.name}")
//    private String exhangeJsonName;

    @Value("${rabbitmq.routingkey.json.name}")
    private String routingJsonKey;

    private final RabbitTemplate rabbitTemplate;


    public ProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public String sendMessage(MessageDto messageDto) throws AmqpException{
        log.info("message send-> : {}",messageDto.getMessage());

        if(messageDto.getTo().equals("nipuna")){

            MessageProperties properties=new MessageProperties();
            properties.setPriority(messageDto.getPriority());
             rabbitTemplate.convertAndSend(exhangeName, routingKey, messageDto.getMessage(), new MessagePostProcessor() {

                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    message.getMessageProperties().setPriority(messageDto.getPriority());
                    return message;
                }
            });
             return "Successfully send the message to broker";
        }
        if(messageDto.getTo().equals("kasun")){
            rabbitTemplate.convertAndSend(exhangeKasunName, routingKasunKey, messageDto.getMessage(), new MessagePostProcessor() {

                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    message.getMessageProperties().setPriority(messageDto.getPriority());
                    return message;
                }
            });
            return "Successfully send the message to broker";
        }else{
            throw new RuntimeException("Please pass the valid type of broker");
        }


}

    public String sendJsonMessage(JsonMessageDto jsonMessageDto) throws AmqpException{

        log.info("message send-> : {}",jsonMessageDto);

        rabbitTemplate.convertAndSend(exhangeKasunName,routingJsonKey,jsonMessageDto);

        return "Successfully send the message to the broker";
    }
}
