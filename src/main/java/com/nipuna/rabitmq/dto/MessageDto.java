package com.nipuna.rabitmq.dto;

import lombok.Data;

@Data
public class MessageDto {

    private int priority;
    private String to;
    private String message;
}
