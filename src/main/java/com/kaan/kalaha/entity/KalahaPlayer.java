package com.kaan.kalaha.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Getter
@ToString
@Builder
@Entity
@Table(name = "KALAHA_PLAYER")
@SequenceGenerator(name = "kalaha_player_sequence", sequenceName = "kalaha_player_sequence", allocationSize = 1)
@NoArgsConstructor
public class KalahaPlayer extends BaseEntity{

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
