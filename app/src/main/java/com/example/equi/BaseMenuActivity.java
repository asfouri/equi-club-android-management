package com.example.equi;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public abstract class BaseMenuActivity extends AppCompatActivity {

    protected void setupMenuToolbar(int toolbarId, String title, boolean showBack) {
        Toolbar toolbar = findViewById(toolbarId);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(showBack);
        }
    }

    protected String resolveUserName() {
        String userName = getIntent().getStringExtra("USER_NAME");
        return userName != null ? userName : "Utilisateur";
    }

    protected int getMenuResourceId() {
        return 0;
    }

    protected boolean handleMenuItemSelection(int itemId) {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int menuResourceId = getMenuResourceId();
        if (menuResourceId == 0) {
            return false;
        }
        getMenuInflater().inflate(menuResourceId, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        } else if (handleMenuItemSelection(id)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
