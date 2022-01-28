package com.academia.library.cryptor;

public interface UserCryptor {

    String decode(String encodeValue);

    String encode(String value);
}
