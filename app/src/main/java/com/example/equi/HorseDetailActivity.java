package com.example.equi;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import io.realm.Realm;
import java.util.UUID;

public class HorseDetailActivity extends AdminMenuActivity {

    private EditText etHorseNom;
    private EditText etHorseParent;
    private EditText etHorseElevage;
    private EditText etHorseRace;
    private EditText etHorseAge;
    private EditText etHorseToday;
    private EditText etHorseState;
    private ImageView imgHorse;
    private Realm realm;
    private String horseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horse_detail);
        setupMenuToolbar(R.id.toolbar_horse_detail, "Gestion du cheval", true);

        realm = Realm.getDefaultInstance();

        imgHorse = findViewById(R.id.img_detail_horse);
        etHorseNom = findViewById(R.id.et_horse_nom);
        etHorseParent = findViewById(R.id.et_horse_parent);
        etHorseElevage = findViewById(R.id.et_horse_elevage);
        etHorseRace = findViewById(R.id.et_horse_race);
        etHorseAge = findViewById(R.id.et_horse_age);
        etHorseToday = findViewById(R.id.et_horse_today);
        etHorseState = findViewById(R.id.et_horse_state);

        Button btnAdd = findViewById(R.id.btn_add_horse);
        Button btnUpdate = findViewById(R.id.btn_update_horse);
        Button btnDelete = findViewById(R.id.btn_delete_horse);
        Button btnBack = findViewById(R.id.btn_back_horse);

        horseId = getIntent().getStringExtra("HORSE_ID");
        if (horseId != null) {
            loadHorseData(horseId);
        } else {
            imgHorse.setImageResource(R.drawable.logo_equ);
        }

        btnAdd.setOnClickListener(v -> addHorse());
        btnUpdate.setOnClickListener(v -> updateHorse());
        btnDelete.setOnClickListener(v -> deleteHorse());
        btnBack.setOnClickListener(v -> finish());
    }

    private void loadHorseData(String id) {
        Horse horse = realm.where(Horse.class).equalTo("id", id).findFirst();
        if (horse != null) {
            etHorseNom.setText(horse.getName());
            etHorseParent.setText(horse.getFatherName());
            etHorseElevage.setText(horse.getElevage());
            etHorseRace.setText(horse.getRace());
            etHorseAge.setText(String.valueOf(horse.getAge()));
            etHorseToday.setText(horse.getACoursAujourdhui() ? "oui" : "non");
            etHorseState.setText(horse.isSick() ? Horse.STATE_SICK : Horse.STATE_GOOD);

            imgHorse.setImageResource(DrawableResolver.resolveHorseImage(horse.getPhotoUrl()));
        }
    }

    private boolean hasCoursAujourdhui() {
        return etHorseToday.getText().toString().trim().equalsIgnoreCase("oui");
    }

    private String getSelectedHealthState() {
        String requestedState = etHorseState.getText().toString().trim();
        String normalized = Horse.normalizeHealthState(requestedState);
        if (Horse.STATE_STOPPED.equals(normalized)) {
            return Horse.STATE_GOOD;
        }
        return normalized;
    }

    private void addHorse() {
        String nom = etHorseNom.getText().toString().trim();
        if (nom.isEmpty()) {
            Toast.makeText(this, "Veuillez saisir un nom", Toast.LENGTH_SHORT).show();
            return;
        }

        realm.executeTransaction(r -> {
            Horse horse = r.createObject(Horse.class, UUID.randomUUID().toString());
            horse.setName(nom);
            horse.setFatherName(etHorseParent.getText().toString());
            horse.setElevage(etHorseElevage.getText().toString());
            horse.setRace(etHorseRace.getText().toString());
            try {
                horse.setAge(Integer.parseInt(etHorseAge.getText().toString()));
            } catch (Exception e) {
                horse.setAge(0);
            }
            horse.setACoursAujourdhui(hasCoursAujourdhui());
            horse.setEtatSante(getSelectedHealthState());
            horse.setPhotoUrl("logo_equ");
        });
        Toast.makeText(this, "Cheval ajoute", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void updateHorse() {
        if (horseId == null) {
            return;
        }

        if (etHorseNom.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Le nom du cheval est obligatoire", Toast.LENGTH_SHORT).show();
            return;
        }

        realm.executeTransaction(r -> {
            Horse horse = r.where(Horse.class).equalTo("id", horseId).findFirst();
            if (horse != null) {
                horse.setName(etHorseNom.getText().toString());
                horse.setFatherName(etHorseParent.getText().toString());
                horse.setElevage(etHorseElevage.getText().toString());
                horse.setRace(etHorseRace.getText().toString());
                try {
                    horse.setAge(Integer.parseInt(etHorseAge.getText().toString()));
                } catch (Exception ignored) {
                }
                horse.setACoursAujourdhui(hasCoursAujourdhui());
                horse.setEtatSante(getSelectedHealthState());
            }
        });
        Toast.makeText(this, "Cheval modifie", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void deleteHorse() {
        if (horseId == null) {
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("Supprimer le cheval")
                .setMessage("Confirmer la suppression de ce cheval ?")
                .setPositiveButton("Supprimer", (dialog, which) -> {
                    realm.executeTransaction(r -> {
                        Horse horse = r.where(Horse.class).equalTo("id", horseId).findFirst();
                        if (horse != null) {
                            horse.deleteFromRealm();
                        }
                    });
                    Toast.makeText(this, "Cheval supprime", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("Annuler", null)
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
