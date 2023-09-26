package com.heimtn.telegrambot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class TimeParseException extends RuntimeException{
    public TimeParseException(String msg){
        super(msg);
    }
}
