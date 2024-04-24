package cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.model.services.Interfaz;

import cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.model.domain.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
@NoArgsConstructor
@AllArgsConstructor
public interface JwtServices {
    String generateToken(UserDetails userDetails);
    String getUserEmail(String token);
    boolean validateToken(String token, UserDetails userDetails);
    String getUsername(String token);

}
