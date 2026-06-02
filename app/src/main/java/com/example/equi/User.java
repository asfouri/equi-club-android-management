package com.example.equi;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {
    @PrimaryKey
    private String email;
    private String nom;
    private String prenom;
    private String password;
    private String telephone;
    private String adresse;
    private String role;
    private String niveauDiplome;

    public User() {}

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getNiveauDiplome() { return niveauDiplome; }
    public void setNiveauDiplome(String niveauDiplome) { this.niveauDiplome = niveauDiplome; }
}
