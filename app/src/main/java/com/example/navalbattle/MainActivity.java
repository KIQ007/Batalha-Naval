package com.example.navalbattle;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Tabuleiro tabBatalha;
    private MediaPlayer playerNavio;
    private MediaPlayer playerBomba;
    private MediaPlayer playerMar;
    private MediaPlayer playerFundo;
    private boolean parada;
    private int contador;
    private int contJogadas;
    private int pontos;
    private int multiplicador = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabBatalha = new Tabuleiro();
        playerNavio = MediaPlayer.create(this, R.raw.navio);
        playerMar = MediaPlayer.create(this, R.raw.agua);
        playerBomba = MediaPlayer.create(this, R.raw.bomba);
        playerFundo = MediaPlayer.create(this, R.raw.fundo);

        parada = false;

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                contador++;
                int minutos = (contador % 3600) / 60;
                int segundos = contador % 60;

                TextView cronometro = findViewById(R.id.tvCronometro);
                cronometro.setText(minutos + ":" + String.format("%02d", segundos));

                if (!playerFundo.isPlaying()) {
                    playerFundo.start();
                }

                if (!parada) {
                    handler.postDelayed(this, 1000);
                }
            }
        });

        GridLayout gridLayout = findViewById(R.id.gridLayout);
        int linhas = 15;
        int colunas = 10;
        TextView qtdJogadas = findViewById(R.id.tvJogadas);
        TextView qtdpontos = findViewById(R.id.tvPontos);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;
        float buttonSizeFactor = 0.9f;

        int buttonSize = (int) (Math.min(screenWidth / colunas, screenHeight / linhas) * buttonSizeFactor);

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                Button button = new Button(this);
                int posicao = i * colunas + j;
                button.setTag(posicao);

                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(buttonSize, buttonSize);
                button.setLayoutParams(params);

                button.setOnClickListener(v -> {
                    int tagPos = (int) v.getTag();
                    int linha = tagPos / colunas;
                    int coluna = tagPos % colunas;
                    int valor = tabBatalha.pos(linha, coluna);

                    Button clickedButton = (Button) v;

                    if (valor == 0) {
                        clickedButton.setBackgroundResource(R.drawable.cargueiro);
                        playerNavio.start();
                        contJogadas++;

                        if (multiplicador == 1) {
                            pontos++;
                            multiplicador = 2;
                        } else {
                            pontos += multiplicador;
                            multiplicador *= 2;
                        }
                    } else if (valor == 1) {
                        clickedButton.setBackgroundResource(R.drawable.ondas);
                        playerMar.start();
                        contJogadas++;
                        multiplicador = 1;
                    } else if (valor == 2) {
                        clickedButton.setBackgroundResource((R.drawable.bomba));
                        playerBomba.start();
                        contJogadas++;
                        pontos -= 5;
                        multiplicador = 1;
                    }

                    qtdJogadas.setText(String.valueOf(contJogadas));
                    qtdpontos.setText(String.valueOf(pontos));
                    clickedButton.setEnabled(false);
                });

                gridLayout.addView(button);
            }
        }
    }

}
