package cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.model.services.Interfaz;

import cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.model.DTO.PlayerDTO;

import java.util.List;

public interface PlayerServices {

    List<PlayerDTO> getAllPlayers();

    PlayerDTO save (PlayerDTO playerDTO);

    PlayerDTO getPlayerById(Long id);

    PlayerDTO createPlayer(PlayerDTO playerDTO);

    PlayerDTO updatePlayer (long playerId, PlayerDTO playerDTO);

    void deletePlayer(Long id);
    double getSuccessRateAverage ();
    PlayerDTO getWinner ();
    PlayerDTO getLoser ();

}




