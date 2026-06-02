package com.example.equi;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RiderAdapter extends RecyclerView.Adapter<RiderAdapter.RiderViewHolder> {

    private List<User> riderList;

    public RiderAdapter(List<User> riderList) {
        this.riderList = riderList;
    }

    @NonNull
    @Override
    public RiderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rider, parent, false);
        return new RiderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RiderViewHolder holder, int position) {
        User rider = riderList.get(position);
        holder.tvName.setText(rider.getNom());
        holder.tvEmail.setText(rider.getEmail());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), RiderDetailActivity.class);
            intent.putExtra("RIDER_EMAIL", rider.getEmail());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return riderList.size();
    }

    public static class RiderViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvEmail;

        public RiderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_rider_name);
            tvEmail = itemView.findViewById(R.id.tv_rider_email);
        }
    }
}