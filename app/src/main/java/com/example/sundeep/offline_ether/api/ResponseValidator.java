package com.example.sundeep.offline_ether.api;

public interface ResponseValidator<T> {

    public boolean validate(T object);

}
