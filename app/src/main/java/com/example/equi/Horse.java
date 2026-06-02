package com.example.equi;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import java.util.Locale;

public class Horse extends RealmObject {
    public static final String STATE_GOOD = "Bon";
    public static final String STATE_SICK = "Malade";
    public static final String STATE_STOPPED = "Arrete";
    public static final int STOPPED_AFTER_HOUR = 18;

    @PrimaryKey
    private String id;
    private String name;
    private int age;
    private String race;
    private String fatherName;
    private String motherName;
    private String elevage;
    
    // Pedigree "Côté du Chef"
    private String chefNom;
    private String chefParent;
    private String chefElevage;
    private String chefRace;
    private int chefAge;

    private String photoUrl;
    private String etatSante = STATE_GOOD;
    private boolean aCoursAujourdhui = false;
    private String cavalierNom = "";

    public Horse() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getRace() { return race; }
    public void setRace(String race) { this.race = race; }

    public String getFatherName() { return fatherName; }
    public void setFatherName(String fatherName) { this.fatherName = fatherName; }

    public String getMotherName() { return motherName; }
    public void setMotherName(String motherName) { this.motherName = motherName; }

    public String getElevage() { return elevage; }
    public void setElevage(String elevage) { this.elevage = elevage; }

    public String getChefNom() { return chefNom; }
    public void setChefNom(String chefNom) { this.chefNom = chefNom; }

    public String getChefParent() { return chefParent; }
    public void setChefParent(String chefParent) { this.chefParent = chefParent; }

    public String getChefElevage() { return chefElevage; }
    public void setChefElevage(String chefElevage) { this.chefElevage = chefElevage; }

    public String getChefRace() { return chefRace; }
    public void setChefRace(String chefRace) { this.chefRace = chefRace; }

    public int getChefAge() { return chefAge; }
    public void setChefAge(int chefAge) { this.chefAge = chefAge; }

    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }

    public String getEtatSante() { return etatSante; }
    public void setEtatSante(String etatSante) {
        this.etatSante = normalizeHealthState(etatSante);
    }

    public boolean getACoursAujourdhui() { return aCoursAujourdhui; }
    public void setACoursAujourdhui(boolean aCoursAujourdhui) { this.aCoursAujourdhui = aCoursAujourdhui; }

    public String getCavalierNom() { return cavalierNom; }
    public void setCavalierNom(String cavalierNom) { this.cavalierNom = cavalierNom; }

    public boolean isSick() {
        return STATE_SICK.equalsIgnoreCase(normalizeHealthState(etatSante));
    }

    public boolean isStopped(int currentHour) {
        return !isSick() && aCoursAujourdhui && currentHour >= STOPPED_AFTER_HOUR;
    }

    public String getCurrentState(int currentHour) {
        if (isSick()) {
            return STATE_SICK;
        }
        if (isStopped(currentHour)) {
            return STATE_STOPPED;
        }
        return STATE_GOOD;
    }

    public static String normalizeHealthState(String state) {
        if (state == null) {
            return STATE_GOOD;
        }

        String normalized = state.trim().toLowerCase(Locale.ROOT);
        if (normalized.equals("malade") || normalized.equals("sick")) {
            return STATE_SICK;
        }
        if (normalized.equals("arrete") || normalized.equals("stopped")) {
            return STATE_STOPPED;
        }
        return STATE_GOOD;
    }
}
