package com.kaan.kalaha.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kaan.kalaha.enums.PitType;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "KALAHA_PIT")
@SequenceGenerator(name = "kalaha_pit_sequence", sequenceName = "kalaha_pit_sequence", allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
public class KalahaPit extends BaseEntity {

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
