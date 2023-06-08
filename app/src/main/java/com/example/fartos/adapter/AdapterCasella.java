package com.example.fartos.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fartos.R;
import com.example.fartos.model.Casella;

import java.util.List;

public class AdapterCasella extends RecyclerView.Adapter<AdapterCasella.MyViewHolder> {
    private List<Casella> casellaList;

    public AdapterCasella(List<Casella> casellaList) {
        this.casellaList = casellaList;
    }

    @NonNull
    @Override
    public AdapterCasella.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterCasella.MyViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_carta, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCasella.MyViewHolder holder, int position) {
        holder.bindData(casellaList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return casellaList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView spriteP1;
        ImageView spriteP2;
        TextView txtCasella;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            spriteP1 = itemView.findViewById(R.id.p1);
            spriteP2 = itemView.findViewById(R.id.p2);
            txtCasella = itemView.findViewById(R.id.casellaN);

        }

        void bindData(final Casella casella, int position) {
            txtCasella.setText("Casella " + casella.getNom());
            spriteP1.setImageResource(casella.getP1());
            txtCasella.setBackgroundColor(Color.TRANSPARENT);
            txtCasella.setTextColor(Color.WHITE);
            spriteP2.setImageResource(casella.getP2());

        }
    }
}

