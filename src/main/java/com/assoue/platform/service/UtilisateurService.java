package com.assoue.platform.service;

import com.assoue.platform.dto.RegisterRequest;
import com.assoue.platform.entity.*;
import com.assoue.platform.repository.ArtisanProfilRepository;
import com.assoue.platform.repository.GarageProfilRepository;
import com.assoue.platform.repository.MunicipaliteProfilRepository;
import com.assoue.platform.repository.UtilisateurRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.EnumSet;
import java.util.Set;

@Service
public class UtilisateurService {

    private static final Set<RoleUtilisateur> ROLES_SANS_INSCRIPTION_PUBLIQUE =
            EnumSet.of(RoleUtilisateur.ADMINISTRATEUR, RoleUtilisateur.GESTIONNAIRE, RoleUtilisateur.COLLECTEUR);

    private static final Set<RoleUtilisateur> ROLES_AVEC_VALIDATION_ADMIN =
            EnumSet.of(RoleUtilisateur.GARAGE, RoleUtilisateur.MUNICIPALITE, RoleUtilisateur.ARTISAN);

    private final UtilisateurRepository utilisateurRepository;
    private final GarageProfilRepository garageProfilRepository;
    private final MunicipaliteProfilRepository municipaliteProfilRepository;
    private final ArtisanProfilRepository artisanProfilRepository;
    private final PasswordEncoder passwordEncoder;

    public UtilisateurService(UtilisateurRepository utilisateurRepository,
                               GarageProfilRepository garageProfilRepository,
                               MunicipaliteProfilRepository municipaliteProfilRepository,
                               ArtisanProfilRepository artisanProfilRepository,
                               PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.garageProfilRepository = garageProfilRepository;
        this.municipaliteProfilRepository = municipaliteProfilRepository;
        this.artisanProfilRepository = artisanProfilRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Utilisateur inscrire(RegisterRequest request) {

        if (ROLES_SANS_INSCRIPTION_PUBLIQUE.contains(request.getRole())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Ce rôle ne peut pas être créé par inscription publique");
        }

        validerDetailsSelonRole(request);

        if (utilisateurRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cet email est déjà utilisé");
        }

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(request.getNom());
        utilisateur.setEmail(request.getEmail());
        utilisateur.setMotDePasse(passwordEncoder.encode(request.getMotDePasse()));
        utilisateur.setTelephone(request.getTelephone());
        utilisateur.setVille(request.getVille());
        utilisateur.setRole(request.getRole());
        utilisateur.setStatutCompte(determinerStatutInitial(request.getRole()));

        Utilisateur u = utilisateurRepository.save(utilisateur);

        switch (request.getRole()) {
            case GARAGE -> {
                GarageProfil profil = new GarageProfil();
                profil.setUtilisateur(u);
                profil.setNomGarage(request.getGarageDetails().getNomGarage());
                profil.setAdresse(request.getGarageDetails().getAdresse());
                garageProfilRepository.save(profil);
            }
            case MUNICIPALITE -> {
                MunicipaliteProfil profil = new MunicipaliteProfil();
                profil.setUtilisateur(u);
                profil.setNomCommune(request.getMunicipaliteDetails().getNomCommune());
                profil.setZoneCouverte(request.getMunicipaliteDetails().getZoneCouverte());
                municipaliteProfilRepository.save(profil);
            }
            case ARTISAN -> {
                ArtisanProfil profil = new ArtisanProfil();
                profil.setUtilisateur(u);
                profil.setSpecialite(request.getArtisanDetails().getSpecialite());
                artisanProfilRepository.save(profil);
            }
            default -> { /* CITOYEN, CLIENT : aucun profil à créer */ }
        }

        return u;
    }

    private void validerDetailsSelonRole(RegisterRequest request) {
        switch (request.getRole()) {
            case GARAGE -> {
                if (request.getGarageDetails() == null) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Le champ garageDetails est requis pour le rôle GARAGE");
                }
            }
            case MUNICIPALITE -> {
                if (request.getMunicipaliteDetails() == null) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Le champ municipaliteDetails est requis pour le rôle MUNICIPALITE");
                }
            }
            case ARTISAN -> {
                if (request.getArtisanDetails() == null) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Le champ artisanDetails est requis pour le rôle ARTISAN");
                }
            }
            default -> { /* CITOYEN, CLIENT : aucun détail requis */ }
        }
    }

    private StatutCompte determinerStatutInitial(RoleUtilisateur role) {
        return ROLES_AVEC_VALIDATION_ADMIN.contains(role)
                ? StatutCompte.EN_ATTENTE_VALIDATION
                : StatutCompte.ACTIF;
    }
}