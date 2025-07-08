package com.example.Backend.Core.DTO;

public class EncryptedResponse {
    private String ciphertext;
    public EncryptedResponse() {}
    public String getCiphertext() { return ciphertext; }
    public void setCiphertext(String ciphertext) { this.ciphertext = ciphertext; }
}