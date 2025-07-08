package com.example.Backend.Core.Client;

import com.example.Backend.Core.DTO.EncryptedRequest;
import com.example.Backend.Core.DTO.EncryptedResponse;
import com.example.Backend.Core.DTO.FilterParams;
import com.example.Backend.Core.DTO.GastoDto;
import com.example.Backend.Core.Service.CryptoService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class GastoSecureClient {

    private static final Logger log = LoggerFactory.getLogger(CryptoService.class);
    private final CryptoService crypto;
    private final RestTemplate rest;
    private final ObjectMapper mapper = new ObjectMapper();
    private final String baseUrl;

    public GastoSecureClient(CryptoService crypto,
                             RestTemplate rest,
                             @Value("${minicore.url}") String baseUrl) {
        this.crypto = crypto;
        this.rest = rest;
        this.baseUrl = baseUrl;
    }

    @SuppressWarnings("unchecked")
    public Map<String,List<GastoDto>> filtrar(Date inicio, Date fin) throws Exception {
        // 1️⃣ Preparar y cifrar el cuerpo
        FilterParams params = new FilterParams(inicio, fin);
        String json      = mapper.writeValueAsString(params);
        String cipherReq = crypto.encrypt(json);
        EncryptedRequest req = new EncryptedRequest(cipherReq);

        // 1️⃣ Saca el Jwt del contexto
        JwtAuthenticationToken auth =
                (JwtAuthenticationToken) SecurityContextHolder
                        .getContext()
                        .getAuthentication();
        Jwt jwt = auth.getToken();
        String tokenValue = jwt.getTokenValue();    // <-- aquí sí tienes el Bearer real

        // 2️⃣ Construye headers con ese token
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(tokenValue);

        HttpEntity<EncryptedRequest> entity = new HttpEntity<>(req, headers);

        log.debug("→ Core preparing filtered JSON: {} → encrypting…", json);
        log.debug("→ Core calling MiniCore with cipher: {}", cipherReq);
        // 3️⃣ Llamada
        ResponseEntity<EncryptedResponse> resp = rest.exchange(
                baseUrl + "/gasto/secure/filtrar",
                HttpMethod.POST,
                entity,
                EncryptedResponse.class
        );

        log.debug("← Core received cipherResponse: {}", resp.getBody().getCiphertext());
        // 5️⃣ Descifrar y devolver
        String cipherResp = resp.getBody().getCiphertext();
        String plainJson  = crypto.decrypt(cipherResp);
        log.debug("← Core decrypted JSON: {}", plainJson);
        return mapper.readValue(
                plainJson,
                new TypeReference<Map<String,List<GastoDto>>>() {}
        );
    }
}