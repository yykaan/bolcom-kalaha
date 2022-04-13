package com.kaan.kalaha.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kaan.kalaha.enums.PitType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * KalahaPit domain class
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "KALAHA_PIT")
@NoArgsConstructor
public class KalahaPit{

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "STONES")
    private Integer stones;

    @Column(name = "POSITION", nullable = false)
    private int position;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "PIT_TYPE")
    private PitType pitType;

    @ToString.Exclude
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "KALAHABOARD_ID", nullable = false)
    private KalahaBoard kalahaBoard;

    public KalahaPit(KalahaBoard kalahaBoard, int position, int stones, PitType pitType) {
        this.kalahaBoard = kalahaBoard;
        this.position = position;
        this.stones = stones;
        this.pitType = pitType;
    }
}
