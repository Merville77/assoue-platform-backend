package com.assoue.platform.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "municipalite_profil")
@Getter
@Setter
public class MunicipaliteProfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "utilisateur_id", nullable = false, unique = true)
    private Utilisateur utilisateur;

    @Column(name = "nom_commune", nullable = false, length = 200)
    private String nomCommune;

    @Column(name = "zone_couverte", length = 255)
    private String zoneCouverte;
}