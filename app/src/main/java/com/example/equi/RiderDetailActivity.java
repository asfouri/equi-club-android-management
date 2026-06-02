package com.example.equi;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import io.realm.Realm;

public class RiderDetailActivity extends AdminMenuActivity {

    private EditText etName;
    private EditText etPhone;
    private EditText etDiplome;
    private TextView tvEmail;
    private Realm realm;
    private String riderEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_detail);
        setupMenuToolbar(R.id.toolbar_rider_detail, "Informations cavalier", true);

        etName = findViewById(R.id.et_detail_rider_name);
        tvEmail = findViewById(R.id.tv_detail_rider_email);
        etPhone = findViewById(R.id.et_detail_rider_phone);
        etDiplome = findViewById(R.id.et_detail_rider_diplome);
        Button btnUpdate = findViewById(R.id.btn_update_rider);
        Button btnDelete = findViewById(R.id.btn_delete_rider);
        Button btnBack = findViewById(R.id.btn_back_riders);

        realm = Realm.getDefaultInstance();

        riderEmail = getIntent().getStringExtra("RIDER_EMAIL");
        if (riderEmail != null) {
            loadRiderData();
        }

        btnUpdate.setOnClickListener(v -> updateRider());
        btnDelete.setOnClickListener(v -> deleteRider());
        btnBack.setOnClickListener(v -> finish());
    }

    private void loadRiderData() {
        User rider = realm.where(User.class).equalTo("email", riderEmail).findFirst();
        if (rider != null) {
            etName.setText(rider.getNom());
            tvEmail.setText(rider.getEmail());
            etPhone.setText(rider.getTelephone() != null ? rider.getTelephone() : "");
            etDiplome.setText(rider.getNiveauDiplome() != null ? rider.getNiveauDiplome() : "");
        }
    }

    private void updateRider() {
        if (riderEmail == null) {
            return;
        }

        String updatedName = etName.getText().toString().trim();
        if (updatedName.isEmpty()) {
            Toast.makeText(this, "Le nom du cavalier est obligatoire", Toast.LENGTH_SHORT).show();
            return;
        }

        realm.executeTransaction(r -> {
            User rider = r.where(User.class).equalTo("email", riderEmail).findFirst();
            if (rider != null) {
                rider.setNom(updatedName);
                rider.setTelephone(etPhone.getText().toString().trim());
                rider.setNiveauDiplome(etDiplome.getText().toString().trim());
            }
        });

        Toast.makeText(this, "Cavalier mis a jour avec succes", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void deleteRider() {
        if (riderEmail == null) {
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("Supprimer le cavalier")
                .setMessage("Confirmer la suppression de cette fiche cavalier ?")
                .setPositiveButton("Supprimer", (dialog, which) -> {
                    realm.executeTransaction(r -> {
                        User rider = r.where(User.class).equalTo("email", riderEmail).findFirst();
                        if (rider != null) {
                            rider.deleteFromRealm();
                        }
                    });

                    Toast.makeText(this, "Cavalier supprime", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("Annuler", null)
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
        }
    }
}
