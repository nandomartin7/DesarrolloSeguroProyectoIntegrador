package com.example.MiniCore.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.core.VaultTransitOperations;
import org.springframework.vault.core.VaultTransitTemplate;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Configuration
public class VaultConfig {

    @Bean
    public VaultEndpoint vaultEndpoint() {
        return VaultEndpoint.from(URI.create("http://host.docker.internal:8200"));
    }

    @Bean
    public ClientAuthentication clientAuthentication() {
        return new TokenAuthentication("myroot");
    }

    @Bean
    public VaultTemplate vaultTemplate(VaultEndpoint endpoint,
                                       ClientAuthentication auth) {
        return new VaultTemplate(endpoint, auth);
    }

    @Bean
    public VaultTransitOperations vaultTransit(VaultTemplate vaultTemplate) {
        // “transit” es el mount path en tu Vault UI
        return new VaultTransitTemplate(vaultTemplate, "Transit-KMS");
    }
}

