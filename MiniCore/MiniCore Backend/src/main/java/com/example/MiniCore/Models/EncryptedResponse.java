package com.example.MiniCore.Models;

public class EncryptedResponse {
    private String ciphertext;
    public EncryptedResponse() {}
    public EncryptedResponse(String ciphertext) { this.ciphertext = ciphertext; }
    public String getCiphertext() { return ciphertext; }
    public void setCiphertext(String ciphertext) { this.ciphertext = ciphertext; }
}