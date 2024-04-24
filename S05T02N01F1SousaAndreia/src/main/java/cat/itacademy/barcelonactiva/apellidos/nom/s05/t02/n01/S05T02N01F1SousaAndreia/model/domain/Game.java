package cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.deser.UnresolvedId;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;
import java.util.Random;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="games")
public class Game {

    private static String Status = "WON";
    private static String Status2 = "LOST";
    private Random random = new Random();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long gameId;
    @Column(name="diceOne")
    private int dice1;
    @Column(name="diceTwo")
    private int dice2;
    @Column(name="won")
    private boolean won;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "playerID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Player player;

    public Game(Player player) {
        this.player = player;
    }

    public boolean isWon() {
        return won;
    }

    public void rollDices() {
        this.dice1 = getRandomNumber();
        this.dice2 = getRandomNumber();
        calculateResult();
    }

    public void calculateResult() {
        if (this.dice1 + this.dice2 == 7) {
            this.won = true;
        } else {
            this.won = false;
        }
    }

    public int getRandomNumber() {
        int rand = random.nextInt(6) + 1;
        return rand;

    }

}
