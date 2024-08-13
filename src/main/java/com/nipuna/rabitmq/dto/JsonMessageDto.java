package com.nipuna.rabitmq.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class JsonMessageDto {

    private int messageCde;
    private String news;
    private Double cost;

}
