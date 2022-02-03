package com.academia.library.cryptor.impl;

import com.academia.library.cryptor.Cryptor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class CryptorImpl implements Cryptor {

    @Override
    public String decode(String encodeValue) {
        return new String(Base64.decodeBase64(encodeValue.getBytes(StandardCharsets.UTF_8)));
    }

    @Override
    public String encode(String decodeValue) {
        return new String(Base64.encodeBase64(decodeValue.getBytes(StandardCharsets.UTF_8)));
    }
}