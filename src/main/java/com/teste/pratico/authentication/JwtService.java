package com.teste.pratico.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  @Value("${application.security.jwt.secret-key}")
  private String secretKey;

  @Value("${application.security.jwt.expiration}")
  private long jwtExpiration;

  @Value("${application.security.jwt.refresh-token.expiration}")
  private long refreshExpiration;

  public String extrairLogin(String token) {
    return extrairClaims(token, Claims::getSubject);
  }

  public <T> T extrairClaims(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extrairTodasClaims(token);
    return claimsResolver.apply(claims);
  }

  public String gerarToken(UserDetails userDetails) {
    return gerarToken(new HashMap<>(), userDetails);
  }

  public String gerarToken(Map<String, Object> extraClaims, UserDetails userDetails) {
    return buildToken(extraClaims, userDetails, jwtExpiration);
  }

  public String gerarRefreshToken(UserDetails userDetails) {
    return buildToken(new HashMap<>(), userDetails, refreshExpiration);
  }

  public boolean isValidToken(String token, UserDetails userDetails) {
    final String login = extrairLogin(token);
    return (login.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {

    return Jwts
        .builder()
        .setClaims(extraClaims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extrairClaims(token, Claims::getExpiration);
  }

  private Claims extrairTodasClaims(String token) {
    return Jwts
        .parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
