package com.saurabh.ecommerce.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean("springSecurityFilterChain")
    public SecurityFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity)
    {
        serverHttpSecurity
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/eureka/**").permitAll()
                        .anyExchange().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwtSpec -> jwtSpec
                                .jwkSetUri("http://localhost:8181/realms/spring-boot-microservice/protocol/openid-connect/certs")
                        )
                );
        return (SecurityFilterChain) serverHttpSecurity.build();
    }

}
