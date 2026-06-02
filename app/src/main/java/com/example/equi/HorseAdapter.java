package com.example.equi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalTime;
import java.util.List;

public class HorseAdapter extends RecyclerView.Adapter<HorseAdapter.HorseViewHolder> {

    public interface OnHorseSelectedListener {
        void onHorseSelected(Horse horse);
    }

    private List<Horse> listeChevaux;
    private final Context context;
    private final OnHorseSelectedListener onHorseSelectedListener;
    private final boolean readOnlyMode;
    private String selectedHorseId;

    public HorseAdapter(List<Horse> listeChevaux, Context context) {
        this(listeChevaux, context, null, false);
    }

    public HorseAdapter(List<Horse> listeChevaux, Context context, boolean readOnlyMode) {
        this(listeChevaux, context, null, readOnlyMode);
    }

    public HorseAdapter(List<Horse> listeChevaux, Context context, OnHorseSelectedListener onHorseSelectedListener) {
        this(listeChevaux, context, onHorseSelectedListener, false);
    }

    public HorseAdapter(List<Horse> listeChevaux, Context context, OnHorseSelectedListener onHorseSelectedListener, boolean readOnlyMode) {
        this.listeChevaux = listeChevaux;
        this.context = context;
        this.onHorseSelectedListener = onHorseSelectedListener;
        this.readOnlyMode = readOnlyMode;
    }

    @NonNull
    @Override
    public HorseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cheval, parent, false);
        return new HorseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorseViewHolder holder, int position) {
        Horse currentHorse = listeChevaux.get(position);
        int currentHour = LocalTime.now().getHour();
        String currentState = currentHorse.getCurrentState(currentHour);

        holder.tvNom.setText(currentHorse.getName());
        holder.tvRace.setText("Race : " + currentHorse.getRace());
        holder.tvDetails.setText(
                "Age : " + currentHorse.getAge()
                        + " ans\nCavalier : " + currentHorse.getCavalierNom());
        holder.tvState.setText(currentState);

        holder.imgCheval.setImageResource(DrawableResolver.resolveHorseImage(currentHorse.getPhotoUrl()));

        if (Horse.STATE_SICK.equals(currentState)) {
            holder.tvNom.setTextColor(Color.RED);
            holder.tvState.setBackgroundResource(R.drawable.bg_status_sick);
            holder.tvState.setTextColor(Color.parseColor("#8F2B22"));
        } else if (Horse.STATE_STOPPED.equals(currentState)) {
            holder.tvNom.setTextColor(Color.parseColor("#B26A00"));
            holder.tvState.setBackgroundResource(R.drawable.bg_status_stopped);
            holder.tvState.setTextColor(Color.parseColor("#8A5A00"));
        } else {
            holder.tvNom.setTextColor(Color.parseColor("#8C6A53"));
            holder.tvState.setBackgroundResource(R.drawable.bg_status_good);
            holder.tvState.setTextColor(Color.parseColor("#3E6E44"));
        }

        boolean isSelected = currentHorse.getId() != null && currentHorse.getId().equals(selectedHorseId);
        holder.itemView.setBackgroundResource(isSelected ? R.drawable.bg_surface_card_tinted : R.drawable.bg_surface_card);

        if (onHorseSelectedListener != null) {
            holder.itemView.setOnClickListener(v -> {
                selectedHorseId = currentHorse.getId();
                notifyDataSetChanged();
                onHorseSelectedListener.onHorseSelected(currentHorse);
            });
            holder.itemView.setOnLongClickListener(v -> {
                openHorseDetails(currentHorse);
                return true;
            });
        } else {
            holder.itemView.setOnClickListener(v -> openHorseDetails(currentHorse));
            holder.itemView.setOnLongClickListener(null);
        }
    }

    @Override
    public int getItemCount() {
        return listeChevaux.size();
    }

    public void updateList(List<Horse> newList) {
        this.listeChevaux = newList;
        notifyDataSetChanged();
    }

    public void setSelectedHorseId(String selectedHorseId) {
        this.selectedHorseId = selectedHorseId;
        notifyDataSetChanged();
    }

    private void openHorseDetails(Horse horse) {
        Intent intent = new Intent(context, readOnlyMode ? HorseInfoActivity.class : HorseDetailActivity.class);
        intent.putExtra("HORSE_ID", horse.getId());
        context.startActivity(intent);
    }

    public static class HorseViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCheval;
        TextView tvNom, tvRace, tvDetails, tvState;

        public HorseViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCheval = itemView.findViewById(R.id.img_horse_club);
            tvNom = itemView.findViewById(R.id.tv_club_name);
            tvState = itemView.findViewById(R.id.tv_club_state);
            tvRace = itemView.findViewById(R.id.tv_club_race);
            tvDetails = itemView.findViewById(R.id.tv_club_details);
        }
    }
}
