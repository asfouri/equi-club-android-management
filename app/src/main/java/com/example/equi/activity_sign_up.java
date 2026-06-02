package com.example.equi;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Locale;

import io.realm.Realm;

public class activity_sign_up extends BaseMenuActivity {

    private EditText etNom;
    private EditText etPrenom;
    private EditText etAge;
    private EditText etEmail;
    private EditText etPass;
    private EditText etTel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setupMenuToolbar(R.id.toolbar_sign_up, "Inscription", true);

        etNom = findViewById(R.id.et_nom);
        etPrenom = findViewById(R.id.et_prenom);
        etAge = findViewById(R.id.et_age);
        etEmail = findViewById(R.id.et_email_sign);
        etPass = findViewById(R.id.et_pass_sign);
        etTel = findViewById(R.id.et_tel);

        Button btnValider = findViewById(R.id.btn_valider);
        btnValider.setOnClickListener(v -> enregistrerUtilisateur());
    }

    private void enregistrerUtilisateur() {
        String nom = etNom.getText().toString().trim();
        String prenom = etPrenom.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String pass = etPass.getText().toString().trim();
        String telephone = etTel.getText().toString().trim();

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir les informations obligatoires", Toast.LENGTH_SHORT).show();
            return;
        }

        String role = "Cavalier";
        if (email.toLowerCase(Locale.ROOT).startsWith("admin")) {
            role = "ADMIN";
        }

        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            User existingUser = realm.where(User.class).equalTo("email", email).findFirst();
            if (existingUser != null) {
                Toast.makeText(this, "Un compte avec cet email existe deja", Toast.LENGTH_SHORT).show();
                return;
            }

            String finalRole = role;

            realm.executeTransaction(r -> {
                User newUser = new User();
                newUser.setNom(nom + " " + prenom);
                newUser.setPrenom(prenom);
                newUser.setEmail(email);
                newUser.setPassword(pass);
                newUser.setTelephone(telephone);
                newUser.setRole(finalRole);
                r.copyToRealmOrUpdate(newUser);
            });

            envoieEmailValidation(email, nom + " " + prenom);
            Toast.makeText(this, "Inscription reussie !", Toast.LENGTH_SHORT).show();

            if (finalRole.equals("ADMIN")) {
                startActivity(new Intent(this, ActivityAdmin.class));
            } else {
                Intent intent = new Intent(this, activity_acceuil.class);
                intent.putExtra("USER_NAME", prenom);
                startActivity(intent);
            }

            finish();
        } catch (Exception e) {
            Toast.makeText(this, "Erreur Base de donnees : " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    private void envoieEmailValidation(String emailUser, String nomComplet) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"alielhemmaaya@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Validation de compte : " + nomComplet);
        intent.putExtra(Intent.EXTRA_TEXT, "Un nouvel utilisateur souhaite s'inscrire.\n\n"
                + "Nom : " + nomComplet + "\n"
                + "Email : " + emailUser + "\n\n"
                + "Veuillez valider son acces sur la plateforme.");
        try {
            startActivity(Intent.createChooser(intent, "Choisir une application email..."));
        } catch (Exception e) {
            Toast.makeText(this, "Aucune application email installee", Toast.LENGTH_SHORT).show();
        }
    }
}
