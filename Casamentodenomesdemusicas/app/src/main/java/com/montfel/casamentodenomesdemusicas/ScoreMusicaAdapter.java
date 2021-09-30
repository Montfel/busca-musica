package com.montfel.casamentodenomesdemusicas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Cria o adapter que vai ser responsável por dimensionar e atualizar os dados do RecyclerView
public class ScoreMusicaAdapter extends RecyclerView.Adapter<ScoreMusicaAdapter.MyViewHolder> {

    private List<Musica> listaScoreMusicas;

    public ScoreMusicaAdapter(List<Musica> listaScoreMusicas) {
        this.listaScoreMusicas = listaScoreMusicas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_musica_adapter, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(ScoreMusicaAdapter.MyViewHolder holder, int position) {
        Musica musica = listaScoreMusicas.get(position);
        holder.tvScore.setText(String.valueOf(musica.getScore()));
        holder.tvNomeMusica.setText(musica.getNomeMusica());
    }

    @Override
    public int getItemCount() {
        return this.listaScoreMusicas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvNomeMusica;
        TextView tvScore;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvNomeMusica = itemView.findViewById(R.id.tvNomeMusica);
            tvScore = itemView.findViewById(R.id.tvScore);
        }
    }
}
