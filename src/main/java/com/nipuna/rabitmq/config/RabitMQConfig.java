package com.nipuna.rabitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabitMQConfig {

    @Value("${rabbitmq.queue.nipuna.name}")
    private String queueNipunaName;

    @Value("${rabbitmq.exchange.nipuna.name}")
    private String exhangeNipunaName;

    @Value("${rabbitmq.routingkey.nipuna.name}")
    private String routingKeyNipuna;

    @Value("${rabbitmq.queue.kasun.name}")
    private String queueKasunName;

    @Value("${rabbitmq.exchange.kasun.name}")
    private String exhangeKasunName;

    @Value("${rabbitmq.routingkey.kasun.name}")
    private String routingKeyKasun;

    @Value("${rabbitmq.queue.json.name}")
    private String queueJsonName;

    @Value("${rabbitmq.routingkey.json.name}")
    private String routingKeyJson;

    @Bean
    public Queue queuenipuna(){
        return QueueBuilder
                .durable(queueNipunaName)
                .withArgument("x-max-length",5)
                .withArgument("x-max-priority",5)
                .withArgument("x-message-ttl", 60000)
                .withArgument("x-dead-letter-exchange",exhangeKasunName)
                .withArgument("x-dead-letter-routing-key",routingKeyKasun)
                .build();
    }

    @Bean
    public TopicExchange exchangeNipuna(){
        return new TopicExchange(exhangeNipunaName);
    }

    @Bean
    public Binding binding(){
        return BindingBuilder
                .bind(queuenipuna())
                .to(exchangeNipuna())
                .with(routingKeyNipuna);
    }

    @Bean
    public Queue queueKasun(){
        return QueueBuilder
                .durable(queueKasunName)
                .withArgument("x-max-length",5)
                .withArgument("x-max-priority",5)
                .build();
    }

    @Bean
    public TopicExchange exchangeKasun(){
        return new TopicExchange(exhangeKasunName);
    }

    @Bean
    public Binding binding2(){
        return BindingBuilder
                .bind(queueKasun())
                .to(exchangeKasun())
                .with(routingKeyKasun);
    }

    @Bean
    public Queue jsonQueue(){
        return new Queue("json_queue");
    }

    @Bean
    public Binding jsonBinding(){
        return BindingBuilder
                .bind(jsonQueue())
                .to(exchangeKasun())
                .with(routingKeyJson);
    }

    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate=new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

//    @Bean
//    public Queue queuenipuna(){
//        return QueueBuilder
//                .durable(queueNipunaName)
//                .withArgument("x-max-length", 10)
//                .withArgument("x-max-priority", 5)
//                .withArgument("x-message-ttl", 300000) // 5 minutes in milliseconds after this time queue element will automatically remove
//                .withArgument("x-queue-mode", "lazy") // specify queue type as lazy
//                .withArgument("x-dead-letter-exchange", "my.dead.letter.exchange")   oldest message automatically remove and add to this exchange queue , and also deleting messages will also go there
//                .withArgument("x-dead-letter-routing-key", "my.dead.letter.routing.key")
//                .withArgument("x-queue-master-locator", "min-masters")
//                .build();
//    }

    //Apart from above bean to use RabitMQ need to configure below
    //ConnectionFactory
    //RabbitTemplate
    //RabbitAdmin
    //But in spring boot Above three are autoconfigured by spring boot so doesn't need to configure them manually.

    //link of the wso2 for RabitMQ
    //https://ei.docs.wso2.com/en/7.0.0/micro-integrator/references/synapse-properties/transport-parameters/rabbitmq-transport-parameters/
}
