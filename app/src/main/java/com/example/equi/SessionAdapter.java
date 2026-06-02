package com.example.equi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.SessionViewHolder> {

    private final List<Session> sessionList;
    private final OnSessionActionListener listener;

    public interface OnSessionActionListener {
        void onEdit(Session session);
        void onDelete(Session session);
    }

    public SessionAdapter(List<Session> sessionList, OnSessionActionListener listener) {
        this.sessionList = sessionList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SessionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_session, parent, false);
        return new SessionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SessionViewHolder holder, int position) {
        Session session = sessionList.get(position);
        holder.tvHeure.setText(session.getHeure());
        holder.tvDate.setText("Date : " + SessionDateUtils.formatDateForDisplay(session.getDate()));
        holder.tvCavalier.setText("Cavalier : " + session.getCavalierNom());
        holder.tvCheval.setText("Cheval : " + session.getChevalNom());
        bindStatus(holder, session);

        holder.btnEdit.setOnClickListener(v -> listener.onEdit(session));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(session));
    }

    private void bindStatus(SessionViewHolder holder, Session session) {
        LocalDate sessionDate = SessionDateUtils.parseDate(session.getDate());
        LocalDate today = LocalDate.now();
        if (SessionDateUtils.isCompleted(session, LocalDateTime.now())) {
            holder.tvStatus.setText("Terminee");
            holder.tvStatus.setTextColor(holder.itemView.getResources().getColor(R.color.theme_success));
            holder.tvStatus.setBackgroundResource(R.drawable.bg_surface_card_tinted);
        } else if (sessionDate != null && sessionDate.equals(today)) {
            holder.tvStatus.setText("Aujourd'hui");
            holder.tvStatus.setTextColor(holder.itemView.getResources().getColor(R.color.theme_accent));
            holder.tvStatus.setBackgroundResource(R.drawable.bg_kpi_accent);
        } else {
            holder.tvStatus.setText("Prevue");
            holder.tvStatus.setTextColor(holder.itemView.getResources().getColor(R.color.theme_info));
            holder.tvStatus.setBackgroundResource(R.drawable.bg_status_info);
        }
    }

    @Override
    public int getItemCount() {
        return sessionList.size();
    }

    public static class SessionViewHolder extends RecyclerView.ViewHolder {
        TextView tvHeure, tvDate, tvCavalier, tvCheval, tvStatus;
        Button btnEdit, btnDelete;

        public SessionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHeure = itemView.findViewById(R.id.tv_session_heure);
            tvDate = itemView.findViewById(R.id.tv_session_date);
            tvCavalier = itemView.findViewById(R.id.tv_session_cavalier);
            tvCheval = itemView.findViewById(R.id.tv_session_cheval);
            tvStatus = itemView.findViewById(R.id.tv_session_status);
            btnEdit = itemView.findViewById(R.id.btn_edit_session);
            btnDelete = itemView.findViewById(R.id.btn_delete_session);
        }
    }
}
