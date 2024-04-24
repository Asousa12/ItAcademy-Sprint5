package cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.controllers;

import cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.exception.PlayerNotFoundException;
import cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.model.DTO.PlayerDTO;
import cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.model.services.Interfaz.PlayerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/player")
public class PlayerController {

    @Autowired
    private PlayerServices playerServices;

    @GetMapping
    public ResponseEntity<List<PlayerDTO>> getAllPlayers() {
        List<PlayerDTO> players = playerServices.getAllPlayers();
        return ResponseEntity.ok(players);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDTO> getPlayerById(@PathVariable Long id) {
        PlayerDTO player = playerServices.getPlayerById(id);
        return ResponseEntity.ok(player);
    }

    @PostMapping
    public ResponseEntity<PlayerDTO> createPlayer(@RequestBody PlayerDTO playerDTO) {
        PlayerDTO createdPlayer = playerServices.createPlayer(playerDTO);
        return new ResponseEntity<>(createdPlayer, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlayerDTO> updatePlayer(@PathVariable Long id, @RequestBody PlayerDTO playerDTO) {
        PlayerDTO updatedPlayer = playerServices.updatePlayer(id, playerDTO);
        return ResponseEntity.ok(updatedPlayer);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        playerServices.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/ranking/loser")
    public ResponseEntity<PlayerDTO> loserPlayer(){
        try {
            return ResponseEntity.ok(playerServices.getLoser());
        } catch (PlayerNotFoundException pnfe) {
            System.out.println(pnfe.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/ranking/winner")
    public ResponseEntity<PlayerDTO> winnerPlayer(){
        try {
            return ResponseEntity.ok(playerServices.getWinner());
        } catch (PlayerNotFoundException pnfe) {
            System.out.println(pnfe.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/ranking")
    public ResponseEntity<Double> avgWinRate() {
        try {
            return ResponseEntity.ok(playerServices.getSuccessRateAverage());
        } catch (PlayerNotFoundException pnfe) {
            System.out.println(pnfe.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
