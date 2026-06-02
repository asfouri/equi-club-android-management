package com.example.equi;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;

public class activity_acceuil extends RiderMenuActivity {

    private TextView tvBienvenue;
    private TextView tvTitre;
    private EditText etSearch;
    private Button btnShowDay;
    private Button btnShowRest;
    private RecyclerView rvChevaux;
    private HorseAdapter adapter;
    private List<Horse> listeChevauxAffiches;
    private Realm realm;
    private boolean afficherChevauxDuJour = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil);
        setupMenuToolbar(R.id.toolbar_accueil, "Espace chevalier", false);

        tvBienvenue = findViewById(R.id.tv_bienvenue);
        tvTitre = findViewById(R.id.tv_titre_chevaux);
        etSearch = findViewById(R.id.et_search_horse);
        rvChevaux = findViewById(R.id.rv_chevaux);

        Button btnSearch = findViewById(R.id.btn_search_horse);
        Button btnReset = findViewById(R.id.btn_reset_horse);
        btnShowDay = findViewById(R.id.btn_show_day);
        btnShowRest = findViewById(R.id.btn_show_rest);
        Button btnManageSession = findViewById(R.id.btn_manage_session);
        Button btnContact = findViewById(R.id.btn_contact);
        Button btnAbout = findViewById(R.id.btn_about);
        Button btnLogout = findViewById(R.id.btn_logout);

        realm = Realm.getDefaultInstance();

        String nomUser = getIntent().getStringExtra("USER_NAME");
        tvBienvenue.setText("Espace chevalier : " + (nomUser != null ? nomUser : "Cavalier"));

        btnSearch.setOnClickListener(v -> filtrer(etSearch.getText().toString()));
        btnReset.setOnClickListener(v -> {
            etSearch.setText("");
            chargerListeCourante();
        });
        btnShowDay.setOnClickListener(v -> {
            afficherChevauxDuJour = true;
            chargerChevauxDuJour();
        });
        btnShowRest.setOnClickListener(v -> {
            afficherChevauxDuJour = false;
            chargerChevauxClubRepos();
        });
        btnManageSession.setOnClickListener(v -> proposerGestionSeance());
        btnContact.setOnClickListener(v -> afficherContactsAdministration());
        btnAbout.setOnClickListener(v -> afficherInfosClub());
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        chargerChevauxDuJour();
    }

    private void chargerListeCourante() {
        if (afficherChevauxDuJour) {
            chargerChevauxDuJour();
        } else {
            chargerChevauxClubRepos();
        }
    }

    private void chargerChevauxDuJour() {
        tvTitre.setText("Chevaux du jour");
        updateFilterButtons();
        RealmResults<Horse> results = realm.where(Horse.class)
                .equalTo("aCoursAujourdhui", true)
                .findAll();
        listeChevauxAffiches = new ArrayList<>(results);
        majAdapter(listeChevauxAffiches);
    }

    private void chargerChevauxClubRepos() {
        tvTitre.setText("Chevaux au repos");
        updateFilterButtons();
        RealmResults<Horse> results = realm.where(Horse.class)
                .equalTo("aCoursAujourdhui", false)
                .findAll();
        listeChevauxAffiches = new ArrayList<>(results);
        majAdapter(listeChevauxAffiches);
    }

    private void majAdapter(List<Horse> nouvelleListe) {
        adapter = new HorseAdapter(new ArrayList<>(nouvelleListe), this, true);
        rvChevaux.setLayoutManager(new LinearLayoutManager(this));
        rvChevaux.setAdapter(adapter);
    }

    private void afficherContactsAdministration() {
        new AlertDialog.Builder(this)
                .setTitle("Contact administration")
                .setMessage("Administration : 06 45 78 25 12\nDirection : 06 61 45 42 50")
                .setPositiveButton("Appeler", (d, w) ->
                        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:0645782512"))))
                .setNegativeButton("Fermer", null)
                .show();
    }

    private void proposerGestionSeance() {
        String[] options = {"Reserver une seance", "Annuler ma seance", "Changer d'horaire"};
        new AlertDialog.Builder(this)
                .setTitle("Gerer ma seance")
                .setItems(options, (dialog, which) -> {
                    String choix = options[which];
                    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:admin@etrier.ma"));
                    intent.putExtra(Intent.EXTRA_SUBJECT, choix + " - " + tvBienvenue.getText());
                    intent.putExtra(Intent.EXTRA_TEXT, "Bonjour Administration,\nJe souhaite " + choix.toLowerCase(Locale.ROOT) + " pour ma prochaine seance.");
                    startActivity(Intent.createChooser(intent, "Envoyer a l'administration"));
                })
                .show();
    }

    private void afficherInfosClub() {
        String tarifs = "TARIFS :\nInscription annuelle : 1500 DH\n4 cours : 800 DH\nBalade (1h) : 250 DH\nCours passager : 300 DH";
        new AlertDialog.Builder(this)
                .setTitle("A propos du club")
                .setMessage(tarifs)
                .setPositiveButton("Ouvrir la carte", (d, w) -> {
                    Uri mapUri = Uri.parse("geo:33.5731,-7.5898?q=Club+Equestre");
                    startActivity(new Intent(Intent.ACTION_VIEW, mapUri));
                })
                .setNegativeButton("Fermer", null)
                .show();
    }

    private void filtrer(String texte) {
        ArrayList<Horse> filtre = new ArrayList<>();
        String query = texte.toLowerCase(Locale.ROOT);
        for (Horse h : listeChevauxAffiches) {
            String horseName = h.getName() != null ? h.getName() : "";
            if (horseName.toLowerCase(Locale.ROOT).contains(query)) {
                filtre.add(h);
            }
        }
        adapter.updateList(filtre);
    }

    private void updateFilterButtons() {
        if (afficherChevauxDuJour) {
            btnShowDay.setBackgroundResource(R.drawable.bg_chip_active);
            btnShowDay.setTextColor(ContextCompat.getColor(this, android.R.color.white));
            btnShowRest.setBackgroundResource(R.drawable.bg_chip_inactive);
            btnShowRest.setTextColor(ContextCompat.getColor(this, R.color.theme_text_primary));
        } else {
            btnShowDay.setBackgroundResource(R.drawable.bg_chip_inactive);
            btnShowDay.setTextColor(ContextCompat.getColor(this, R.color.theme_text_primary));
            btnShowRest.setBackgroundResource(R.drawable.bg_chip_active);
            btnShowRest.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        chargerListeCourante();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
        }
    }
}
