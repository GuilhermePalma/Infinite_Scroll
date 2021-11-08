package com.example.infinitescrool;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;

/**
 * Controla a Exibição, Adição, Remoção, Clique nos Itens do RecyclerView.
 * <p>
 * Herda os metodos da Classe {@link RecyclerView}
 * <p>
 * Possui uma Classe Interna ({@link InstanceItems}) que Exerce a Função do
 * {@link RecyclerView.ViewHolder}, instanciando e Obtendo os IDs dos Itens do Layout do RecyclerView
 */
public class ManagerRecyclerView extends RecyclerView.Adapter<ManagerRecyclerView.InstanceItems> {

    private final int max_value_item;
    private View viewItem;

    /**
     * Contrutor da Classe ManagerRecyclerView
     *
     * @param max_value_item Valor maximo de Itens que serão Exibidos (A Exibição irá começar do 0
     *                       e ir até o valor Informado)
     */
    public ManagerRecyclerView(int max_value_item) {
        this.max_value_item = max_value_item;
    }

    /**
     * Cria a View e Obtem os IDs necessarios para a manipulações do Widgets
     */
    @NonNull
    @Override
    public InstanceItems onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        viewItem = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_recyclerview, viewGroup, false);

        return new InstanceItems(viewItem);
    }

    /**
     * Insere as Informações nos Itens do Recycler View
     */
    @Override
    public void onBindViewHolder(@NonNull InstanceItems holder, int position) {
        holder.txt_text.setText(position);

        // Exibe uma Snackbar com o Valor do Item que foi Clicado
        holder.txt_text.setOnClickListener(v -> Snackbar.make(viewItem, position, Snackbar.LENGTH_LONG)
                .setAction(viewItem.getContext().getString(R.string.option_ok), v1 -> {
                }).show());
    }


    @Override
    public int getItemCount() {
        if (max_value_item == 0) return 0;
        else return max_value_item + 1;
    }

    // Classe que retorna os campos usados já referenciados
    protected static class InstanceItems extends RecyclerView.ViewHolder {

        private final MaterialCardView cardView_recycler;
        private final TextView txt_text;

        // Recupera os valores definidos no Layout do RecycleAdpater
        protected InstanceItems(@NonNull View itemView) {
            super(itemView);
            txt_text = itemView.findViewById(R.id.txt_item);
            cardView_recycler = itemView.findViewById(R.id.cardView_recyclerView);
        }
    }
}
