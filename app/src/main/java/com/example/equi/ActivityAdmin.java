package com.example.equi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;

public class ActivityAdmin extends AdminMenuActivity {

    private TextView tvBienvenue;
    private TextView tvTotalSeances;
    private TextView tvSeancesTerminees;
    private TextView tvSeancesPrevues;
    private TextView tvChevauxMaladesCount;
    private TextView tvAlerteDetail;
    private TextView tvChevalMaladeNom;
    private TextView tvChevalMaladeEtat;
    private TextView tvChevalMaladeRace;
    private TextView tvChevauxArretesCount;
    private TextView tvResumeJours;
    private TextView tvNextSession;
    private TextView tvOccupancyRate;
    private TextView tvBusiestHorse;
    private TextView tvConflictsCount;
    private ImageView imgChevalMalade;
    private LinearLayout layoutMaladePreview;
    private ProgressBar progressSeancesTerminees;
    private ProgressBar progressSeancesPrevues;
    private LineChart lineChart;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        setupMenuToolbar(R.id.toolbar_admin, "Tableau de bord admin", false);

        tvBienvenue = findViewById(R.id.tv_bienvenue);
        tvTotalSeances = findViewById(R.id.tv_total_seances);
        tvSeancesTerminees = findViewById(R.id.tv_seances_terminees);
        tvSeancesPrevues = findViewById(R.id.tv_seances_prevues);
        tvChevauxMaladesCount = findViewById(R.id.tv_chevaux_malades_count);
        tvAlerteDetail = findViewById(R.id.tv_alerte_detail);
        tvChevalMaladeNom = findViewById(R.id.tv_cheval_malade_nom);
        tvChevalMaladeEtat = findViewById(R.id.tv_cheval_malade_etat);
        tvChevalMaladeRace = findViewById(R.id.tv_cheval_malade_race);
        tvChevauxArretesCount = findViewById(R.id.tv_chevaux_arretes_count);
        tvResumeJours = findViewById(R.id.tv_resume_jours);
        tvNextSession = findViewById(R.id.tv_next_session);
        tvOccupancyRate = findViewById(R.id.tv_occupancy_rate);
        tvBusiestHorse = findViewById(R.id.tv_busiest_horse);
        tvConflictsCount = findViewById(R.id.tv_conflicts_count);
        imgChevalMalade = findViewById(R.id.img_cheval_malade);
        layoutMaladePreview = findViewById(R.id.layout_malade_preview);
        progressSeancesTerminees = findViewById(R.id.progress_seances_terminees);
        progressSeancesPrevues = findViewById(R.id.progress_seances_prevues);
        lineChart = findViewById(R.id.lineChart);
        Button btnVoirMalades = findViewById(R.id.btn_voir_details_malades);
        Button btnRefresh = findViewById(R.id.btn_refresh_admin);
        Button btnQuickSessions = findViewById(R.id.btn_quick_sessions);
        Button btnQuickHorses = findViewById(R.id.btn_quick_horses);

        realm = Realm.getDefaultInstance();

        String nomUser = getIntent().getStringExtra("USER_NAME");
        tvBienvenue.setText("Bonjour " + (nomUser != null ? nomUser : "Admin"));

        setupChart();

        btnVoirMalades.setOnClickListener(v -> startActivity(new Intent(this, activity_club_horses.class)));
        btnRefresh.setOnClickListener(v -> actualiserDashboard());
        btnQuickSessions.setOnClickListener(v -> startActivity(new Intent(this, ManageSessionsActivity.class)));
        btnQuickHorses.setOnClickListener(v -> startActivity(new Intent(this, activity_club_horses.class)));

