package com.example.Backend.Core.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity                      //  ⚠ ya no añadas EnableGlobalMethodSecurity aquí
@EnableMethodSecurity(prePostEnabled = true)   //  Spring 6 / Boot 3
public class SecurityConfig {

    @Bean
    SecurityFilterChain api(HttpSecurity http,
                                   JwtAuthenticationConverter authConverter) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // 1) Deja libres únicamente los ENDPOINTS DE REGISTRO
                        .requestMatchers("/empleado", "/cliente").permitAll()
                        .anyRequest().authenticated()
                )
                // 2) Activa resource-server JWT
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(authConverter))
                );
        return http.build();
    }

    /** Ajusta de "ROLE_" + realm_access.roles  */
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Collection<GrantedAuthority> authorities = new ArrayList<>();

            // EXTRAEMOS los realm‐roles:
            Map<String,Object> realm = jwt.getClaim("realm_access");
            if (realm != null && realm.containsKey("roles")) {
                @SuppressWarnings("unchecked")
                List<String> roles = (List<String>) realm.get("roles");
                roles.forEach(r -> authorities.add(
                        new SimpleGrantedAuthority("ROLE_" + r)
                ));
            }

            return authorities;
        });

        return converter;
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration cfg = new CorsConfiguration();
        cfg.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:5174"));      // front dev-server
        cfg.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        cfg.setAllowedHeaders(List.of("Authorization","Content-Type"));
        cfg.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource src = new UrlBasedCorsConfigurationSource();
        src.registerCorsConfiguration("/**",cfg);
        return src;
    }

    @Bean
    PasswordEncoder passwordEncoder() {     // Sólo lo usas en registro
        return new BCryptPasswordEncoder();
    }
}



