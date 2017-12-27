package com.example.sundeep.offline_ether.api.exception;

public class EtherApiException extends Exception {
    public EtherApiException(String s) {
        super(s);
    }

    public EtherApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