        actualiserDashboard();
    }

    private void setupChart() {
        lineChart.getDescription().setEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.getAxisRight().setEnabled(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]{
                "Lun", "Mar", "Mer", "Jeu", "Ven", "Sam", "Dim"
        }));
        xAxis.setTextColor(Color.parseColor("#66736F"));
        lineChart.getAxisLeft().setTextColor(Color.parseColor("#66736F"));
        lineChart.getLegend().setTextColor(Color.parseColor("#66736F"));
    }

    private void actualiserDashboard() {
        RealmResults<Session> sessions = realm.where(Session.class).findAll();
        RealmResults<Horse> horses = realm.where(Horse.class).findAll();
        int totalSeances = sessions.size();
        int seancesTerminees = countCompletedSessions(sessions);
        int seancesPrevues = Math.max(0, totalSeances - seancesTerminees);

        tvTotalSeances.setText(String.valueOf(totalSeances));
        tvSeancesTerminees.setText("Terminees : " + seancesTerminees);
        tvSeancesPrevues.setText("Prevues : " + seancesPrevues);
        tvNextSession.setText(resolveNextSessionLabel(sessions));
        tvBusiestHorse.setText(resolveTopName(sessions, true));
        tvConflictsCount.setText(String.valueOf(countSchedulingConflicts(sessions)));
        tvOccupancyRate.setText(resolveOccupancyRate(sessions, horses));

        progressSeancesTerminees.setProgress(totalSeances == 0 ? 0 : (seancesTerminees * 100) / totalSeances);
        progressSeancesPrevues.setProgress(totalSeances == 0 ? 0 : (seancesPrevues * 100) / totalSeances);

        RealmResults<Horse> malades = realm.where(Horse.class)
                .equalTo("etatSante", Horse.STATE_SICK)
                .findAll();
        tvChevauxMaladesCount.setText("Malades : " + malades.size());
        int chevauxArretes = countStoppedHorses();
        tvChevauxArretesCount.setText("Arretes : " + chevauxArretes);
        bindMaladePreview(malades);

        String[] jours = {"Lun", "Mar", "Mer", "Jeu", "Ven", "Sam", "Dim"};
        int[] repartition = new int[jours.length];
        for (Session session : sessions) {
            java.time.LocalDate date = SessionDateUtils.parseDate(session.getDate());
            if (date != null) {
                repartition[date.getDayOfWeek().getValue() - 1]++;
            }
        }
        StringBuilder resume = new StringBuilder("Semaine : ");
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < jours.length; i++) {
            int valeur = repartition[i];
            entries.add(new Entry(i, valeur));
            resume.append(jours[i]).append("=").append(valeur);
            if (i < jours.length - 1) {
                resume.append(" | ");
            }
        }
        tvResumeJours.setText(resume.toString());

        LineDataSet dataSet = new LineDataSet(entries, "Volume");
        dataSet.setColor(Color.parseColor("#184A45"));
        dataSet.setCircleColor(Color.parseColor("#B55F3A"));
        dataSet.setCircleRadius(5f);
        dataSet.setLineWidth(2.5f);
        dataSet.setValueTextColor(Color.parseColor("#21302D"));
        lineChart.setData(new LineData(dataSet));
        lineChart.invalidate();
    }

    private String resolveNextSessionLabel(List<Session> sessions) {
        LocalDateTime now = LocalDateTime.now();
        Session next = null;
        LocalDateTime nextDateTime = null;
        for (Session session : sessions) {
            LocalDateTime candidate = SessionDateUtils.parseDateTime(session);
            if (candidate == null || candidate.isBefore(now)) {
                continue;
            }
            if (nextDateTime == null || candidate.isBefore(nextDateTime)) {
                next = session;
                nextDateTime = candidate;
            }
        }
        if (next == null || nextDateTime == null) {
            return "Prochaine seance : aucune seance a venir";
        }
        return "Prochaine seance : " + next.getHeure() + " - " + safe(next.getCavalierNom())
                + " avec " + safe(next.getChevalNom());
    }

    private String resolveTopName(List<Session> sessions, boolean byHorse) {
        Map<String, Integer> counts = new HashMap<>();
        for (Session session : sessions) {
            String name = byHorse ? session.getChevalNom() : session.getCavalierNom();
            if (name == null || name.trim().isEmpty()) {
                continue;
            }
            counts.put(name, counts.getOrDefault(name, 0) + 1);
        }

        String topName = "-";
        int topCount = 0;
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            if (entry.getValue() > topCount) {
                topName = entry.getKey();
                topCount = entry.getValue();
            }
        }
        return topCount == 0 ? "-" : topName + " (" + topCount + ")";
    }

    private int countSchedulingConflicts(List<Session> sessions) {
        Map<String, Integer> horseSlots = new HashMap<>();
        Map<String, Integer> riderSlots = new HashMap<>();
        for (Session session : sessions) {
            String slot = safe(session.getDate()) + "|" + safe(session.getHeure());
            String horseKey = slot + "|horse|" + safe(session.getChevalNom()).toLowerCase(Locale.ROOT);
            String riderKey = slot + "|rider|" + safe(session.getCavalierNom()).toLowerCase(Locale.ROOT);
            horseSlots.put(horseKey, horseSlots.getOrDefault(horseKey, 0) + 1);
            riderSlots.put(riderKey, riderSlots.getOrDefault(riderKey, 0) + 1);
        }

        int conflicts = 0;
        for (Integer count : horseSlots.values()) {
            if (count > 1) {
                conflicts += count - 1;
            }
        }
        for (Integer count : riderSlots.values()) {
            if (count > 1) {
                conflicts += count - 1;
            }
        }
        return conflicts;
    }

    private String resolveOccupancyRate(List<Session> sessions, List<Horse> horses) {
        if (horses == null || horses.isEmpty()) {
            return "0%";
        }
        int horsesWithSessions = 0;
        for (Horse horse : horses) {
            if (horse == null || horse.getName() == null) {
                continue;
            }
            for (Session session : sessions) {
                if (horse.getName().equalsIgnoreCase(safe(session.getChevalNom()))) {
                    horsesWithSessions++;
                    break;
                }
            }
        }
        return ((horsesWithSessions * 100) / horses.size()) + "%";
    }

    private String safe(String value) {
        return value == null ? "" : value.trim();
    }

    private int countCompletedSessions(List<Session> sessions) {
        LocalDateTime now = LocalDateTime.now();
        int count = 0;
        for (Session session : sessions) {
            if (SessionDateUtils.isCompleted(session, now)) {
                count++;
            }
        }
        return count;
    }

    private void bindMaladePreview(RealmResults<Horse> malades) {
        if (malades == null || malades.isEmpty()) {
            layoutMaladePreview.setVisibility(android.view.View.GONE);
            tvAlerteDetail.setText("Aucun cheval malade pour le moment.");
            return;
        }

        Horse firstSickHorse = malades.first();
        if (firstSickHorse == null) {
            layoutMaladePreview.setVisibility(android.view.View.GONE);
            tvAlerteDetail.setText("Aucun cheval malade pour le moment.");
            return;
        }

        layoutMaladePreview.setVisibility(android.view.View.VISIBLE);
        tvChevalMaladeNom.setText(firstSickHorse.getName());
        tvChevalMaladeEtat.setText("Etat : " + firstSickHorse.getCurrentState(LocalTime.now().getHour()));
        tvChevalMaladeRace.setText("Race : " + firstSickHorse.getRace());
        tvAlerteDetail.setText("Cheval a surveiller : " + firstSickHorse.getName());

        imgChevalMalade.setImageResource(DrawableResolver.resolveHorseImage(firstSickHorse.getPhotoUrl()));
    }

    private int countStoppedHorses() {
        RealmResults<Horse> horses = realm.where(Horse.class).findAll();
        int currentHour = LocalTime.now().getHour();
        int count = 0;
        for (Horse horse : horses) {
            if (horse.isStopped(currentHour)) {
                count++;
            }
        }
        return count;
    }

    @Override
    protected void onResume() {
        super.onResume();
        actualiserDashboard();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
        }
    }
}
