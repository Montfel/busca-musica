package com.montfel.casamentodenomesdemusicas;

public class Musica {
    private int score;
    private String nomeMusica;

    public Musica(int score, String nomeMusica) {
        this.score = score;
        this.nomeMusica = nomeMusica;
    }

    public int getScore() {
        return score;
    }

    public String getNomeMusica() {
        return nomeMusica;
    }
}
