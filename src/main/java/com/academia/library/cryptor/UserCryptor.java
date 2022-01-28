package com.academia.library.cryptor;

public interface UserCryptor {

    String decode(String encodeString);

    String encode(String string);
}
