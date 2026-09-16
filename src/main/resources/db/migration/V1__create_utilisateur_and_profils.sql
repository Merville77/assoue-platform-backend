CREATE TABLE utilisateur (
    id              BIGSERIAL PRIMARY KEY,
    nom             VARCHAR(150) NOT NULL,
    telephone       VARCHAR(30),
    email           VARCHAR(150) NOT NULL UNIQUE,
    mot_de_passe    VARCHAR(255) NOT NULL,
    ville           VARCHAR(150),
    role            VARCHAR(30) NOT NULL,
    statut_compte   VARCHAR(30) NOT NULL DEFAULT 'ACTIF',
    date_creation   TIMESTAMP NOT NULL DEFAULT now(),
    CONSTRAINT chk_role CHECK (role IN (
        'ADMINISTRATEUR','GESTIONNAIRE','COLLECTEUR','ARTISAN',
        'CITOYEN','CLIENT','GARAGE','MUNICIPALITE'
    )),
    CONSTRAINT chk_statut CHECK (statut_compte IN (
        'EN_ATTENTE_VALIDATION','ACTIF','REJETE','SUSPENDU'
    ))
);

CREATE INDEX idx_utilisateur_email ON utilisateur(email);
CREATE INDEX idx_utilisateur_role ON utilisateur(role);
CREATE INDEX idx_utilisateur_statut ON utilisateur(statut_compte);

CREATE TABLE garage_profil (
    id              BIGSERIAL PRIMARY KEY,
    utilisateur_id  BIGINT NOT NULL UNIQUE REFERENCES utilisateur(id) ON DELETE CASCADE,
    nom_garage      VARCHAR(200) NOT NULL,
    adresse         VARCHAR(255) NOT NULL
);

CREATE TABLE municipalite_profil (
    id              BIGSERIAL PRIMARY KEY,
    utilisateur_id  BIGINT NOT NULL UNIQUE REFERENCES utilisateur(id) ON DELETE CASCADE,
    nom_commune     VARCHAR(200) NOT NULL,
    zone_couverte   VARCHAR(255)
);

CREATE TABLE artisan_profil (
    id              BIGSERIAL PRIMARY KEY,
    utilisateur_id  BIGINT NOT NULL UNIQUE REFERENCES utilisateur(id) ON DELETE CASCADE,
    specialite      VARCHAR(150) NOT NULL
);