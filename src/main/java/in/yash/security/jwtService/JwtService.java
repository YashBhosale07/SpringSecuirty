package in.yash.security.jwtService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	@Value("${jwt-security-key}")
	private String secretKey; 
	
	 private SecretKey getSigningKey() {
	        return Keys.hmacShaKeyFor(secretKey.getBytes());
	    }
	 
	 public String generateToken(String username) {
		 Map<String, Object> claims = new HashMap<>();
		 return createToken(claims, username);
	 }
	 
	private String createToken(Map<String,Object>claims,String username) {
		return Jwts.builder()
				.addClaims(claims)
				.setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*10))
				.signWith(getSigningKey())
				.compact();
	}

	public String extractUsername(String jwtToken) {
		return extractClaim(jwtToken, Claims::getSubject);
	}
	
	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
	}
	private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
	
	private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
	
	private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
	 
	 public boolean isTokenValid(String token, UserDetails userDetails) {
	        final String username = extractUsername(token);
	        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	    }
	
	
	

}
