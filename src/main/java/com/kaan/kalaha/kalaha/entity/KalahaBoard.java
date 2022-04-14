package com.kaan.kalaha.kalaha.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kaan.kalaha.entity.KalahaPit;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.HashCodeExclude;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * KalahaBoard domain class
 */
@Data
@Entity
@Table(name = "KALAHA_BOARD")
@NoArgsConstructor
public class KalahaBoard{

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonManagedReference
    @HashCodeExclude
    @OneToMany(mappedBy = "kalahaBoard")
    private List<KalahaPit> pits = new ArrayList<>();
}
