package com.example.navalbattle;

import java.util.Random;

public class Tabuleiro {
    public int[][] tab = new int[15][10];

    public Tabuleiro() {
        Random rand = new Random();

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 10; j++) {
                int randomNumber = rand.nextInt(100);

                if (randomNumber < 15) {
                    tab[i][j] = 2;
                } else if (randomNumber < 50) {
                    tab[i][j] = 0;
                } else {
                    tab[i][j] = 1;
                }
            }
        }
    }

    public int pos(int i, int j) {
        return tab[i][j];
    }

    public void novoJogo(){
        Random rand = new Random();

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 10; j++) {
                int randomNumber = rand.nextInt(100);

                if (randomNumber < 15) {
                    tab[i][j] = 2;
                } else if (randomNumber < 50) {
                    tab[i][j] = 0;
                } else {
                    tab[i][j] = 1;
                }
            }
        }
    }

}
