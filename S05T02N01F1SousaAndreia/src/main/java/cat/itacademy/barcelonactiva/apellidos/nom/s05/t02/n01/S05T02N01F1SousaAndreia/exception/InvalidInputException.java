package cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.exception;

public class InvalidInputException extends Throwable {
    public InvalidInputException(String datosDeJuegoInválidos) {
        super(datosDeJuegoInválidos);
    }
}
