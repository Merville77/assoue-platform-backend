package com.assoue.platform.dto;

import com.assoue.platform.entity.RoleUtilisateur;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
    private String motDePasse;

    private String telephone;

    private String ville;

    @NotNull(message = "Le rôle est obligatoire")
    private RoleUtilisateur role;

    @Valid
    private GarageDetails garageDetails;

    @Valid
    private MunicipaliteDetails municipaliteDetails;

    @Valid
    private ArtisanDetails artisanDetails;

    @Getter
    @Setter
    public static class GarageDetails {
        @NotBlank(message = "Le nom du garage est obligatoire")
        private String nomGarage;

        @NotBlank(message = "L'adresse est obligatoire")
        private String adresse;
    }

    @Getter
    @Setter
    public static class MunicipaliteDetails {
        @NotBlank(message = "Le nom de la commune est obligatoire")
        private String nomCommune;

        private String zoneCouverte;
    }

    @Getter
    @Setter
    public static class ArtisanDetails {
        @NotBlank(message = "La spécialité est obligatoire")
        private String specialite;
    }
}