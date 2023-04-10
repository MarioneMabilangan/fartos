package com.example.fartos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainGame extends AppCompatActivity {
    String nom1, nom2, nom3, nom4, nom5, nom6;
int contador;
    private Taulell taulell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);
        getSupportActionBar().hide();


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            nom1 = extras.getString("jugador1");
            nom2 = extras.getString("jugador2");
            nom3 = extras.getString("jugador3");
            nom4 = extras.getString("jugador4");
            nom5 = extras.getString("jugador5");
            nom6 = extras.getString("jugador6");
            contador = extras.getInt("contador");
        }
        Jugador[] jugadors;
        if (contador == 3) {
            jugadors = new Jugador[] { new Jugador(nom1), new Jugador(nom2), new Jugador(nom3), new Jugador(nom4), new Jugador(nom5), new Jugador(nom6) };
        } else if (contador == 2) {
            jugadors = new Jugador[] { new Jugador(nom1), new Jugador(nom2), new Jugador(nom3), new Jugador(nom4), new Jugador(nom5) };
        } else if (contador == 1) {
            jugadors = new Jugador[] { new Jugador(nom1), new Jugador(nom2), new Jugador(nom3), new Jugador(nom4) };
        } else {
            jugadors = new Jugador[] { new Jugador(nom1), new Jugador(nom2), new Jugador(nom3) };
        }

        taulell = new Taulell(jugadors);
        System.out.println(taulell.getJugadors().toString());

        TextView boardView = findViewById(R.id.boardView);
        boardView.setText(taulell.toString());
    }
}