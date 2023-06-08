package com.example.fartos.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fartos.activity.MainGame;
import com.example.fartos.R;
import com.example.fartos.adapter.AdapterJugadorButton;
import com.example.fartos.interfac.SelectListenerJugador;
import com.example.fartos.layout.ScaleCenterItemLayoutManager;
import com.example.fartos.model.Jugador;

import java.util.ArrayList;
import java.util.List;

public class CasellaVuitDialogFragment extends DialogFragment implements SelectListenerJugador {

    List<Jugador> jugadors = new ArrayList<>();
    Jugador selectedJugador;
    AdapterJugadorButton adapterJugadorButton;
    RecyclerView btnRv;
    Jugador user;
    boolean isUser = false;

    @SuppressLint("MissingInflatedId")
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.cas8_dialog_fragment, null);

        Bundle bundle = getArguments();
        jugadors = (List<Jugador>) bundle.getSerializable("jugadors");
        user = (Jugador) bundle.getSerializable("user");
        btnRv = view.findViewById(R.id.recylerPlayers8);

        initElements();

        builder.setView(view);
        return builder.create();
    }

    public void initElements() {
        adapterJugadorButton = new AdapterJugadorButton(jugadors, this);
        btnRv.setHasFixedSize(true);
        btnRv.setLayoutManager(
                new ScaleCenterItemLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        btnRv.setAdapter(adapterJugadorButton);
    }

    @Override
    public void onItemClicked(Jugador jugador) {
        selectedJugador = jugador;
        isUser = selectedJugador.equals(user);
        if (isUser) {
            ((MainGame) getActivity()).move(selectedJugador, 5);
        } else {
            ((MainGame) getActivity()).move(selectedJugador, -5);
        }
        dismiss();
    }
}

