package com.example.equi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmResults;

public class RiderListActivity extends AdminMenuActivity {

    private RecyclerView rvRiders;
    private TextView tvEmpty;
    private RiderAdapter adapter;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_list);
        setupMenuToolbar(R.id.toolbar_riders, "Gestion des cavaliers", true);

        rvRiders = findViewById(R.id.rv_riders);
        tvEmpty = findViewById(R.id.tv_empty_riders);
        Button btnAddRider = findViewById(R.id.btn_add_rider_space);
        realm = Realm.getDefaultInstance();

        rvRiders.setLayoutManager(new LinearLayoutManager(this));
        btnAddRider.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddUserActivity.class);
            intent.putExtra("ROLE_TO_ADD", "Cavalier");
            startActivity(intent);
        });
    }

    private void loadRiders() {
        RealmResults<User> results = realm.where(User.class)
                .equalTo("role", "Cavalier")
                .findAll();

        List<User> riderList = new ArrayList<>(results);
        adapter = new RiderAdapter(riderList);
        rvRiders.setAdapter(adapter);

        boolean hasRiders = !riderList.isEmpty();
        rvRiders.setVisibility(hasRiders ? View.VISIBLE : View.GONE);
        tvEmpty.setVisibility(hasRiders ? View.GONE : View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRiders();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
