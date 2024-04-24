package cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.model.services.Interfaz;

import cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.exception.InvalidInputException;
import cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.model.DTO.GameDTO;

import java.util.List;

public interface GameServices {

    List<GameDTO> getAllGames();
    List<GameDTO> listGamesByPlayer(int id);

    GameDTO getGameById(Long id);

    GameDTO createGame(GameDTO gameDTO) throws InvalidInputException;

    GameDTO updateGame(long id, GameDTO gameDTO);

    boolean deleteGame(Long id);
    GameDTO playerPlayGame(int id);



}
