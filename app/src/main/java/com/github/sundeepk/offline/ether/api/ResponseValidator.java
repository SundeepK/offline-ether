package com.github.sundeepk.offline.ether.api;

public interface ResponseValidator<T> {

    public boolean validate(T object);

}
