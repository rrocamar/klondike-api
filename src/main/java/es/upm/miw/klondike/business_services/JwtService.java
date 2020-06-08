package es.upm.miw.klondike.business_services;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import es.upm.miw.klondike.exceptions.JwtException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {

    private static final String BEARER = "Bearer ";
    private static final String USER = "user";
    private static final String NAME = "name";
    private static final String ROLES = "roles";
    private static final String ISSUER = "es-upm-miw-tfm-klondike";
    private static final int EXPIRES_IN_MILLISECOND = 3600000;
    private static final String SECRET = "secret-password-test";


    public String createToken(String user, String name, String[] roles) {
        return JWT.create()
                .withIssuer(ISSUER)
                .withIssuedAt(new Date())
                .withNotBefore(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRES_IN_MILLISECOND))
                .withClaim(USER, user)
                .withClaim(NAME, name)
                .withArrayClaim(ROLES, roles)
                .sign(Algorithm.HMAC256(SECRET));
    }

    public boolean isBearer(String authorization) {
        return authorization != null && authorization.startsWith(BEARER) && authorization.split("\\.").length == 3;
    }

    public String user(String authorization) {
        return this.verify(authorization).getClaim(USER).asString();
    }

    private DecodedJWT verify(String authorization) {
        if (!this.isBearer(authorization)) {
            throw new JwtException("It is not Bearer");
        }
        try {
            return JWT.require(Algorithm.HMAC256(SECRET))
                    .withIssuer(ISSUER).build()
                    .verify(authorization.substring(BEARER.length()));
        } catch (Exception exception) {
            throw new JwtException("JWT is wrong. " + exception.getMessage());
        }

    }

    public List<String> roles(String authorization) {
        return Arrays.asList(this.verify(authorization).getClaim(ROLES).asArray(String.class));
    }

}