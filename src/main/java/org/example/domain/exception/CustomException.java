package org.example.domain.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomException extends RuntimeException {

    private String userMessage;

    public CustomException(String message, String userMessage) {
        super(message);
        this.userMessage = userMessage;
    }

    public CustomException(String message, Throwable cause, String userMessage) {
        super(message, cause);
        this.userMessage = userMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }

}
