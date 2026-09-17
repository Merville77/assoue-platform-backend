package com.assoue.platform.controller;

import com.assoue.platform.dto.RegisterRequest;
import com.assoue.platform.dto.UtilisateurResponse;
import com.assoue.platform.entity.Utilisateur;
import com.assoue.platform.service.UtilisateurService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UtilisateurService utilisateurService;

    public AuthController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @PostMapping("/register")
    public ResponseEntity<UtilisateurResponse> register(@Valid @RequestBody RegisterRequest request) {
    Utilisateur utilisateur = utilisateurService.inscrire(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(new UtilisateurResponse(utilisateur));
}
}