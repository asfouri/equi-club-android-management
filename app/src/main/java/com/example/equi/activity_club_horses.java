package com.example.equi;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.time.LocalTime;

import io.realm.Realm;
import io.realm.RealmResults;

public class activity_club_horses extends AdminMenuActivity {

    private RecyclerView rvClubHorses;
    private HorseAdapter adapter;
    private List<Horse> allHorses;
    private List<Horse> listeChevaux;
    private EditText etSearch;
    private TextView tvSelectedHorse;
    private TextView tvHorseRegistrySummary;
    private Button btnFilterAll;
    private Button btnFilterReady;
    private Button btnFilterSick;
    private Button btnFilterStopped;
    private Realm realm;
    private String selectedHorseId;
    private String activeFilter = "ALL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_horses);
        setupMenuToolbar(R.id.toolbar_club_horses, "Gestion des chevaux", true);

        rvClubHorses = findViewById(R.id.rv_club_horses);
        etSearch = findViewById(R.id.et_search_club);
        tvSelectedHorse = findViewById(R.id.tv_selected_horse);
        tvHorseRegistrySummary = findViewById(R.id.tv_horse_registry_summary);
        btnFilterAll = findViewById(R.id.btn_filter_horses_all);
        btnFilterReady = findViewById(R.id.btn_filter_horses_ready);
        btnFilterSick = findViewById(R.id.btn_filter_horses_sick);
        btnFilterStopped = findViewById(R.id.btn_filter_horses_stopped);
        Button btnAdd = findViewById(R.id.btn_add_horse_list);
        Button btnUpdate = findViewById(R.id.btn_update_horse_list);
        Button btnDelete = findViewById(R.id.btn_delete_horse_list);

        realm = Realm.getDefaultInstance();
        rvClubHorses.setLayoutManager(new LinearLayoutManager(this));

        btnFilterAll.setOnClickListener(v -> setActiveFilter("ALL"));
        btnFilterReady.setOnClickListener(v -> setActiveFilter("READY"));
        btnFilterSick.setOnClickListener(v -> setActiveFilter("SICK"));
        btnFilterStopped.setOnClickListener(v -> setActiveFilter("STOPPED"));
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                applyHorseFilters();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btnAdd.setOnClickListener(v -> startActivity(new Intent(this, HorseDetailActivity.class)));
        btnUpdate.setOnClickListener(v -> openSelectedHorse());
        btnDelete.setOnClickListener(v -> confirmDeleteHorse());

