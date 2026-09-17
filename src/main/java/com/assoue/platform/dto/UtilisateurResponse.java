package com.assoue.platform.dto;

import com.assoue.platform.entity.RoleUtilisateur;
import com.assoue.platform.entity.StatutCompte;
import com.assoue.platform.entity.Utilisateur;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UtilisateurResponse {

    private final Long id;
    private final String nom;
    private final String email;
    private final String telephone;
    private final String ville;
    private final RoleUtilisateur role;
    private final StatutCompte statutCompte;
    private final LocalDateTime dateCreation;

    public UtilisateurResponse(Utilisateur utilisateur) {
        this.id = utilisateur.getId();
        this.nom = utilisateur.getNom();
        this.email = utilisateur.getEmail();
        this.telephone = utilisateur.getTelephone();
        this.ville = utilisateur.getVille();
        this.role = utilisateur.getRole();
        this.statutCompte = utilisateur.getStatutCompte();
        this.dateCreation = utilisateur.getDateCreation();
    }
}