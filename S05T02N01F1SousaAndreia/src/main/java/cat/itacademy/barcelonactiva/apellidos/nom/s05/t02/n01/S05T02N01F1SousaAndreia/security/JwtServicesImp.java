package cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.security;

import cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.model.services.Interfaz.JwtServices;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServicesImp implements JwtServices {

    
    private static final String SECRET_KEY= "060166c2fd0c03992fb818aa5292cb0943cb0c7a7bdbd622c47388ba1efc4bc9";
    private int expiration;

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        Date currentDate = new Date(System.currentTimeMillis()); // Obtenemos la fecha actual
        Date expirationDate = new Date(currentDate.getTime() + expiration * 1000); // Calculamos la fecha de expiración

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(currentDate) // Usamos la fecha actual como fecha de emisión
                .setExpiration(expirationDate) // Usamos la fecha de expiración calculada
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Especificamos el algoritmo de firma
                .compact();
    }
    @Override
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    @Override
    public String getUserEmail(String token) {
        return null;
    }

    @Override
    public String getUsername(String token) {

        return getClaim(token, Claims::getSubject);
    }
    private <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims getAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }


}