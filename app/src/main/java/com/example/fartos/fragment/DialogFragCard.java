package com.example.fartos.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fartos.activity.MainGame;
import com.example.fartos.R;
import com.example.fartos.adapter.AdapterJugadorButton;
import com.example.fartos.interfac.SelectListenerJugador;
import com.example.fartos.layout.ScaleCenterItemLayoutManager;
import com.example.fartos.model.Casella;
import com.example.fartos.model.Jugador;
import com.example.fartos.model.CartaView;

import java.util.ArrayList;
import java.util.List;

public class DialogFragCard extends DialogFragment implements SelectListenerJugador {
    Jugador selectedJugador;
    CartaView cartaView;
    RecyclerView recyclerView;
    AdapterJugadorButton adapterJugadorButton;
    List<Jugador> jugadors = new ArrayList<>();
    List<Casella> casillas = new ArrayList<>();
    Jugador user;
    Casella lastCasilla;
    boolean isUser = false;
    boolean first8 = true;
    boolean broma = false;

    @SuppressLint("MissingInflatedId")
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment, null);

        Bundle bundle = getArguments();
        cartaView = (CartaView) bundle.getSerializable("carta");
        jugadors = (List<Jugador>) bundle.getSerializable("jugadors");
        casillas = (List<Casella>) bundle.getSerializable("casillas");
        user = (Jugador) bundle.getSerializable("user");


        ImageView imageView = view.findViewById(R.id.cartaInFragment);
        recyclerView = view.findViewById(R.id.recylerPlayers8);
        imageView.setImageResource(cartaView.getSkin());

        init();


        builder.setView(view);
        return builder.create();
    }

    public void init() {
        adapterJugadorButton = new AdapterJugadorButton(jugadors, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(
                new ScaleCenterItemLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapterJugadorButton);
    }

    @Override
    public void onItemClicked(Jugador jugador) {
        selectedJugador = jugador;
        isUser = selectedJugador.equals(user);
        int move = selectedJugador.isPatada() && isUser ? -1 : 0;

        switch (cartaView.getTipus()) {
            case 1:
                ((MainGame) getActivity()).move(selectedJugador, isUser ? move + 1 : move - 1);
                break;
            case 2:
                ((MainGame) getActivity()).move(selectedJugador, isUser ? move + 2 : move - 2);
                break;
            case 3:
                ((MainGame) getActivity()).move(selectedJugador, isUser ? move + 3 : move - 3);
                break;
            case 4:
                ((MainGame) getActivity()).teleport(user, selectedJugador);
                break;
            case 5:
                ((MainGame) getActivity()).zancadilla(selectedJugador);
                break;
            case 6:
                ((MainGame) getActivity()).patada(selectedJugador);
                break;
            case 7:
                ((MainGame) getActivity()).hundimiento(selectedJugador);
                break;
            case 8:
                broma = true;
                ((MainGame) getActivity()).broma(user, selectedJugador);
                break;
        }

        if (selectedJugador.getNumCasilla() == 7 && first8) {
            first8 = false;
            ((MainGame) getActivity()).casilla8(user);
        }

        int posTrob = -1;
        for (int i = 0; i < user.getCartasMano().size(); i++) {
            if (user.getCartasMano().get(i).getTipus() == cartaView.getTipus()) {
                posTrob = i;
                break;
            }
        }

        if (posTrob >= 0 && !broma) {
            user.getCartasMano().remove(posTrob);
            broma = false;
        }

        lastCasilla = casillas.get(casillas.size() - 1);
        if (user.getCartasMano().size() > 0 && lastCasilla.getRonda() >= 3) {
            lastCasilla.setRonda(lastCasilla.getRonda() + 1);
            ((MainGame) getActivity()).deleteCasilla();
            init();
        }

        ((MainGame) getActivity()).checkWin(selectedJugador);
        ((MainGame) getActivity()).recyclerBaraja();

        dismiss();
    }
}

