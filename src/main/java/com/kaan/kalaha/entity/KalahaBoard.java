package com.kaan.kalaha.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "KALAHA_BOARD")
@SequenceGenerator(name = "kalaha_board_sequence", sequenceName = "kalaha_board_sequence", allocationSize = 1)
@NoArgsConstructor
@AllArgsConstructor
public class KalahaBoard extends BaseEntity{

    @OneToMany(mappedBy = "kalahaBoard")
    private List<KalahaPit> pits;

    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "game_id", nullable = false)
    private KalahaGame game;

    public KalahaBoard(KalahaGame game){
        this.game = game;
    }
}
