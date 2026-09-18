package com.assoue.platform.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "garage_profil")
@Getter
@Setter
public class GarageProfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "utilisateur_id", nullable = false, unique = true)
    private Utilisateur utilisateur;

    @Column(name = "nom_garage", nullable = false, length = 200)
    private String nomGarage;

    @Column(nullable = false, length = 255)
    private String adresse;
}