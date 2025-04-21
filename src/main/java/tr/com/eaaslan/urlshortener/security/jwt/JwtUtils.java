package tr.com.eaaslan.urlshortener.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtUtils {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

//    @Value("${security.jwt.expiration-duration}")
//    private Long jwtExpiration;
    //todo stringden longa ceviri hatasi veriyor


    public String getJwtFromHeader(HttpServletRequest httpServletRequest){

        String bearerToken=httpServletRequest.getHeader("Authorization");

        if(bearerToken!=null && bearerToken.startsWith("Bearer ") ){
            return bearerToken.substring(7);
        }
        return null;

    }

    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims=extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String getUsername(String token){
        return extractClaim(token,Claims::getSubject);
    }

    public boolean validateToken(String authToken, UserDetails userDetails){
       final String username=getUsername(authToken);
       return (username.equals(userDetails.getUsername())) && !isTokenExpired(authToken);
    }

    private Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }
    private boolean isTokenExpired(String authToken) {
        return extractExpiration(authToken).before(new Date());
    }

    public String generateToken(UserDetails userDetails){
        String username=userDetails.getUsername();
        String roles=userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        return Jwts.builder()
                .setSubject(username)
                .claim("roles",roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+1000*60*3))
                .signWith(getSignInKey())
                .compact();
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey(){
        byte[] keyBytes= Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }



}
