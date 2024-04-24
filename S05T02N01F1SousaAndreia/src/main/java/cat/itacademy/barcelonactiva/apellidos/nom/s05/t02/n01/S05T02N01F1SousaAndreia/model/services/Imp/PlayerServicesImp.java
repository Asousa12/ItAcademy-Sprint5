package cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.model.services.Imp;

import cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.exception.PlayerNameAlreadyExistsException;
import cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.exception.PlayerNotFoundException;
import cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.model.DTO.PlayerDTO;
import cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.model.domain.Player;
import cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.model.repository.PlayerRepository;
import cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.model.services.Interfaz.PlayerServices;
import ch.qos.logback.classic.Logger;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerServicesImp implements PlayerServices {

    PlayerRepository playerRepository;
    private PlayerServicesImp playerConverter;
    private Logger log;


    private Player convertToEntity(PlayerDTO playerDTO) {
        Player player = new Player();
        player.setPlayerId(Math.toIntExact(playerDTO.getId()));
        player.setPlayerName(playerDTO.getPlayerName());
        player.setRegisterDate(playerDTO.getRegisterDate());
        player.setWinRate(playerDTO.getWinRate());
        return player;
    }

    private PlayerDTO convertToDto(Player updatedPlayer) {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setId(Long.valueOf(updatedPlayer.getPlayerId()));
        playerDTO.setPlayerName(updatedPlayer.getPlayerName());
        playerDTO.setRegisterDate(updatedPlayer.getRegisterDate());
        playerDTO.setWinRate(updatedPlayer.getWinRate());
        return playerDTO;
    }

    @Override
    public List<PlayerDTO> getAllPlayers() {
        List<Player> players = playerRepository.findAll();
        return players.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PlayerDTO save(PlayerDTO playerDTO) {
        String trimmedName = playerDTO.getPlayerName().trim();
        if (trimmedName.isEmpty()) {
            playerDTO.setPlayerName("Anonymous");
            log.info("Player name was empty, set to 'Anonymous'");
        } else {
            if (playerRepository.existsByPlayerName(trimmedName)) {
                throw new PlayerNameAlreadyExistsException("Player with name: " + trimmedName + " already exists");
            }
        }
        Player savedPlayer = playerRepository.save(playerConverter.convertToEntity(playerDTO));
        return playerConverter.convertToDto(savedPlayer);
    }

    @Override
    public PlayerDTO getPlayerById(Long id) {
        Optional<Player> playerOptional = playerRepository.findById(id);
        if (playerOptional.isPresent()) {
            Player player = playerOptional.get();
            return convertToDto(player);
        } else {
            throw new PlayerNotFoundException("Player with id: " + id + " not found");
        }
    }

    @Override
    public PlayerDTO createPlayer(PlayerDTO playerDTO) {
        Player player = convertToEntity(playerDTO);
        Player savedPlayer = playerRepository.save(player);
        return convertToDto(savedPlayer);
    }

    @Override
    public PlayerDTO updatePlayer(long playerId, PlayerDTO playerDTO) {
        Optional<Player> playerOptional = playerRepository.findById(playerId);
        if (playerOptional.isPresent()) {
            Player player = playerOptional.get();

            player.setPlayerName(playerDTO.getPlayerName());
            player.setRegisterDate(playerDTO.getRegisterDate());
            player.setWinRate(playerDTO.getWinRate());
            Player updatedPlayer = playerRepository.save(player);

            return convertToDto(updatedPlayer);
        } else {
            throw new PlayerNotFoundException("Player with id: " + playerId + " not found");
        }
    }
    @Override
    public void deletePlayer(Long id) {
        Optional<Player> playerOptional = playerRepository.findById(id);
        if (playerOptional.isPresent()) {
            playerRepository.deleteById(id);
        } else {
            throw new PlayerNotFoundException("Player with id: " + id + " not found");
        }

    }
    @Override
    public PlayerDTO getWinner() {
        List<Player> players = playerRepository.findAll();
        Optional<Player> playerMaxWinRate;

        if (!players.isEmpty()) {
            playerMaxWinRate = players.stream().sorted(Comparator.comparing(Player::winRate).reversed()).findFirst();
            return convertToDto(playerMaxWinRate.get());
        } else {
            throw new PlayerNotFoundException("No se han encontrado jugadores");
        }
    }
    @Override
    public PlayerDTO getLoser() {
        List<Player> players = playerRepository.findAll();
        Optional<Player> playerLessWinRate;

        if (!players.isEmpty()) {
            playerLessWinRate = players.stream().sorted(Comparator.comparing(Player::winRate)).findFirst();
            return convertToDto(playerLessWinRate.get());
        } else {
            throw new PlayerNotFoundException("No se han encontrado jugadores");
        }
    }
    @Override
    public double getSuccessRateAverage() {
        List<Player> players = playerRepository.findAll();

        if (!players.isEmpty()) {
            double totalWinRate = players.stream().mapToDouble(Player::winRate).sum(); //mapToDouble devuelve un flujo de "doubles". Por cada jugador, calcula su winRate.
            return totalWinRate/players.size();
        } else {
            throw new PlayerNotFoundException("No se han encontrado jugadores");
        }
    }

}


