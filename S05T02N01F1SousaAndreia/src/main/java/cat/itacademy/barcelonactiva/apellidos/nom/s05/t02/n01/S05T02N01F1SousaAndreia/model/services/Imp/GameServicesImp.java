package cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.model.services.Imp;

import cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.exception.GameNotFoundException;
import cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.exception.InvalidInputException;
import cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.exception.PlayerNotFoundException;
import cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.model.DTO.GameDTO;
import cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.model.domain.Game;
import cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.model.domain.Player;
import cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.model.repository.GameRepository;
import cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.model.repository.PlayerRepository;
import cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.model.services.Interfaz.GameServices;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class GameServicesImp implements GameServices {

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;

    public GameServicesImp(GameRepository gameRepository, PlayerRepository playerRepository) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
    }

    private static GameDTO convertToDTO(Game game) {
        return GameDTO.builder()
                .gameID(game.getGameId())
                .playerID(game.getPlayer().getPlayerId())
                .diceOne(game.getDice1())
                .diceTwo(game.getDice2())
                .won(game.isWon())
                .build();
    }

    @Override
    public List<GameDTO> getAllGames() {
        List<Game> games = gameRepository.findAll();
        List<GameDTO> gameDTOs = new ArrayList<>();
        for (Game game : games) {
            gameDTOs.add(convertToDTO(game));
        }

        return gameDTOs;
    }

    @Override
    public List<GameDTO> listGamesByPlayer(int id) {
        Optional<Player> player = playerRepository.findById((long) id);
        List<Game> games = player.get().getGamesList();
        if (player.isPresent() && !games.isEmpty()) {
            return games.stream().map(GameServicesImp::convertToDTO).collect(Collectors.toList());

        } else if (games.isEmpty()) {

            throw new GameNotFoundException("No se han encontrado partidas en el jugador");
        } else {

            throw new EntityNotFoundException("No se ha encontrado el jugador");
        }
    }

    @Override
    public GameDTO getGameById(Long id) {
        Optional<Game> gameOptional = gameRepository.findById(id);

        if (gameOptional.isPresent()) {
            return convertToDTO(gameOptional.get());
        } else {
            throw new GameNotFoundException("Game with ID: " + id + " not found");
        }
    }

    @Override
    @Transactional
    public GameDTO createGame(GameDTO gameDTO) throws InvalidInputException {
        if (gameDTO.getPlayerID() == 0 || gameDTO.getDiceOne() < 1 || gameDTO.getDiceTwo() < 1) {
            throw new InvalidInputException("Datos de juego inválidos");
        }

        Optional<Player> playerOptional = playerRepository.findById(gameDTO.getPlayerID());
        Player player = playerOptional.orElseThrow(() -> new PlayerNotFoundException("Jugador no encontrado"));

        Game game = new Game(player);
        game.setDice1(gameDTO.getDiceOne());
        game.setDice2(gameDTO.getDiceTwo());
        game.setWon(gameDTO.isWon());

        Game savedGame = gameRepository.save(game);

        return convertToDTO(savedGame);
    }

    @Override
    public GameDTO updateGame(long id, GameDTO gameDTO) {
        Optional<Game> gameOptional = gameRepository.findById(id);
        if (gameOptional.isPresent()) {
            Game gameToUpdate = gameOptional.get();

            gameToUpdate.setDice1(gameDTO.getDiceOne());
            gameToUpdate.setDice2(gameDTO.getDiceTwo());
            gameToUpdate.setWon(gameDTO.isWon());

            Game updatedGame = gameRepository.save(gameToUpdate);

            return convertToDTO(updatedGame);
        } else {
            throw new GameNotFoundException("Game with ID: " + id + " not found");
        }
    }
    @Override
    public boolean deleteGame(Long id) {
        gameRepository.deleteById(id);
        return false;
    }

    @Override
    public GameDTO playerPlayGame(int id) {
        GameDTO gameDTO = new GameDTO();

        Random random = new Random();
        int diceOne = random.nextInt(6) + 1;
        int diceTwo = random.nextInt(6) + 1;

        gameDTO.setDiceOne(diceOne);
        gameDTO.setDiceTwo(diceTwo);

        boolean won = diceOne + diceTwo == 7;

        gameDTO.setWon(won);
        return gameDTO;
    }
}
