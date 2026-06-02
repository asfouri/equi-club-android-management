package com.example.equi;

import io.realm.Realm;
import io.realm.RealmResults;
import java.util.UUID;

public class DataManager {

    private Realm realm;

    public DataManager() {
        realm = Realm.getDefaultInstance();
    }

    // --- CRUD SESSIONS (Comme l'exercice CRUD Etudiant du TP) ---

    public void addSession(String heure, String cavalier, String cheval) {
        addSession(SessionDateUtils.todayIso(), heure, cavalier, cheval);
    }

    public void addSession(String date, String heure, String cavalier, String cheval) {
        realm.executeTransaction(r -> {
            Session session = r.createObject(Session.class, UUID.randomUUID().toString());
            session.setDate(date);
            session.setHeure(heure);
            session.setCavalierNom(cavalier);
            session.setChevalNom(cheval);
        });
    }

    public RealmResults<Session> getAllSessions() {
        return realm.where(Session.class).findAll();
    }

    public void updateSession(String id, String heure, String cavalier, String cheval) {
        updateSession(id, SessionDateUtils.todayIso(), heure, cavalier, cheval);
    }

    public void updateSession(String id, String date, String heure, String cavalier, String cheval) {
        realm.executeTransaction(r -> {
            Session session = r.where(Session.class).equalTo("id", id).findFirst();
            if (session != null) {
                session.setDate(date);
                session.setHeure(heure);
                session.setCavalierNom(cavalier);
                session.setChevalNom(cheval);
            }
        });
    }

    public void deleteSession(String id) {
        realm.executeTransaction(r -> {
            Session session = r.where(Session.class).equalTo("id", id).findFirst();
            if (session != null) {
                session.deleteFromRealm();
            }
        });
    }

    public void close() {
        if (realm != null) {
            realm.close();
        }
    }
}
