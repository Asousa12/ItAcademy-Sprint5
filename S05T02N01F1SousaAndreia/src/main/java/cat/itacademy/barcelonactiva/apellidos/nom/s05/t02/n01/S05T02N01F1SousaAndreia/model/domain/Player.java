package cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.model.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer playerId;
    @Column(name="PlayerName")
    private String playerName;
    @Column(name="RegisterDate")
    private LocalDateTime registerDate;
    @OneToMany(mappedBy = "Player")
    private List<Game> gameList;
    private double winRate;

    public Player(Integer playerId) {
        this.playerId = playerId;
    }
    public void addingGame(Game game) {
        if (gameList==null) {
            gameList = new ArrayList<>();
        }
        gameList.add(game);
    }
    public double winRate() {
        if (gameList == null || gameList.isEmpty()) {
            return 0.0;
        }

        long totalGames = gameList.size();
        long totalWins = gameList.stream().filter(Game::isWon).count();

        return (double) totalWins / totalGames * 100;
    }

    public List<Game> getGamesList() {
        return gameList;
    }
}
