package com.example.equi;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import io.realm.Realm;

public class AddUserActivity extends AdminMenuActivity {

    private EditText etNom;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etPhone;
    private EditText etDiplome;
    private TextView tvTitle;
    private String userRole;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        realm = Realm.getDefaultInstance();

        userRole = getIntent().getStringExtra("ROLE_TO_ADD");
        if (userRole == null) {
            userRole = "Cavalier";
        }

        setupMenuToolbar(
                R.id.toolbar_add_user,
                "Nouveau Cavalier",
                true
        );

        tvTitle = findViewById(R.id.tv_title_add_user);
        etNom = findViewById(R.id.et_add_user_nom);
        etEmail = findViewById(R.id.et_add_user_email);
        etPassword = findViewById(R.id.et_add_user_password);
        etPhone = findViewById(R.id.et_add_user_phone);
        etDiplome = findViewById(R.id.et_add_user_diplome);
        Button btnSave = findViewById(R.id.btn_save_new_user);
        Button btnCancel = findViewById(R.id.btn_cancel_add_user);

        tvTitle.setText("Ajouter un Cavalier");

        btnSave.setOnClickListener(v -> saveUser());
        btnCancel.setOnClickListener(v -> finish());
    }

    private void saveUser() {
        String nom = etNom.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String pass = etPassword.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String diplome = etDiplome.getText().toString().trim();

        if (nom.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir les champs obligatoires", Toast.LENGTH_SHORT).show();
            return;
        }

        User existingUser = realm.where(User.class).equalTo("email", email).findFirst();
        if (existingUser != null) {
            Toast.makeText(this, "Un utilisateur avec cet email existe deja", Toast.LENGTH_SHORT).show();
            return;
        }

        realm.executeTransaction(r -> {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setNom(nom);
            newUser.setPassword(pass);
            newUser.setTelephone(phone);
            newUser.setNiveauDiplome(diplome);
            newUser.setRole("Cavalier");
            r.copyToRealmOrUpdate(newUser);
        });

        Toast.makeText(this, "Cavalier ajoute avec succes !", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
        }
    }
}
