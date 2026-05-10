package com.lyrachtech.ebankingbackend.exceptions;

public class UnAuthorizedAccountOperation extends Exception {
    public UnAuthorizedAccountOperation(String message) {
        super(message);
    }
}
