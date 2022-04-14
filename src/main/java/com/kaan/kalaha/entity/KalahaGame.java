package com.kaan.kalaha.entity;

import com.kaan.kalaha.enums.GameState;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.builder.HashCodeExclude;

import javax.persistence.*;

/**
 * KalahaGame domain class
 */
@Data
@Entity
@Table(name = "KALAHA_GAME")
@NoArgsConstructor
public class KalahaGame{

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "first_player_id", nullable = false)
    private KalahaPlayer firstPlayer;

    @ManyToOne
    @JoinColumn(name = "second_player_id")
    private KalahaPlayer secondPlayer;

    @ManyToOne
    @JoinColumn(name = "player_turn_id")
    private KalahaPlayer playerTurn;

    @ToString.Exclude
    @HashCodeExclude
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "board_id")
    private KalahaBoard kalahaBoard;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "GAME_STATE", nullable = false)
    private GameState gameState;

    public KalahaGame(KalahaPlayer player, KalahaPlayer playerTurn, GameState gameState) {
        this.firstPlayer = player;
        this.playerTurn = playerTurn;
        this.gameState = gameState;
    }
}
