package com.example.equi;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import java.util.UUID;

public class Session extends RealmObject {
    @PrimaryKey
    private String id = UUID.randomUUID().toString();
    private String date;
    private String heure;
    private String cavalierNom;
    private String chevalNom;

    public Session() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getHeure() { return heure; }
    public void setHeure(String heure) { this.heure = heure; }

    public String getCavalierNom() { return cavalierNom; }
    public void setCavalierNom(String cavalierNom) { this.cavalierNom = cavalierNom; }

    public String getChevalNom() { return chevalNom; }
    public void setChevalNom(String chevalNom) { this.chevalNom = chevalNom; }
}
