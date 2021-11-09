package com.example.infinitescrool;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

/**
 * Controla a Exibição, Adição, Remoção, Clique nos Itens do RecyclerView.
 * <p>
 * Herda os metodos da Classe {@link RecyclerView}
 * <p>
 * Possui uma Classe Interna ({@link InstanceItems}) que Exerce a Função do
 * {@link RecyclerView.ViewHolder}, instanciando e Obtendo os IDs dos Itens do Layout do RecyclerView
 */
public class ManagerRecyclerView extends RecyclerView.Adapter<ManagerRecyclerView.InstanceItems> {

    // Constantes que definem a quantidade de Itens que podem ser exibidos
    public static final int MAX_ITEMS_LOAD = 29;
    public static final int MAX_ITEMS_INITIAL = (3 * MAX_ITEMS_LOAD) + 3;

    // Itens que serão usados na programação
    private final List<String> list_items;
    private final View view_snackBar;
    private View viewItem;

    /**
     * Contrutor da Classe ManagerRecyclerView
     *
     * @param list_items    Lista de Itens que serão Exibidos no RecyclerView
     * @param view_snackBar View que será exibida a mensagem da SnackBar
     */
    public ManagerRecyclerView(List<String> list_items, View view_snackBar) {
        this.list_items = list_items;
        this.view_snackBar = view_snackBar;
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
        holder.txt_text.setText(String.valueOf(position));

        // Exibe uma Snackbar com o Valor do Item que foi Clicado
        holder.txt_text.setOnClickListener(v -> Snackbar
                .make(view_snackBar, String.valueOf(position), Snackbar.LENGTH_LONG)
                .setAction(viewItem.getContext().getString(R.string.option_ok), v1 -> {
                }).show());
    }

    /**
     * Obtem o Tamanho da Lista de Itens que serão Apresentados
     */
    @Override
    public int getItemCount() {
        if (list_items == null || list_items.size() == 0) return 0;
        else return list_items.size();
    }

    /**
     * Classe que retorna os campos que serão usados, com suas referencias
     */
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
