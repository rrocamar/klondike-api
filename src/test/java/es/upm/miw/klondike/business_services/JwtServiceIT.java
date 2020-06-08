package es.upm.miw.klondike.business_services;

import es.upm.miw.TestConfig;
import es.upm.miw.klondike.exceptions.JwtException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@TestConfig
class JwtServiceIT {

    @Autowired
    private JwtService jwtService;

    @Test
    void testJwtExceptionNotBearer() {
        assertThrows(JwtException.class, () -> this.jwtService.user("Not Bearer"));
    }

    @Test
    void testJwtExceptionTokenError() {
        assertThrows(JwtException.class, () -> this.jwtService.user("Bearer error.error.error"));
    }

}