        updateSelectedHorseLabel(null);
    }

    private void loadHorses() {
        RealmResults<Horse> results = realm.where(Horse.class).findAll();
        allHorses = new ArrayList<>(results);
        applyHorseFilters();
    }

    private void applyHorseFilters() {
        if (allHorses == null) {
            return;
        }
        listeChevaux = new ArrayList<>();
        String query = etSearch.getText().toString().trim().toLowerCase(Locale.ROOT);
        for (Horse horse : allHorses) {
            if (!matchesActiveFilter(horse) || !matchesSearch(horse, query)) {
                continue;
            }
            listeChevaux.add(horse);
        }

        adapter = new HorseAdapter(listeChevaux, this, horse -> {
            selectedHorseId = horse.getId();
            updateSelectedHorseLabel(horse);
        });
        rvClubHorses.setAdapter(adapter);
        updateFilterButtons();
        updateRegistrySummary();
        syncSelectionWithList();
    }

    private void setActiveFilter(String filter) {
        activeFilter = filter;
        applyHorseFilters();
    }

    private boolean matchesActiveFilter(Horse horse) {
        int currentHour = LocalTime.now().getHour();
        if ("READY".equals(activeFilter)) {
            return !horse.isSick() && !horse.isStopped(currentHour);
        }
        if ("SICK".equals(activeFilter)) {
            return horse.isSick();
        }
        if ("STOPPED".equals(activeFilter)) {
            return horse.isStopped(currentHour);
        }
        return true;
    }

    private boolean matchesSearch(Horse horse, String query) {
        if (query.isEmpty()) {
            return true;
        }
        String haystack = safe(horse.getName()) + " "
                + safe(horse.getRace()) + " "
                + safe(horse.getCavalierNom()) + " "
                + safe(horse.getElevage()) + " "
                + safe(horse.getEtatSante());
        return haystack.toLowerCase(Locale.ROOT).contains(query);
    }

    private void updateFilterButtons() {
        styleFilterButton(btnFilterAll, "ALL".equals(activeFilter));
        styleFilterButton(btnFilterReady, "READY".equals(activeFilter));
        styleFilterButton(btnFilterSick, "SICK".equals(activeFilter));
        styleFilterButton(btnFilterStopped, "STOPPED".equals(activeFilter));
    }

    private void styleFilterButton(Button button, boolean active) {
        button.setBackgroundResource(active ? R.drawable.bg_button_primary : R.drawable.bg_button_secondary);
        button.setTextColor(getResources().getColor(active ? android.R.color.white : R.color.theme_text_primary));
    }

    private void updateRegistrySummary() {
        int sick = 0;
        int stopped = 0;
        int currentHour = LocalTime.now().getHour();
        for (Horse horse : allHorses) {
            if (horse.isSick()) {
                sick++;
            } else if (horse.isStopped(currentHour)) {
                stopped++;
            }
        }
        tvHorseRegistrySummary.setText(listeChevaux.size() + " affiches sur " + allHorses.size()
                + " | " + sick + " malade(s) | " + stopped + " arrete(s)");
    }

    private void openSelectedHorse() {
        if (selectedHorseId == null) {
            Toast.makeText(this, "Selectionnez un cheval pour modifier", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, HorseDetailActivity.class);
        intent.putExtra("HORSE_ID", selectedHorseId);
        startActivity(intent);
    }

    private void confirmDeleteHorse() {
        if (selectedHorseId == null) {
            Toast.makeText(this, "Selectionnez un cheval pour supprimer", Toast.LENGTH_SHORT).show();
            return;
        }

        Horse selectedHorse = realm.where(Horse.class).equalTo("id", selectedHorseId).findFirst();
        if (selectedHorse == null) {
            selectedHorseId = null;
            updateSelectedHorseLabel(null);
            Toast.makeText(this, "Cheval introuvable", Toast.LENGTH_SHORT).show();
            return;
        }

        String horseName = selectedHorse.getName();
        new AlertDialog.Builder(this)
                .setTitle("Supprimer le cheval")
                .setMessage("Voulez-vous supprimer " + horseName + " ?")
                .setPositiveButton("Supprimer", (dialog, which) -> {
                    realm.executeTransaction(r -> {
                        Horse horseToDelete = r.where(Horse.class).equalTo("id", selectedHorseId).findFirst();
                        if (horseToDelete != null) {
                            horseToDelete.deleteFromRealm();
                        }
                    });
                    selectedHorseId = null;
                    updateSelectedHorseLabel(null);
                    Toast.makeText(this, "Cheval supprime", Toast.LENGTH_SHORT).show();
                    loadHorses();
                })
                .setNegativeButton("Annuler", null)
                .show();
    }

    private void syncSelectionWithList() {
        if (selectedHorseId == null) {
            updateSelectedHorseLabel(null);
            return;
        }

        for (Horse horse : listeChevaux) {
            if (selectedHorseId.equals(horse.getId())) {
                adapter.setSelectedHorseId(selectedHorseId);
                updateSelectedHorseLabel(horse);
                return;
            }
        }

        selectedHorseId = null;
        adapter.setSelectedHorseId(null);
        updateSelectedHorseLabel(null);
    }

    private void updateSelectedHorseLabel(Horse horse) {
        if (horse == null) {
            tvSelectedHorse.setText("Touchez un cheval pour le selectionner, puis utilisez Modifier ou Retirer.");
        } else {
            tvSelectedHorse.setText("Selection : " + horse.getName() + " | "
                    + horse.getCurrentState(LocalTime.now().getHour()) + " | Cavalier : "
                    + safe(horse.getCavalierNom()));
        }
    }

    private String safe(String value) {
        return value == null ? "" : value.trim();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadHorses();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
        }
    }
}
