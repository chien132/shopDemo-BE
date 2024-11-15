package chien.demo.shopdemo.security.jwt;

import chien.demo.shopdemo.security.services.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/** The type Jwt utils. */
@Component
public class JwtUtils {
  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  @Value("${jwt.jwtSecret}")
  private String jwtSecret;

  @Value("${jwt.jwtExpirationMs}")
  private int jwtExpirationMs;

  /**
   * Generate jwt token string.
   *
   * @param authentication the authentication
   * @return the string
   */
  public String generateJwtToken(Authentication authentication, Map<String, Object> claims) {

    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

    SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    return Jwts.builder()
        .claims(claims)
        .subject((userPrincipal.getUsername()))
        .issuedAt(new Date())
        .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
        .signWith(secret)
        .compact();
  }

  public String getUserNameFromJwtToken(String token) {
    SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    return Jwts.parser().verifyWith(secret).build().parseSignedClaims(token).getPayload().getSubject();
  }

  /**
   * Validate jwt token boolean.
   *
   * @param authToken the auth token
   * @return the boolean
   */
  public boolean validateJwtToken(String authToken) {
    try {
      SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
      Jwts.parser().verifyWith(secret).build().parseSignedClaims(authToken);
      return true;
    } catch (SignatureException e) {
      logger.error("Invalid JWT signature: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage());
    }

    return false;
  }
}
