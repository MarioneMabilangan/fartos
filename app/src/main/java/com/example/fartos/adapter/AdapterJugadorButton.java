package com.example.fartos.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fartos.R;
import com.example.fartos.interfac.SelectListenerJugador;
import com.example.fartos.model.Jugador;

import java.util.List;

public class AdapterJugadorButton extends RecyclerView.Adapter<AdapterJugadorButton.MyViewHolder> {
    private final List<Jugador> playerList;
    private final SelectListenerJugador listenerJugador;

    public AdapterJugadorButton(List<Jugador> playerList, SelectListenerJugador listenerJugador) {
        this.playerList = playerList;
        this.listenerJugador = listenerJugador;
    }

    @NonNull
    @Override
    public AdapterJugadorButton.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                int viewType) {
        return new AdapterJugadorButton.MyViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_button, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterJugadorButton.MyViewHolder holder,
                                 @SuppressLint("RecyclerView") int position) {
        holder.bindData(playerList.get(position), position);

        holder.cardView.setOnClickListener(
                v -> listenerJugador.onItemClicked(playerList.get(position)));
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView btn;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            btn = itemView.findViewById(R.id.playerButton);
            cardView = itemView.findViewById(R.id.cardViewBtn);
        }

        void bindData(final Jugador jugador, int position) {
            btn.setImageResource(jugador.getIcon());
        }
    }
}