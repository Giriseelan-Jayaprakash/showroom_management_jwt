package com.showroommanagement_jwt.service;

import com.showroommanagement_jwt.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {
    public Map<String, String> generateTokens(User user) {
        Map<String, String> tokens = new HashMap<>();
        String accessToken = generateToken(user, false);
        String refreshToken = generateToken(user, true);
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);
        return tokens;
    }

    public String generateToken(User user, boolean isRefreshToken) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("emailId", user.getEmailId());
        claims.put("name", user.getUserName());
        long expirationTime;
        if (isRefreshToken) {
            expirationTime = 1000L * 60 * 60 * 24 * 30;
        } else {
            expirationTime = 1000 * 60 * 30;
        }
        return Jwts
                .builder()
                .claims()
                .add(claims)
                .subject(user.getId())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .and()
                .signWith(getKey())
                .compact();
    }

    private String secretkey = "";

    public JWTService() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey skey = keyGenerator.generateKey();
            secretkey = Base64.getEncoder().encodeToString(skey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretkey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserName(String token) {
        return extractClaim(token, claims -> claims.get("emailId", String.class));
    }

    private <T> T extractClaim(final String token, final Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {

        try {
            return Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();


        } catch (SignatureException e) {
            throw new RuntimeException("JWT signature verification failed");
        } catch (Exception e) {
            System.err.println("JWT validation error: " + e.getMessage());
            throw new RuntimeException("JWT validation failed");
        }
//        finally {
//            throw new BadRequestServiceAlertException("Test exception");
//        }
    }

    public boolean validateToken(final String token, final UserDetails userDetails) {
        final String userName = extractUserName(token);
        return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(final String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(final String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
/*
	public boolean isTokenValid(final String token,final UserDetails userDetails) {
		final String username = extractUserName(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(final String token) {
		return extractClaim(token, Claims::getExpiration).before(new Date());
	}
 */