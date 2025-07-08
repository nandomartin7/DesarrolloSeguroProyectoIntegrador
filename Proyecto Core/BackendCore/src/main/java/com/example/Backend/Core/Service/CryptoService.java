package com.example.Backend.Core.Service;

import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultTransitOperations;
import org.springframework.vault.support.Plaintext;
import org.springframework.vault.support.Ciphertext;

import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class CryptoService {

    private static final Logger log = LoggerFactory.getLogger(CryptoService.class);
    private final VaultTransitOperations transit;

    public CryptoService(VaultTransitOperations transit) {
        this.transit = transit;
    }

    public String encrypt(String plaintext) {
        log.debug("Core encrypt input JSON: {}", plaintext);
        // envolvemos los bytes en un Plaintext
        Plaintext pt = Plaintext.of(plaintext, StandardCharsets.UTF_8);
        // → esto devuelve un objeto Ciphertext
        Ciphertext ct = transit.encrypt("filtrar-key", pt);
        // y aquí sí existe getCiphertext()
        log.debug("Core encrypt output cipher: {}", ct.getCiphertext());
        return ct.getCiphertext();
    }

    public String decrypt(String ciphertext) {
        log.debug("Core decrypt input cipher: {}", ciphertext);
        // envolvemos el String cifrado en un objeto Ciphertext
        Ciphertext ct = Ciphertext.of(ciphertext);
        // → esto devuelve un objeto Plaintext
        Plaintext pt  = transit.decrypt("filtrar-key", ct);
        // y aquí sí existe getPlaintext() (retorna byte[]) o asString()
        log.debug("Core decrypt output JSON: {}", pt.asString(StandardCharsets.UTF_8));
        return pt.asString(StandardCharsets.UTF_8);
    }
}