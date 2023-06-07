package com.example.fartos.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.fartos.activity.MainGame;
import com.example.fartos.R;

public class Jugadores extends DialogFragment {
    String jugador1, jugador2, jugador3, jugador4, jugador5, jugador6;
    public Jugadores.OnDismissListener onDismissListener;
    int contador = 0;

    public Jugadores() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jugadores, container, false);

        EditText groupIdEditText = view.findViewById(R.id.editTextGroupName);
        EditText groupIdEditText2 = view.findViewById(R.id.jugador2);
        EditText groupIdEditText3 = view.findViewById(R.id.jugador3);
        EditText groupIdEditText4 = view.findViewById(R.id.jugador4);
        EditText groupIdEditText5 = view.findViewById(R.id.jugador5);
        EditText groupIdEditText6 = view.findViewById(R.id.jugador6);
        ImageView cuatro = view.findViewById(R.id.cuatro);
        ImageView cinco = view.findViewById(R.id.cinco);
        ImageView seis = view.findViewById(R.id.seis);

        cuatro.setVisibility(View.INVISIBLE);
        cinco.setVisibility(View.INVISIBLE);
        seis.setVisibility(View.INVISIBLE);
        groupIdEditText4.setVisibility(View.INVISIBLE);
        groupIdEditText5.setVisibility(View.INVISIBLE);
        groupIdEditText6.setVisibility(View.INVISIBLE);

        Button confirmButton = view.findViewById(R.id.buttonGetAlumnos);
        Button mas = view.findViewById(R.id.button3);

        mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contador++;
                if (contador == 1){
                    groupIdEditText4.setVisibility(View.VISIBLE);
                    cuatro.setVisibility(View.VISIBLE);
                }else
                if (contador == 2){
                    groupIdEditText5.setVisibility(View.VISIBLE);
                    cinco.setVisibility(View.VISIBLE);
                }else
                if (contador == 3){
                    groupIdEditText6.setVisibility(View.VISIBLE);
                    seis.setVisibility(View.VISIBLE);
                }
            }
        });


        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombrejugador1 = groupIdEditText.getText().toString();
                String nombrejugador2 = groupIdEditText2.getText().toString();
                String nombrejugador3 = groupIdEditText3.getText().toString();
                String nombrejugador5 = groupIdEditText5.getText().toString();
                String nombrejugador4 = groupIdEditText4.getText().toString();
                String nombrejugador6 = groupIdEditText6.getText().toString();
                jugador1 = nombrejugador1;
                jugador2 = nombrejugador2;
                jugador3 = nombrejugador3;
                jugador4 = nombrejugador4;
                jugador5 = nombrejugador5;
                jugador6 = nombrejugador6;

                if (onDismissListener != null) {
                    onDismissListener.onDismiss(Jugadores.this);
                }

                dismiss();
                Intent intent = new Intent(getActivity(), MainGame.class);
                intent.putExtra("jugador1", jugador1);
                intent.putExtra("jugador2", jugador2);
                intent.putExtra("jugador3", jugador3);
                intent.putExtra("jugador4", jugador4);
                intent.putExtra("jugador5", jugador5);
                intent.putExtra("jugador6", jugador6);
                intent.putExtra("contador", contador);
                startActivity(intent);
            }
        });

        return view;
    }

    public void setOnDismissListener(Jugadores.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }



    public interface OnDismissListener {
        void onDismiss(Jugadores fragment);
    }
    @Override
    public void onStart() {
        super.onStart();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        getDialog().getWindow().setLayout((int) (width * 1), (int) (height * 0.45));
    }
}