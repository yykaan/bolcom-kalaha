package com.kaan.kalaha.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * KalahaBoard domain class
 */
@Getter
@Setter
@Entity
@Table(name = "KALAHA_BOARD")
@NoArgsConstructor
public class KalahaBoard{

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @JsonManagedReference
    @OneToMany(mappedBy = "kalahaBoard")
    private List<KalahaPit> pits = new ArrayList<>();

    @ToString.Exclude
    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "game_id")
    private KalahaGame game;

    public KalahaBoard(KalahaGame game){
        this.game = game;
    }
}
