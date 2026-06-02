package com.example.equi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;

public class LoginActivity extends BaseMenuActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnTestAdmin;
    private Button btnTestRider;
    private Button btnShare;
    private TextView tvCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupMenuToolbar(R.id.toolbar_login, "Connexion", false);

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        tvCreateAccount = findViewById(R.id.tv_create_account);
        btnShare = findViewById(R.id.btn_share);
        btnTestAdmin = findViewById(R.id.btn_test_admin);
        btnTestRider = findViewById(R.id.btn_test_rider);

        btnShare.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Rejoins le club l'Etrier !");
            startActivity(Intent.createChooser(shareIntent, "Partager"));
        });

        tvCreateAccount.setOnClickListener(v ->
                startActivity(new Intent(this, activity_sign_up.class)));

        btnLogin.setOnClickListener(v -> performLogin(
                etEmail.getText().toString().trim(),
                etPassword.getText().toString().trim()));

        btnTestAdmin.setOnClickListener(v -> {
            Intent intent = new Intent(this, ActivityAdmin.class);
            intent.putExtra("USER_NAME", "Prof (Admin Mode)");
            startActivity(intent);
        });

        btnTestRider.setOnClickListener(v -> {
            Intent intent = new Intent(this, activity_acceuil.class);
            intent.putExtra("USER_NAME", "Cavalier Demo");
            startActivity(intent);
        });
    }

    private void performLogin(String emailSaisi, String passSaisi) {
        if (emailSaisi.isEmpty() || passSaisi.isEmpty()) {
            Toast.makeText(this, "Champs vides !", Toast.LENGTH_SHORT).show();
            return;
        }

        Realm realm = Realm.getDefaultInstance();
        try {
            RealmResults<User> results = realm.where(User.class)
                    .equalTo("email", emailSaisi)
                    .findAll();

            if (!results.isEmpty()) {
                User user = results.get(0);
                if (user.getPassword().equals(passSaisi)) {
                    Intent intent;
                    String role = user.getRole();

                    if (role != null && role.equalsIgnoreCase("ADMIN")) {
                        intent = new Intent(this, ActivityAdmin.class);
                    } else {
                        intent = new Intent(this, activity_acceuil.class);
                    }

                    intent.putExtra("USER_NAME", user.getNom());
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Mauvais mot de passe", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Utilisateur inconnu", Toast.LENGTH_SHORT).show();
            }
        } finally {
            realm.close();
        }
    }
}
