package com.example.Backend.Core.DTO;

public class EncryptedRequest {
    private String ciphertext;
    public EncryptedRequest() {}
    public EncryptedRequest(String ciphertext) { this.ciphertext = ciphertext; }
    public String getCiphertext() { return ciphertext; }
    public void setCiphertext(String ciphertext) { this.ciphertext = ciphertext; }
}