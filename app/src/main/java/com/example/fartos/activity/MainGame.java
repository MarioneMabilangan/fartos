package com.example.fartos.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fartos.R;
import com.example.fartos.adapter.AdapterCartaView;
import com.example.fartos.adapter.AdapterCasella;
import com.example.fartos.fragment.CasellaVuitDialogFragment;
import com.example.fartos.fragment.DialogFragCard;
import com.example.fartos.fragment.Youwin;
import com.example.fartos.interfac.SelectListenerCarta;
import com.example.fartos.layout.ScaleCenterItemLayoutManager;
import com.example.fartos.model.Casella;
import com.example.fartos.model.Jugador;
import com.example.fartos.model.CartaView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainGame extends AppCompatActivity implements SelectListenerCarta {

    String nom1, nom2, nom3, nom4, nom5, nom6;
    int contador;
    private static final List<Casella> CASELLAS = new ArrayList<>();
    private final List<Jugador> players = new ArrayList<>();
    private Casella casellaFinal;
    private List<CartaView> baralla = new ArrayList<>();
    private AdapterCasella adapterCasella;
    private RecyclerView cartaRV;
    private RecyclerView casellaRV;
    private Jugador user;
    private int backgroundTransparent;
    private int currentPlayerIndex = 0;
    TextView textViewTurno;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button buttonShowHideCartas;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);
        getSupportActionBar().hide();
        cartaRV = findViewById(R.id.recyclerCartas);
        textViewTurno = findViewById(R.id.turno);
        buttonShowHideCartas = findViewById(R.id.btnMostrarOcultarCartas);
        buttonShowHideCartas.setOnClickListener(view -> {
            if (cartaRV.getVisibility() == View.VISIBLE) {
                cartaRV.setVisibility(View.GONE);
                buttonShowHideCartas.setText("Mostrar cartas");
            } else {
                cartaRV.setVisibility(View.VISIBLE);
                buttonShowHideCartas.setText("Ocultar cartas");
            }
        });
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
        backgroundTransparent = R.drawable.background_transparente;
        if (contador == 3) {
            initJugadors6();
            initMap6();
        } else if (contador == 2) {
            initJugadors5();
            initMap5();
        } else if (contador == 1) {
            initJugadors4();
            initMap4();
        } else {
            initJugadors();
            initMap();
        }
        user = players.get(currentPlayerIndex);
        textViewTurno.setText("Turno: " + user.getName());
        initBaraja();
        shuffleCartas();

    }

    public void initJugadors() {
        players.add(new Jugador(nom1, R.drawable.ww, R.drawable.wwicon));
        players.add(new Jugador(nom2, R.drawable.viktorp, R.drawable.viktor));
        players.add(new Jugador(nom3, R.drawable.vi, R.drawable.viicon));
    }
    public void initJugadors4() {
        players.add(new Jugador(nom1, R.drawable.ww, R.drawable.wwicon));
        players.add(new Jugador(nom2, R.drawable.viktorp, R.drawable.viktor));
        players.add(new Jugador(nom3, R.drawable.vi, R.drawable.viicon));
        players.add(new Jugador(nom4, R.drawable.jayce, R.drawable.jayceicon));
    }
    public void initJugadors5() {
        players.add(new Jugador(nom1, R.drawable.ww, R.drawable.wwicon));
        players.add(new Jugador(nom2, R.drawable.viktorp, R.drawable.viktor));
        players.add(new Jugador(nom3, R.drawable.vi, R.drawable.viicon));
        players.add(new Jugador(nom4, R.drawable.jayce, R.drawable.jayceicon));
        players.add(new Jugador(nom5, R.drawable.sevika, R.drawable.sevikaicon));
    }
    public void initJugadors6() {
        players.add(new Jugador(nom1, R.drawable.ww, R.drawable.wwicon));
        players.add(new Jugador(nom2, R.drawable.viktorp, R.drawable.viktor));
        players.add(new Jugador(nom3, R.drawable.vi, R.drawable.viicon));
        players.add(new Jugador(nom4, R.drawable.jayce, R.drawable.jayceicon));
        players.add(new Jugador(nom5, R.drawable.sevika, R.drawable.sevikaicon));
        players.add(new Jugador(nom6, R.drawable.jinx, R.drawable.jinxicon));
    }


    public void shuffleCartas() {
        int numCardsPerPlayer = (players.size() >= 5) ? 5 : 6;
        for (Jugador jugador : players) {
            for (int i = 0; i < numCardsPerPlayer; i++) {
                jugador.getCartasMano().add(baralla.remove(0));
            }
        }
        recyclerBaraja();
    }

    public void recyclerBaraja() {
        List<CartaView> cartaEnMano = user.getCartasMano();

        if (cartaEnMano.isEmpty()) {
            cartaRV.setVisibility(View.GONE);
            String toastMessage = "T'has quedat sense cartes a la baralla";
            if (casellaFinal.getRonda() == 3) {
                toastMessage += "\nRonda finalitzada " + casellaFinal.getRonda();
            } else {
                toastMessage += "\nNova ronda! " + (casellaFinal.getRonda() + 1);
                casellaFinal.setRonda(casellaFinal.getRonda() + 1);
                initBaraja();
                shuffleCartas();
            }
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
        } else {
            cartaRV.setVisibility(View.VISIBLE);
            if (cartaRV.getAdapter() == null) {
                AdapterCartaView adapterCartaView = new AdapterCartaView(cartaEnMano, this);
                cartaRV.setAdapter(adapterCartaView);
                ScaleCenterItemLayoutManager linearLayoutManager =
                        new ScaleCenterItemLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                cartaRV.setLayoutManager(linearLayoutManager);
                cartaRV.setHasFixedSize(true);
            }
        }
    }

    public void recyclerCasellas() {
        adapterCasella = new AdapterCasella(CASELLAS);
        casellaRV.setHasFixedSize(true);
        casellaRV.setLayoutManager(new LinearLayoutManager(this));
        casellaRV.setAdapter(adapterCasella);
    }

    public void initBaraja() {
        baralla = new ArrayList<>();
        int[] quantitats = {28, 18, 10, 3, 4, 3, 2, 2};
        int[] ids = {R.drawable.mou_1, R.drawable.mou_2, R.drawable.mou_3,
                R.drawable.teleport, R.drawable.zancadilla, R.drawable.patada,
                R.drawable.hundimiento,
                R.drawable.broma};
        String[] noms =
                {"Moure 1", "Moure2", "Moure3", "Teleport", "Zancadilla", "Patada", "Hundimiento",
                        "Broma"};

        for (int i = 0; i < quantitats.length; i++) {
            for (int j = 0; j < quantitats[i]; j++) {
                baralla.add(new CartaView(noms[i], ids[i], i + 1));
            }
        }
        Collections.shuffle(baralla);
    }

    public void initMap() {
        casellaRV = findViewById(R.id.recyclerCasellas);
        for (int i = 0; i < 16; i++) {
            CASELLAS.add(new Casella(String.valueOf(i), i, backgroundTransparent, backgroundTransparent));
        }
        casellaFinal = CASELLAS.get(CASELLAS.size() - 1);
        recyclerCasellas();

        move(players.get(2), 0);
        move(players.get(0), 1);
        move(players.get(1), 0);
    }
    public void initMap4() {
        casellaRV = findViewById(R.id.recyclerCasellas);
        for (int i = 0; i < 16; i++) {
            CASELLAS.add(new Casella(String.valueOf(i), i, backgroundTransparent, backgroundTransparent));
        }
        casellaFinal = CASELLAS.get(CASELLAS.size() - 1);
        recyclerCasellas();

        move(players.get(2), 0);
        move(players.get(0), 1);
        move(players.get(1), 2);
        move(players.get(3), 1);
    }
    public void initMap5() {
        casellaRV = findViewById(R.id.recyclerCasellas);
        for (int i = 0; i < 16; i++) {
            CASELLAS.add(new Casella(String.valueOf(i), i, backgroundTransparent, backgroundTransparent));
        }
        casellaFinal = CASELLAS.get(CASELLAS.size() - 1);
        recyclerCasellas();

        move(players.get(2), 0);
        move(players.get(0), 1);
        move(players.get(1), 2);
        move(players.get(3), 1);
        move(players.get(4), 2);
    }
    public void initMap6() {
        casellaRV = findViewById(R.id.recyclerCasellas);
        for (int i = 0; i < 16; i++) {
            CASELLAS.add(new Casella(String.valueOf(i), i, backgroundTransparent, backgroundTransparent));
        }
        casellaFinal = CASELLAS.get(CASELLAS.size() - 1);
        recyclerCasellas();

        move(players.get(2), 0);
        move(players.get(0), 1);
        move(players.get(1), 2);
        move(players.get(3), 1);
        move(players.get(4), 2);
        move(players.get(5), 3);
    }

    public void move(Jugador jugador, int move) {
        int nCasella1 = Math.max(jugador.getNumCasella(), 0);
        int nCasella2 = nCasella1 + move;
        if (nCasella2 <= 0) {
            nCasella2 = 0;
        }
        if (nCasella2 >= CASELLAS.size() - 1) {
            nCasella2 = CASELLAS.size() - 1;
        }
        jugador.setNumCasella(nCasella2);

        if (CASELLAS.get(nCasella2).getP1() == backgroundTransparent) {
            if (jugador.isPlayer()) {
                CASELLAS.get(nCasella1).setP1(backgroundTransparent);
            } else {
                CASELLAS.get(nCasella1).setP2(backgroundTransparent);
            }
            CASELLAS.get(nCasella2).setP1(jugador.getSprite());
            jugador.setPlayer(true);
        } else if (CASELLAS.get(nCasella2).getP2() == backgroundTransparent) {
            if (jugador.isPlayer()) {
                CASELLAS.get(nCasella1).setP1(backgroundTransparent);
            } else {
                CASELLAS.get(nCasella1).setP2(backgroundTransparent);
            }
            CASELLAS.get(nCasella2).setP2(jugador.getSprite());
            jugador.setPlayer(false);
        }

        casellaRV.setAdapter(adapterCasella);
    }

    public void teleport(Jugador user, Jugador selectedJugador) {
        int casellaUser = user.getNumCasella();
        boolean pUser = user.isPlayer();
        int casellaSelectedJugador = selectedJugador.getNumCasella();
        boolean pSelectedJugador = selectedJugador.isPlayer();
        user.setNumCasella(casellaSelectedJugador);
        user.setPlayer(pSelectedJugador);
        selectedJugador.setNumCasella(casellaUser);
        selectedJugador.setPlayer(pUser);

        if (user.isPlayer()) {
            CASELLAS.get(user.getNumCasella()).setP1(user.getSprite());
        } else {
            CASELLAS.get(user.getNumCasella()).setP2(user.getSprite());
        }
        if (selectedJugador.isPlayer()) {
            CASELLAS.get(selectedJugador.getNumCasella()).setP1(backgroundTransparent);
            CASELLAS.get(selectedJugador.getNumCasella()).setP1(selectedJugador.getSprite());
        } else {
            CASELLAS.get(selectedJugador.getNumCasella()).setP2(selectedJugador.getSprite());
        }
        recyclerCasellas();
    }

    public void zancadilla(Jugador jugador) {
        jugador.getCartasMano().remove((int) (Math.random() * jugador.getCartasMano().size()));
    }


    public void patada(Jugador jugador) {
        jugador.setPatada(true);
    }


    public void hundimiento(Jugador jugador) {
        int move = jugador.getNumCasella();
        move(jugador, -move);
    }


    public void broma(Jugador player, Jugador selectedPlayer) {
        int posTrob = -1;
        boolean trobada = false;
        int pos = 0;
        for (CartaView c : player.getCartasMano()) {
            if (c.getTipus() == 8 && !trobada) {
                posTrob = pos;
                trobada = true;
            }
            pos = pos + 1;
        }
        if (!(player.getCartasMano().isEmpty())) {
            player.getCartasMano().remove(posTrob);
        }
        List<CartaView> handUser = player.getCartasMano();
        List<CartaView> handSelectedJugador = selectedPlayer.getCartasMano();
        player.setMa(handSelectedJugador);
        selectedPlayer.setMa(handUser);

        recyclerBaraja();
    }

    public void casella8(Jugador user) {
        CasellaVuitDialogFragment cartaOchoDialogFragment = new CasellaVuitDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        bundle.putSerializable("jugadors", (Serializable) players);
        cartaOchoDialogFragment.setArguments(bundle);
        cartaOchoDialogFragment.show(getSupportFragmentManager(), "Casella 8 Fragment");
    }

    public void deleteCasella() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            players.removeIf(jugador -> jugador.getNumCasella() == 0);
        }
        for (Jugador jugador : players) {
            jugador.setNumCasella(jugador.getNumCasella() - 1);
        }
        CASELLAS.remove(0);

    }

    public void checkWin(Jugador jugador) {
        if (jugador.getNumCasella() == CASELLAS.size() - 1) {
            FragmentManager manager = getSupportFragmentManager();
            Youwin cm = new Youwin();

            cm.show(manager, "MessageDialog");
            Toast.makeText(this, "Guanyador " + jugador.getName() + "!", Toast.LENGTH_LONG).show();
        }
        if (players.size() == 1) {
            Toast.makeText(this, "Guanyador " + players.get(currentPlayerIndex).getName() + "!", Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public void onItemClicked(CartaView cartaView) {
        CartaView cartaSeleccionada;
        cartaSeleccionada = cartaView;
        Jugador jugador = players.get(currentPlayerIndex);
        DialogFragCard dialogFragCard = new DialogFragCard();
        Bundle bundle = new Bundle();
        bundle.putSerializable("carta", cartaSeleccionada);
        bundle.putSerializable("jugadors", (Serializable) players);
        bundle.putSerializable("user", jugador);
        bundle.putSerializable("casellas", (Serializable) CASELLAS);
        dialogFragCard.setArguments(bundle);
        dialogFragCard.show(getSupportFragmentManager(), "Carta Fragment");
        currentPlayerIndex++;
        if (currentPlayerIndex >= players.size()) {
            currentPlayerIndex = 0;
        }
        Jugador jugadorActual = getJugadorActual();
        textViewTurno.setText("Turno: " + jugadorActual.getName());
    }

    public Jugador getJugadorActual() {
        return players.get(currentPlayerIndex);
    }
}