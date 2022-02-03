package com.academia.library.cryptor;

public interface Cryptor {

    String decode(String encodeValue);

    String encode(String value);
}