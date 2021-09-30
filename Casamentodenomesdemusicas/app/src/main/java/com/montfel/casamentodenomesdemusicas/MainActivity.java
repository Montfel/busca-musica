package com.montfel.casamentodenomesdemusicas;

import static com.montfel.casamentodenomesdemusicas.BancoDeDados.bancoDeDados;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final List<Musica> listaScoreMusica = new ArrayList<>();
    private RecyclerView rvScoreMusica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.titulo);

        EditText etNomeMusica = findViewById(R.id.etNomeMusica);
        rvScoreMusica = findViewById(R.id.rvScoreMusica);

        // Configura a RecyclerView onde mostrará os elementos dispostos em uma lista na tela
        rvScoreMusica.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvScoreMusica.setHasFixedSize(true);

        // Configura um listener no campo de texto para verificar as mudanças no texto, e para cada
        // mudança ele chama a calculadora de Score
        etNomeMusica.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculaScore(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    // Função que recebe o input do campo de texto e calcula o score
    private void calculaScore(String input) {

        // Verifica se o input obtido é nulo, se sim limpa o RecyclerView, senão calcula o score
        if (input.isEmpty()) {
            rvScoreMusica.setAdapter(null);
        } else {
            // Limpa a lista para obter resultados diferentes para cada pesquisa
            listaScoreMusica.clear();

            // Formata o texto do input substituindo os caracteres com acento por sem acento, como
            // também converte todos os caracteres para minúsculo
            String inputFormatado = Normalizer.normalize(input, Normalizer.Form.NFD)
                                              .replaceAll("[^\\p{ASCII}]", "")
                                              .toLowerCase();

            // Transfere o texto formatado do input para um array quebrando em espaços
            String[] palavrasInput = inputFormatado.split(" ");

            // Percorre o banco de dados que foi gravado em um List estático
            for (String musica : bancoDeDados) {
                int score = 0;

                // Formata o texto do banco de dados substituindo os caracteres com acento por sem
                // acento, como também converte todos os caracteres para minúsculo
                String musicaFormatada = Normalizer.normalize(musica, Normalizer.Form.NFD)
                                               .replaceAll("[^\\p{ASCII}]", "")
                                               .toLowerCase();

                // Transfere o texto formatado do banco de dados para um array quebrando em espaços
                String[] palavrasMusica = musicaFormatada.split(" ");

                // Percorre as palavras do input
                for (String pInput : palavrasInput) {

                    // Percorre as palavras da música
                    for (String pMusica : palavrasMusica) {

                        // Acrescenta o score em 10 se uma palava do input for exatemente igual a
                        // uma palavra da música
                        if (pInput.equals(pMusica)) {
                            score += 10;
                        }

                        // Recupera o tamanho da menor palavra entre o input e a música, pois não é
                        // necessário comparar as posições maiores que o tamanho da menor palavra,
                        // pois sempre vão ter a contagem zero
                        int menor = Integer.min(pInput.length(), pMusica.length());

                        // Realiza um laço até o tamanho da menor palavra e compara se as letras de
                        // cada palavra são iguais na mesma posição, se sim acrescenta o score em 1
                        for (int i = 0; i < menor; i++) {
                            if (pInput.charAt(i) == pMusica.charAt(i)) {
                                score++;
                            }
                        }
                    }
                }

                // Verifica se na música existe a palavra "feat" e se ela não existe no input, caso
                // atenda a esses requisitos descrementa o score em 5
                if (musicaFormatada.contains("feat") && !inputFormatado.contains("feat")) {
                    score -= 5;
                }

                // Adiciona na lista o objeto música com o score calculado e o nome original da música
                listaScoreMusica.add(new Musica(score, musica));
            }

            // Ordena a lista em ordem decrescente
            listaScoreMusica.sort((o1, o2) -> Integer.compare(o2.getScore(), o1.getScore()));

            // Instancia o adapter e transfere para ele a lista com as 10 primeiras posições e após
            // isso define o adapter para o Recycler View
            rvScoreMusica.setAdapter(new ScoreMusicaAdapter(listaScoreMusica.subList(0, 10)));
        }
    }
}