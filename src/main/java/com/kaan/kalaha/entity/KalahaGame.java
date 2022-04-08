package com.kaan.kalaha.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kaan.kalaha.enums.GameState;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "KALAHA_GAME")
@SequenceGenerator(name = "kalaha_game_sequence", sequenceName = "kalaha_game_sequence", allocationSize = 1)
@NoArgsConstructor
@AllArgsConstructor
public class KalahaGame extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "first_player_id", nullable = false)
    private KalahaPlayer firstPlayer;

    @ManyToOne
    @JoinColumn(name = "second_player_id")
    private KalahaPlayer secondPlayer;

    @ManyToOne
    @JoinColumn(name = "player_turn_id")
    private KalahaPlayer playerTurn;

    @OneToOne
    @JsonManagedReference
    @JoinColumn(name = "board_id", nullable = false)
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
