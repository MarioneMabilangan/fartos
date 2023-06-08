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
import com.example.fartos.fragment.CartaOchoDialogFragment;
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
    private static final List<Casella> CASILLAS = new ArrayList<>();
    private final List<Jugador> players = new ArrayList<>();
    private Casella casillaFinal;
    private List<CartaView> baralla = new ArrayList<>();
    private AdapterCasella adapterCasella;
    private RecyclerView cartaRV;
    private RecyclerView casillaRV;
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
            if (casillaFinal.getRonda() == 3) {
                toastMessage += "\nRonda finalitzada " + casillaFinal.getRonda();
            } else {
                toastMessage += "\nNova ronda! " + (casillaFinal.getRonda() + 1);
                casillaFinal.setRonda(casillaFinal.getRonda() + 1);
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

    public void recyclerCasillas() {
        adapterCasella = new AdapterCasella(CASILLAS);
        casillaRV.setHasFixedSize(true);
        casillaRV.setLayoutManager(new LinearLayoutManager(this));
        casillaRV.setAdapter(adapterCasella);
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
        casillaRV = findViewById(R.id.recyclerCasillas);
        for (int i = 0; i < 16; i++) {
            CASILLAS.add(new Casella(String.valueOf(i), i, backgroundTransparent, backgroundTransparent));
        }
        casillaFinal = CASILLAS.get(CASILLAS.size() - 1);
        recyclerCasillas();

        move(players.get(2), 0);
        move(players.get(0), 1);
        move(players.get(1), 0);
    }
    public void initMap4() {
        casillaRV = findViewById(R.id.recyclerCasillas);
        for (int i = 0; i < 16; i++) {
            CASILLAS.add(new Casella(String.valueOf(i), i, backgroundTransparent, backgroundTransparent));
        }
        casillaFinal = CASILLAS.get(CASILLAS.size() - 1);
        recyclerCasillas();

        move(players.get(2), 0);
        move(players.get(0), 1);
        move(players.get(1), 2);
        move(players.get(3), 1);
    }
    public void initMap5() {
        casillaRV = findViewById(R.id.recyclerCasillas);
        for (int i = 0; i < 16; i++) {
            CASILLAS.add(new Casella(String.valueOf(i), i, backgroundTransparent, backgroundTransparent));
        }
        casillaFinal = CASILLAS.get(CASILLAS.size() - 1);
        recyclerCasillas();

        move(players.get(2), 0);
        move(players.get(0), 1);
        move(players.get(1), 2);
        move(players.get(3), 1);
        move(players.get(4), 2);
    }
    public void initMap6() {
        casillaRV = findViewById(R.id.recyclerCasillas);
        for (int i = 0; i < 16; i++) {
            CASILLAS.add(new Casella(String.valueOf(i), i, backgroundTransparent, backgroundTransparent));
        }
        casillaFinal = CASILLAS.get(CASILLAS.size() - 1);
        recyclerCasillas();

        move(players.get(2), 0);
        move(players.get(0), 1);
        move(players.get(1), 2);
        move(players.get(3), 1);
        move(players.get(4), 2);
        move(players.get(5), 3);
    }

    public void move(Jugador jugador, int move) {
        int nCasilla1 = Math.max(jugador.getNumCasilla(), 0);
        int nCasilla2 = nCasilla1 + move;
        if (nCasilla2 <= 0) {
            nCasilla2 = 0;
        }
        if (nCasilla2 >= CASILLAS.size() - 1) {
            nCasilla2 = CASILLAS.size() - 1;
        }
        jugador.setNumCasilla(nCasilla2);

        if (CASILLAS.get(nCasilla2).getP1() == backgroundTransparent) {
            if (jugador.isPlayer()) {
                CASILLAS.get(nCasilla1).setP1(backgroundTransparent);
            } else {
                CASILLAS.get(nCasilla1).setP2(backgroundTransparent);
            }
            CASILLAS.get(nCasilla2).setP1(jugador.getSprite());
            jugador.setPlayer(true);
        } else if (CASILLAS.get(nCasilla2).getP2() == backgroundTransparent) {
            if (jugador.isPlayer()) {
                CASILLAS.get(nCasilla1).setP1(backgroundTransparent);
            } else {
                CASILLAS.get(nCasilla1).setP2(backgroundTransparent);
            }
            CASILLAS.get(nCasilla2).setP2(jugador.getSprite());
            jugador.setPlayer(false);
        }

        casillaRV.setAdapter(adapterCasella);
    }

    public void teleport(Jugador user, Jugador selectedJugador) {
        int casillaUser = user.getNumCasilla();
        boolean pUser = user.isPlayer();
        int casillaSelectedJugador = selectedJugador.getNumCasilla();
        boolean pSelectedJugador = selectedJugador.isPlayer();
        user.setNumCasilla(casillaSelectedJugador);
        user.setPlayer(pSelectedJugador);
        selectedJugador.setNumCasilla(casillaUser);
        selectedJugador.setPlayer(pUser);

        if (user.isPlayer()) {
            CASILLAS.get(user.getNumCasilla()).setP1(user.getSprite());
        } else {
            CASILLAS.get(user.getNumCasilla()).setP2(user.getSprite());
        }
        if (selectedJugador.isPlayer()) {
            CASILLAS.get(selectedJugador.getNumCasilla()).setP1(backgroundTransparent);
            CASILLAS.get(selectedJugador.getNumCasilla()).setP1(selectedJugador.getSprite());
        } else {
            CASILLAS.get(selectedJugador.getNumCasilla()).setP2(selectedJugador.getSprite());
        }
        recyclerCasillas();
    }

    public void zancadilla(Jugador jugador) {
        jugador.getCartasMano().remove((int) (Math.random() * jugador.getCartasMano().size()));
    }


    public void patada(Jugador jugador) {
        jugador.setPatada(true);
    }


    public void hundimiento(Jugador jugador) {
        int move = jugador.getNumCasilla();
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

    public void casilla8(Jugador user) {
        CartaOchoDialogFragment cartaOchoDialogFragment = new CartaOchoDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        bundle.putSerializable("jugadors", (Serializable) players);
        cartaOchoDialogFragment.setArguments(bundle);
        cartaOchoDialogFragment.show(getSupportFragmentManager(), "Casilla 8 Fragment");
    }

    public void deleteCasilla() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            players.removeIf(jugador -> jugador.getNumCasilla() == 0);
        }
        for (Jugador jugador : players) {
            jugador.setNumCasilla(jugador.getNumCasilla() - 1);
        }
        CASILLAS.remove(0);

    }

    public void checkWin(Jugador jugador) {
        if (jugador.getNumCasilla() == CASILLAS.size() - 1) {
            FragmentManager manager = getSupportFragmentManager();
            Youwin cm = new Youwin();

            cm.show(manager, "MessageDialog");
            Toast.makeText(this, "Guanyador " + jugador.getName() + "!", Toast.LENGTH_SHORT).show();
        }
        if (players.size() == 1) {
            Toast.makeText(this, "Guanyador " + players.get(currentPlayerIndex).getName() + "!", Toast.LENGTH_SHORT)
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
        bundle.putSerializable("casillas", (Serializable) CASILLAS);
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