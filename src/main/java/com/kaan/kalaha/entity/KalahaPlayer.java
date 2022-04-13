package com.kaan.kalaha.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * KalahaPlayer domain class
 */

@Data
@Entity
@Table(name = "KALAHA_PLAYER")
@NoArgsConstructor
public class KalahaPlayer{

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "USER_NAME", unique = true, nullable = false)
    private String username;

    @Column(name = "EMAIL", unique = true, nullable = false)
    private String email;

    @JsonIgnore
    @Column(name = "PASSWORD", nullable = false)
    private String password;

    //create constructor with username, email, password
    public KalahaPlayer(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
