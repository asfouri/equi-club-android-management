package com.example.equi;

import android.app.Application;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmResults;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmMigration migration = (realm, oldVersion, newVersion) -> {
            if (oldVersion == 1) {
                if (realm.getSchema().get("Session") != null && !realm.getSchema().get("Session").hasField("date")) {
                    realm.getSchema().get("Session").addField("date", String.class);
                }
                oldVersion++;
            }
        };
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("equi.realm")
                .schemaVersion(2)
                .migration(migration)
                .allowWritesOnUiThread(true)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

        ajouterChevauxInitiaux();
        ajouterUtilisateursInitiaux();
        ajouterSeancesInitiales();
        ajouterDatesFictivesAuxSeancesExistantes();
        completerDonneesDemoChevaux();
    }

    private void ajouterChevauxInitiaux() {
        Realm realm = Realm.getDefaultInstance();

        if (realm.where(Horse.class).count() == 0) {
            realm.executeTransaction(r -> {
                Horse h1 = new Horse();
                h1.setId("1");
                h1.setName("Denver de Berni");
                h1.setAge(7);
                h1.setRace("KWPN");
                h1.setFatherName("Eldorado van");
                h1.setMotherName("Vanya de Berni");
                h1.setEtatSante(Horse.STATE_GOOD);
                h1.setACoursAujourdhui(true);
                h1.setPhotoUrl("denverdeberni");
                h1.setCavalierNom("Yassine Alaoui");
                r.copyToRealmOrUpdate(h1);

                Horse h2 = new Horse();
                h2.setId("2");
                h2.setName("Calypso Dream");
                h2.setAge(11);
                h2.setRace("Arabe-Barbe");
                h2.setFatherName("Rex");
                h2.setMotherName("Luna");
                h2.setEtatSante(Horse.STATE_GOOD);
                h2.setACoursAujourdhui(true);
                h2.setPhotoUrl("calypsodream");
                h2.setCavalierNom("Salma Idrissi");
                r.copyToRealmOrUpdate(h2);

                Horse h3 = new Horse();
                h3.setId("3");
                h3.setName("Flash Rider");
                h3.setAge(9);
                h3.setRace("Selle francais");
                h3.setFatherName("Quick Love");
                h3.setMotherName("Diamantine");
                h3.setEtatSante(Horse.STATE_GOOD);
                h3.setACoursAujourdhui(false);
                h3.setPhotoUrl("flashrider");
                h3.setCavalierNom("Mehdi Naciri");
                r.copyToRealmOrUpdate(h3);

                Horse h4 = new Horse();
                h4.setId("4");
                h4.setName("Zahra");
                h4.setAge(6);
                h4.setRace("Barbe");
                h4.setFatherName("Atlas");
                h4.setMotherName("Rima");
                h4.setEtatSante(Horse.STATE_SICK);
                h4.setACoursAujourdhui(false);
                h4.setPhotoUrl("zahra");
                h4.setCavalierNom("Imane Tazi");
                r.copyToRealmOrUpdate(h4);

                Horse h5 = new Horse();
                h5.setId("5");
                h5.setName("Apache King");
                h5.setAge(12);
                h5.setRace("Paint Horse");
                h5.setFatherName("Marco");
                h5.setMotherName("Bella");
                h5.setEtatSante(Horse.STATE_GOOD);
                h5.setACoursAujourdhui(true);
                h5.setPhotoUrl("apacheking");
                h5.setCavalierNom("Sara Idrissi");
                r.copyToRealmOrUpdate(h5);
            });
        }

        realm.close();
    }

    private void ajouterUtilisateursInitiaux() {
        Realm realm = Realm.getDefaultInstance();

        if (realm.where(User.class).count() == 0) {
            realm.executeTransaction(r -> {
                User admin = r.createObject(User.class, "admin@etrier.ma");
                admin.setNom("Ali El Hemma");
                admin.setPrenom("Ali");
                admin.setPassword("admin123");
                admin.setTelephone("0612345678");
                admin.setAdresse("Casablanca");
                admin.setRole("ADMIN");
                admin.setNiveauDiplome("Responsable du club");

                User rider1 = r.createObject(User.class, "yassine@etrier.ma");
                rider1.setNom("Yassine Alaoui");
                rider1.setPrenom("Yassine");
                rider1.setPassword("cavalier123");
                rider1.setTelephone("0600112233");
                rider1.setAdresse("Casablanca");
                rider1.setRole("Cavalier");
                rider1.setNiveauDiplome("Galop 3");

                User rider2 = r.createObject(User.class, "salma@etrier.ma");
                rider2.setNom("Salma Idrissi");
                rider2.setPrenom("Salma");
                rider2.setPassword("cavalier123");
                rider2.setTelephone("0600223344");
                rider2.setAdresse("Rabat");
                rider2.setRole("Cavalier");
                rider2.setNiveauDiplome("Galop 4");

                User rider3 = r.createObject(User.class, "mehdi@etrier.ma");
                rider3.setNom("Mehdi Naciri");
                rider3.setPrenom("Mehdi");
                rider3.setPassword("cavalier123");
                rider3.setTelephone("0600334455");
                rider3.setAdresse("Mohammedia");
                rider3.setRole("Cavalier");
                rider3.setNiveauDiplome("Debutant");

                User rider4 = r.createObject(User.class, "imane@etrier.ma");
                rider4.setNom("Imane Tazi");
                rider4.setPrenom("Imane");
                rider4.setPassword("cavalier123");
                rider4.setTelephone("0600445566");
                rider4.setAdresse("Bouskoura");
                rider4.setRole("Cavalier");
                rider4.setNiveauDiplome("Galop 2");
            });
        }

        realm.close();
    }

    private void ajouterSeancesInitiales() {
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(r -> {
            ajouterSeance(r, "session-1", SessionDateUtils.getSuggestedFakeDate(0), "07:45", "Yassine Alaoui", "Denver de Berni");
            ajouterSeance(r, "session-2", SessionDateUtils.getSuggestedFakeDate(0), "08:30", "Salma Idrissi", "Calypso Dream");
            ajouterSeance(r, "session-3", SessionDateUtils.getSuggestedFakeDate(1), "09:15", "Mehdi Naciri", "Flash Rider");
            ajouterSeance(r, "session-4", SessionDateUtils.getSuggestedFakeDate(2), "10:30", "Imane Tazi", "Zahra");
            ajouterSeance(r, "session-5", SessionDateUtils.getSuggestedFakeDate(3), "11:30", "Sara Idrissi", "Apache King");
            ajouterSeance(r, "session-6", SessionDateUtils.getSuggestedFakeDate(3), "13:00", "Yassine Alaoui", "Apache King");
            ajouterSeance(r, "session-7", SessionDateUtils.getSuggestedFakeDate(4), "14:30", "Salma Idrissi", "Denver de Berni");
            ajouterSeance(r, "session-8", SessionDateUtils.getSuggestedFakeDate(5), "16:00", "Mehdi Naciri", "Calypso Dream");
            ajouterSeance(r, "session-9", SessionDateUtils.getSuggestedFakeDate(6), "17:30", "Imane Tazi", "Flash Rider");
            ajouterSeance(r, "session-10", SessionDateUtils.getSuggestedFakeDate(6), "19:00", "Sara Idrissi", "Zahra");
        });

        realm.close();
    }

    private void ajouterSeance(Realm realm, String id, String date, String heure, String cavalier, String cheval) {
        Session session = realm.where(Session.class).equalTo("id", id).findFirst();
        if (session != null && session.getDate() != null && !session.getDate().trim().isEmpty()) {
            return;
        }

        if (session == null) {
            session = realm.createObject(Session.class, id);
        }
        session.setDate(date);
        session.setHeure(heure);
        session.setCavalierNom(cavalier);
        session.setChevalNom(cheval);
    }

    private void ajouterDatesFictivesAuxSeancesExistantes() {
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(r -> {
            RealmResults<Session> sessions = r.where(Session.class).findAll();
            for (int i = 0; i < sessions.size(); i++) {
                Session session = sessions.get(i);
                if (session != null && (session.getDate() == null || session.getDate().trim().isEmpty())) {
                    session.setDate(SessionDateUtils.getSuggestedFakeDate(i));
                }
            }
        });

        realm.close();
    }

    private void completerDonneesDemoChevaux() {
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(r -> {
            Horse zahra = r.where(Horse.class).equalTo("id", "4").findFirst();
            if (zahra != null) {
                zahra.setEtatSante(Horse.STATE_SICK);
                zahra.setCavalierNom("Imane Tazi");
                zahra.setElevage("Haras Atlas");
            }

            Horse calypso = r.where(Horse.class).equalTo("id", "2").findFirst();
            if (calypso != null) {
                calypso.setEtatSante(Horse.STATE_GOOD);
                calypso.setElevage("Domaine des Oliviers");
            }

            Horse denver = r.where(Horse.class).equalTo("id", "1").findFirst();
            if (denver != null && (denver.getElevage() == null || denver.getElevage().isEmpty())) {
                denver.setElevage("Haras Berni");
            }

            Horse flash = r.where(Horse.class).equalTo("id", "3").findFirst();
            if (flash != null) {
                flash.setCavalierNom("Mehdi Naciri");
                if (flash.getElevage() == null || flash.getElevage().isEmpty()) {
                    flash.setElevage("Haras du Soleil");
                }
            }

            Horse apache = r.where(Horse.class).equalTo("id", "5").findFirst();
            if (apache != null && (apache.getElevage() == null || apache.getElevage().isEmpty())) {
                apache.setElevage("Domaine Royal");
            }
        });

        realm.close();
    }
}
