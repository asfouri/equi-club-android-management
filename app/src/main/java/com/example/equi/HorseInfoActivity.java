package com.example.equi;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import io.realm.Realm;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class HorseInfoActivity extends RiderMenuActivity {

    private ImageView imgHorse;
    private TextView tvHorseName;
    private TextView tvHorseParent;
    private TextView tvHorseElevage;
    private TextView tvHorseRace;
    private TextView tvHorseAge;
    private TextView tvHorseToday;
    private TextView tvHorseHealth;
    private Realm realm;
    private String horseId;
    private String horseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horse_info);
        setupMenuToolbar(R.id.toolbar_horse_info, "Informations du cheval", true);

        imgHorse = findViewById(R.id.img_info_horse);
        tvHorseName = findViewById(R.id.tv_info_horse_name);
        tvHorseParent = findViewById(R.id.tv_info_horse_parent);
        tvHorseElevage = findViewById(R.id.tv_info_horse_elevage);
        tvHorseRace = findViewById(R.id.tv_info_horse_race);
        tvHorseAge = findViewById(R.id.tv_info_horse_age);
        tvHorseToday = findViewById(R.id.tv_info_horse_today);
        tvHorseHealth = findViewById(R.id.tv_info_horse_health);
        Button btnUpdateState = findViewById(R.id.btn_update_horse_state);
        Button btnScheduleSession = findViewById(R.id.btn_schedule_horse_session);
        Button btnBack = findViewById(R.id.btn_back_horse_info);

        realm = Realm.getDefaultInstance();

        horseId = getIntent().getStringExtra("HORSE_ID");
        if (horseId != null) {
            loadHorseData(horseId);
        }

        btnUpdateState.setOnClickListener(v -> showStateDialog());
        btnScheduleSession.setOnClickListener(v -> showScheduleDialog());
        btnBack.setOnClickListener(v -> finish());
    }

    private void loadHorseData(String horseId) {
        Horse horse = realm.where(Horse.class).equalTo("id", horseId).findFirst();
        if (horse == null) {
            return;
        }

        horseName = horse.getName();
        tvHorseName.setText(horse.getName());
        tvHorseParent.setText("Parent : " + safeText(horse.getFatherName()));
        tvHorseElevage.setText("Elevage : " + safeText(horse.getElevage()));
        tvHorseRace.setText("Race : " + safeText(horse.getRace()));
        tvHorseAge.setText("Age : " + horse.getAge());
        tvHorseToday.setText("Cours aujourd'hui : " + (horse.getACoursAujourdhui() ? "oui" : "non"));
        tvHorseHealth.setText("Etat : " + horse.getCurrentState(LocalTime.now().getHour()));

        imgHorse.setImageResource(DrawableResolver.resolveHorseImage(horse.getPhotoUrl()));
    }

    private String safeText(String value) {
        return value == null || value.isEmpty() ? "Non renseigne" : value;
    }

    private void showStateDialog() {
        if (horseId == null) {
            return;
        }

        String[] states = {Horse.STATE_GOOD, Horse.STATE_SICK};
        new AlertDialog.Builder(this)
                .setTitle("Mettre a jour l'etat")
                .setItems(states, (dialog, which) -> {
                    String selectedState = states[which];
                    realm.executeTransaction(r -> {
                        Horse horse = r.where(Horse.class).equalTo("id", horseId).findFirst();
                        if (horse != null) {
                            horse.setEtatSante(selectedState);
                        }
                    });
                    loadHorseData(horseId);
                    Toast.makeText(this, "Etat du cheval mis a jour", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Annuler", null)
                .show();
    }

    private void showScheduleDialog() {
        if (horseId == null) {
            return;
        }

        Horse horse = realm.where(Horse.class).equalTo("id", horseId).findFirst();
        if (horse == null) {
            return;
        }

        String currentState = horse.getCurrentState(LocalTime.now().getHour());
        if (Horse.STATE_SICK.equals(currentState) || Horse.STATE_STOPPED.equals(currentState)) {
            Toast.makeText(this, "Ce cheval n'est pas disponible pour une seance", Toast.LENGTH_SHORT).show();
            return;
        }

        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_schedule_session, null);
        EditText etDate = dialogView.findViewById(R.id.et_session_date);
        EditText etHeure = dialogView.findViewById(R.id.et_session_time);

        LocalDate today = LocalDate.now();

        etDate.setText(today.toString());

        etDate.setOnClickListener(v -> showDatePicker(etDate));

        new AlertDialog.Builder(this)
                .setTitle("Programmer une seance")
                .setMessage("Choisissez le jour et l'heure pour " + horse.getName())
                .setView(dialogView)
                .setPositiveButton("Valider", (dialog, which) -> saveSession(
                        etDate.getText().toString().trim(),
                        etHeure.getText().toString().trim()))
                .setNegativeButton("Annuler", null)
                .show();
    }

    private void showDatePicker(EditText target) {
        LocalDate initialDate = SessionDateUtils.parseDate(target.getText().toString().trim());
        if (initialDate == null) {
            initialDate = LocalDate.now();
        }

        new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) ->
                        target.setText(LocalDate.of(year, month + 1, dayOfMonth).toString()),
                initialDate.getYear(),
                initialDate.getMonthValue() - 1,
                initialDate.getDayOfMonth()
        ).show();
    }

    private void saveSession(String sessionDate, String heure) {
        if (horseId == null || horseName == null) {
            return;
        }
        if (sessionDate.isEmpty()) {
            Toast.makeText(this, "Veuillez choisir une date", Toast.LENGTH_SHORT).show();
            return;
        }
        if (heure.isEmpty()) {
            Toast.makeText(this, "Veuillez saisir une heure", Toast.LENGTH_SHORT).show();
            return;
        }
        if (SessionDateUtils.parseDate(sessionDate) == null) {
            Toast.makeText(this, "Date invalide. Format attendu : AAAA-MM-JJ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (SessionDateUtils.parseTime(heure) == null) {
            Toast.makeText(this, "Heure invalide. Format attendu : HH:MM", Toast.LENGTH_SHORT).show();
            return;
        }

        String riderName = resolveUserName();
        boolean alreadyExists = realm.where(Session.class)
                .equalTo("date", sessionDate)
                .equalTo("heure", heure)
                .equalTo("chevalNom", horseName)
                .findFirst() != null;
        if (alreadyExists) {
            Toast.makeText(this, "Une seance existe deja pour ce cheval a cette heure", Toast.LENGTH_SHORT).show();
            return;
        }

        realm.executeTransaction(r -> {
            Session session = r.createObject(Session.class, UUID.randomUUID().toString());
            session.setDate(sessionDate);
            session.setHeure(heure);
            session.setCavalierNom(riderName);
            session.setChevalNom(horseName);

            Horse horse = r.where(Horse.class).equalTo("id", horseId).findFirst();
            if (horse != null) {
                horse.setACoursAujourdhui(true);
                horse.setCavalierNom(riderName);
            }
        });

        loadHorseData(horseId);
        Toast.makeText(this, "Seance programmee avec succes", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
        }
    }
}
