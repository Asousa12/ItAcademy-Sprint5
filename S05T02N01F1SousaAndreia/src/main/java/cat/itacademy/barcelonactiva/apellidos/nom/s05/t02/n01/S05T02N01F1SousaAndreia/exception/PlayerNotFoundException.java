package cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PlayerNotFoundException extends ResponseStatusException {

    public PlayerNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Player not found");
    }

    public PlayerNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}