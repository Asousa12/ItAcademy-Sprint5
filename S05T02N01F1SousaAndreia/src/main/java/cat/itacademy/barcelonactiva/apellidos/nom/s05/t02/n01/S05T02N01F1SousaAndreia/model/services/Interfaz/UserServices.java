package cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.model.services.Interfaz;

import cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.model.DTO.RegisterDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserServices {

    void saveUser(RegisterDTO registrationDto);

    UserDetailsService userDetailsService();

}

