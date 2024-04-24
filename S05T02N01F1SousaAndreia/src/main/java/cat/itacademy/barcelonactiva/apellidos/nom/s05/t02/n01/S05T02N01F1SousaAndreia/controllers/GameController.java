package cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.controllers;

import cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.exception.GameAlreadyExistsException;
import cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.exception.InvalidInputException;
import cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.model.DTO.GameDTO;
import cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.model.services.Interfaz.GameServices;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/game")
public class GameController {

    @Autowired
    private GameServices gameServices;

    @GetMapping("/{id}")
    public ResponseEntity<List<GameDTO>> getAllGames() {
        List<GameDTO> games = gameServices.getAllGames();
        return ResponseEntity.ok(games);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameDTO> getGameById(@PathVariable Long id) {
        GameDTO game = gameServices.getGameById(id);
        if (game != null) {
            return ResponseEntity.ok(game);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> createGame(@RequestBody GameDTO gameDTO) {
        try {
            GameDTO createdGame = gameServices.createGame(gameDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Game created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create the game due to an internal server error");
        } catch (InvalidInputException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("update/{id}")
    public ResponseEntity<GameDTO> updateGame(@PathVariable Long id, @RequestBody GameDTO gameDTO) {
        GameDTO updatedGame = gameServices.updateGame(id, gameDTO);
        if (updatedGame != null) {
            return ResponseEntity.ok(updatedGame);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/{id}/games")
    public ResponseEntity<GameDTO> playerPlayGame(@PathVariable long id){
        try {
            GameDTO gameDTO = gameServices.playerPlayGame((int) id);
            return ResponseEntity.ok(gameDTO);
        } catch (EntityNotFoundException enfe) {
            System.out.println(enfe.getMessage());
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        boolean deleted = gameServices.deleteGame(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
