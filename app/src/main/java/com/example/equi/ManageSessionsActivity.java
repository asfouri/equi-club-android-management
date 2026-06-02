package com.example.equi;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import io.realm.Realm;
import io.realm.RealmResults;

public class ManageSessionsActivity extends AdminMenuActivity implements SessionAdapter.OnSessionActionListener {

    private RecyclerView rvSessions;
    private SessionAdapter adapter;
    private List<Session> allSessions;
    private List<Session> sessionList;
    private Realm realm;
    private EditText etSearch;
    private TextView tvSessionSummary;
    private TextView tvEmptySessions;
    private Button btnFilterAll;
    private Button btnFilterToday;
    private Button btnFilterWeek;
    private Button btnFilterAlerts;
    private String activeFilter = "ALL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_sessions);
        setupMenuToolbar(R.id.toolbar_sessions, "Planning des seances", true);

        rvSessions = findViewById(R.id.rv_sessions);
        etSearch = findViewById(R.id.et_search_session);
        tvSessionSummary = findViewById(R.id.tv_session_summary);
        tvEmptySessions = findViewById(R.id.tv_empty_sessions);
        Button btnAdd = findViewById(R.id.btn_add_session);
        btnFilterAll = findViewById(R.id.btn_filter_all_sessions);
        btnFilterToday = findViewById(R.id.btn_filter_today_sessions);
        btnFilterWeek = findViewById(R.id.btn_filter_week_sessions);
        btnFilterAlerts = findViewById(R.id.btn_filter_alert_sessions);

        realm = Realm.getDefaultInstance();
        rvSessions.setLayoutManager(new LinearLayoutManager(this));
        loadSessions();

        btnAdd.setOnClickListener(v -> showSessionDialog(null));
        btnFilterAll.setOnClickListener(v -> setActiveFilter("ALL"));
        btnFilterToday.setOnClickListener(v -> setActiveFilter("TODAY"));
        btnFilterWeek.setOnClickListener(v -> setActiveFilter("WEEK"));
        btnFilterAlerts.setOnClickListener(v -> setActiveFilter("ALERTS"));
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                applyFilters();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void loadSessions() {
        RealmResults<Session> results = realm.where(Session.class).findAll();
        allSessions = new ArrayList<>(results);
        allSessions.sort(Comparator
                .comparing((Session session) -> session.getDate() != null ? session.getDate() : "9999-12-31")
                .thenComparing(session -> session.getHeure() != null ? session.getHeure() : "99:99"));
        applyFilters();
    }

    private void applyFilters() {
        if (allSessions == null) {
            return;
        }
        String query = etSearch.getText().toString().trim().toLowerCase(Locale.ROOT);
        sessionList = new ArrayList<>();
        for (Session session : allSessions) {
            if (!matchesActiveFilter(session) || !matchesSearch(session, query)) {
                continue;
            }
            sessionList.add(session);
        }

        updateFilterButtons();
        updateSummary();
        tvEmptySessions.setVisibility(sessionList.isEmpty() ? View.VISIBLE : View.GONE);
        rvSessions.setVisibility(sessionList.isEmpty() ? View.GONE : View.VISIBLE);
        adapter = new SessionAdapter(sessionList, this);
        rvSessions.setAdapter(adapter);
    }

    private void setActiveFilter(String filter) {
        activeFilter = filter;
        applyFilters();
    }

    private boolean matchesSearch(Session session, String query) {
        if (query.isEmpty()) {
            return true;
        }
        String haystack = safe(session.getChevalNom()) + " "
                + safe(session.getCavalierNom()) + " "
                + safe(session.getDate()) + " "
                + SessionDateUtils.formatDateForDisplay(session.getDate()) + " "
                + safe(session.getHeure());
        return haystack.toLowerCase(Locale.ROOT).contains(query);
    }

    private boolean matchesActiveFilter(Session session) {
        if ("TODAY".equals(activeFilter)) {
            LocalDate date = SessionDateUtils.parseDate(session.getDate());
            return date != null && date.equals(LocalDate.now());
        }
        if ("WEEK".equals(activeFilter)) {
            LocalDate date = SessionDateUtils.parseDate(session.getDate());
            if (date == null) {
                return false;
            }
            LocalDate monday = LocalDate.now().with(DayOfWeek.MONDAY);
            LocalDate sunday = monday.plusDays(6);
            return !date.isBefore(monday) && !date.isAfter(sunday);
        }
        if ("ALERTS".equals(activeFilter)) {
            return hasSessionAlert(session);
        }
        return true;
    }

    private void updateFilterButtons() {
        styleFilterButton(btnFilterAll, "ALL".equals(activeFilter));
        styleFilterButton(btnFilterToday, "TODAY".equals(activeFilter));
        styleFilterButton(btnFilterWeek, "WEEK".equals(activeFilter));
        styleFilterButton(btnFilterAlerts, "ALERTS".equals(activeFilter));
    }

    private void styleFilterButton(Button button, boolean active) {
        button.setBackgroundResource(active ? R.drawable.bg_button_primary : R.drawable.bg_button_secondary);
        button.setTextColor(getResources().getColor(active ? android.R.color.white : R.color.theme_text_primary));
    }

    private void updateSummary() {
        int completed = 0;
        for (Session session : allSessions) {
            if (SessionDateUtils.isCompleted(session, LocalDateTime.now())) {
                completed++;
            }
        }
        tvSessionSummary.setText(sessionList.size() + " affichees sur " + allSessions.size()
                + " | " + completed + " terminees | " + countSchedulingConflicts() + " conflit(s)");
    }

