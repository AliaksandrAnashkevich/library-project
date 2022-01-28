package com.academia.library.cryptor.impl;

import com.academia.library.cryptor.UserCryptor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class UserCryptorImpl implements UserCryptor {

    @Override
    public String decode(String encodeString) {
        return new String(Base64.decodeBase64(encodeString.getBytes(StandardCharsets.UTF_8)));
    }

    @Override
    public String encode(String string) {
        return new String(Base64.encodeBase64(string.getBytes(StandardCharsets.UTF_8)));
    }
}
