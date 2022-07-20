package me.synology.gooseauthapiserver.configuration.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

  private Key secretKey;

  private static final long TOKEN_VALID_MILLISECOND = 1000L * 60 * 60;

  private final UserDetailsService userDetailsService;

  @PostConstruct
  protected void init() {
    secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
  }

  public String createToken(String userPrimaryKey, List<String> roles) {
    Claims claims = Jwts.claims().setSubject(userPrimaryKey);
    claims.put("roles", roles);
    Date now = new Date();

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(new Date(now.getTime() + TOKEN_VALID_MILLISECOND))
        .signWith(secretKey)
        .compact();
  }

  public String getUserPrimaryKey(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(secretKey)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public Authentication getAuthentication(String token) {
    UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPrimaryKey(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public String resolveToken(HttpServletRequest request) {
    return request.getHeader("X-AUTH-TOKEN");
  }

  public boolean validateToken(String jwtToken) {
    try {
      Jws<Claims> claimsJws = Jwts.parserBuilder()
          .setSigningKey(secretKey)
          .build()
          .parseClaimsJws(jwtToken);

      return !claimsJws.getBody().getExpiration().before(new Date());
    } catch (Exception e) {
      log.error("validateToken Exception ", e);
      return false;
    }
  }
}