    private void showSessionDialog(Session sessionToEdit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_session, null);

        EditText etDate = view.findViewById(R.id.et_date);
        EditText etHeure = view.findViewById(R.id.et_heure);
        EditText etCavalier = view.findViewById(R.id.et_cavalier);
        EditText etCheval = view.findViewById(R.id.et_cheval);

        if (sessionToEdit != null) {
            builder.setTitle("Modifier seance");
            etDate.setText(sessionToEdit.getDate());
            etHeure.setText(sessionToEdit.getHeure());
            etCavalier.setText(sessionToEdit.getCavalierNom());
            etCheval.setText(sessionToEdit.getChevalNom());
        } else {
            builder.setTitle("Ajouter seance");
            etDate.setText(SessionDateUtils.getSuggestedFakeDate((int) realm.where(Session.class).count()));
        }

        builder.setView(view);
        builder.setPositiveButton("Valider", null);
        builder.setNegativeButton("Annuler", null);

        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(dialogInterface -> dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String date = etDate.getText().toString().trim();
            String heure = etHeure.getText().toString().trim();
            String cavalier = etCavalier.getText().toString().trim();
            String cheval = etCheval.getText().toString().trim();

            if (date.isEmpty() || heure.isEmpty() || cavalier.isEmpty() || cheval.isEmpty()) {
                Toast.makeText(this, "Tous les champs sont obligatoires", Toast.LENGTH_SHORT).show();
                return;
            }
            if (SessionDateUtils.parseDate(date) == null) {
                Toast.makeText(this, "Date invalide. Format attendu : AAAA-MM-JJ", Toast.LENGTH_SHORT).show();
                return;
            }
            if (SessionDateUtils.parseTime(heure) == null) {
                Toast.makeText(this, "Heure invalide. Format attendu : HH:MM", Toast.LENGTH_SHORT).show();
                return;
            }

            Session conflictingSession = realm.where(Session.class)
                    .equalTo("date", date)
                    .equalTo("heure", heure)
                    .equalTo("chevalNom", cheval)
                    .findFirst();
            boolean isConflict = conflictingSession != null
                    && (sessionToEdit == null || !conflictingSession.getId().equals(sessionToEdit.getId()));
            if (isConflict) {
                Toast.makeText(this, "Ce cheval a deja une seance a cette heure", Toast.LENGTH_SHORT).show();
                return;
            }

            Session conflictingRider = realm.where(Session.class)
                    .equalTo("date", date)
                    .equalTo("heure", heure)
                    .equalTo("cavalierNom", cavalier)
                    .findFirst();
            boolean isRiderConflict = conflictingRider != null
                    && (sessionToEdit == null || !conflictingRider.getId().equals(sessionToEdit.getId()));
            if (isRiderConflict) {
                Toast.makeText(this, "Ce cavalier a deja une seance a cette heure", Toast.LENGTH_SHORT).show();
                return;
            }

            Horse selectedHorse = realm.where(Horse.class).equalTo("name", cheval).findFirst();
            if (selectedHorse != null && selectedHorse.isSick()) {
                Toast.makeText(this, "Attention : ce cheval est marque malade", Toast.LENGTH_LONG).show();
            }

            realm.executeTransaction(r -> {
                Session session = sessionToEdit != null
                        ? r.where(Session.class).equalTo("id", sessionToEdit.getId()).findFirst()
                        : r.createObject(Session.class, java.util.UUID.randomUUID().toString());
                if (session != null) {
                    session.setDate(date);
                    session.setHeure(heure);
                    session.setCavalierNom(cavalier);
                    session.setChevalNom(cheval);
                }
            });
            loadSessions();
            dialog.dismiss();
        }));
        dialog.show();
    }

    @Override
    public void onEdit(Session session) {
        showSessionDialog(session);
    }

    @Override
    public void onDelete(Session session) {
        new AlertDialog.Builder(this)
                .setTitle("Supprimer la seance")
                .setMessage("Confirmer la suppression de cette seance ?")
                .setPositiveButton("Supprimer", (dialog, which) -> {
                    realm.executeTransaction(r -> {
                        Session current = r.where(Session.class).equalTo("id", session.getId()).findFirst();
                        if (current != null) {
                            current.deleteFromRealm();
                        }
                    });
                    loadSessions();
                })
                .setNegativeButton("Annuler", null)
                .show();
    }

    private boolean hasSessionAlert(Session session) {
        Horse horse = realm.where(Horse.class).equalTo("name", safe(session.getChevalNom())).findFirst();
        if (horse != null && horse.isSick()) {
            return true;
        }
        return hasDuplicateSlot(session, "chevalNom") || hasDuplicateSlot(session, "cavalierNom");
    }

    private boolean hasDuplicateSlot(Session session, String fieldName) {
        String participant = "chevalNom".equals(fieldName) ? session.getChevalNom() : session.getCavalierNom();
        RealmResults<Session> duplicates = realm.where(Session.class)
                .equalTo("date", session.getDate())
                .equalTo("heure", session.getHeure())
                .equalTo(fieldName, participant)
                .findAll();
        return duplicates.size() > 1;
    }

    private int countSchedulingConflicts() {
        int conflicts = 0;
        for (Session session : allSessions) {
            if (hasDuplicateSlot(session, "chevalNom")) {
                conflicts++;
            }
            if (hasDuplicateSlot(session, "cavalierNom")) {
                conflicts++;
            }
        }
        return conflicts / 2;
    }

    private String safe(String value) {
        return value == null ? "" : value.trim();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
        }
    }
}
