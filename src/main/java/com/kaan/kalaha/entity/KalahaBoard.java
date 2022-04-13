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

@Getter
@Setter
@Entity
@Table(name = "KALAHA_BOARD")
@SequenceGenerator(name = "kalaha_board_sequence", sequenceName = "kalaha_board_sequence", allocationSize = 1)
@NoArgsConstructor
public class KalahaBoard extends BaseEntity{

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
