package com.example.fartos.model;

import java.util.*;

public class Carta {

    public enum tipuscarta {
        MOV_1(28), MOV_2(18), MOV_3(10), TELEPORT(3), ZANCADILLA(4), PATADA(3), HUNDIMIENTO(2),
        BROMA(2);

        private int numCartes;

        public int getNumCartes() {
            return numCartes;
        }

        tipuscarta(int numCartes) {
            this.numCartes = numCartes;
        }
    }


    private int numero;
    private tipuscarta efecte;


    public int getNumero() {
        return numero;
    }

    public tipuscarta getEfecte() {
        return efecte;
    }


    private Carta(int numero, tipuscarta efecte) {
        this.numero = numero;
        this.efecte = efecte;
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();


        result.append(numero + " --> ");
        switch (efecte) {
            case MOV_1:
                result.append("Mover 1");
                break;

            case MOV_2:
                result.append("Mover 2");
                break;

            case MOV_3:
                result.append("Mover 3");
                break;

            default:
                result.append(efecte.name() + "");
        }

        return result.toString();
    }
}