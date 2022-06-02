package edu.progra3.polarcity.security;

import edu.progra3.polarcity.exceptions.BadRequestException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private Integer jwtExpirationInMs;

    public String generateToken(Authentication authentication){
        String email = authentication.getName();
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getEmailJwt(String token){
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch (SignatureException ex) {
            throw new BadRequestException("Firma JWT no valida");
        }
        catch (MalformedJwtException ex) {
            throw new BadRequestException("Token JWT no valida");
        }
        catch (ExpiredJwtException ex) {
            throw new BadRequestException("Token JWT caducado");
        }
        catch (UnsupportedJwtException ex) {
            throw new BadRequestException("Token JWT no compatible");
        }
        catch (IllegalArgumentException ex) {
            throw new BadRequestException("La cadena claims JWT esta vacia");
        }
    }
}
