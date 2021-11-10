package com.example.infinitescrool;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
public class ManagerRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Constantes que definem a quantidade de Itens que serão exibidos
    public static final int MAX_ITEMS_LOAD = 29;
    public static final int MAX_ITEMS_INITIAL = MAX_ITEMS_LOAD + 2;

    // Tipos de Views
    private static final int POSITION_HEADER = 0;
    private static final int POSITION_LOADING = 1;
    private static final int POSITION_ITEM = 2;

    // Itens que serão usados na programação
    private final List<String> list_items;
    private final View view_snackBar;
    private final int id_layout_header;
    private final String title_header;
    private View view_item;

    /**
     * Contrutor da Classe ManagerRecyclerView
     *
     * @param list_items       Lista de Itens que serão Exibidos no RecyclerView
     * @param view_snackBar    View que será exibida a mensagem da SnackBar
     * @param id_layout_header ID do Layout do Header que será exibido no Primeiro Item
     * @param title_header     Titulo do Header (1° Item do RecyclerView)
     */
    public ManagerRecyclerView(List<String> list_items, View view_snackBar, int id_layout_header,
                               String title_header) {
        this.list_items = list_items;
        this.view_snackBar = view_snackBar;
        this.id_layout_header = id_layout_header;
        this.title_header = title_header;
    }

    /**
     * Cria a View e Obtem os IDs necessarios para a manipulações do Widgets
     *
     * @param viewGroup Local onde será inserido o Layout
     * @param viewType  Tipo da View (Header, Loading, Item, ...)
     * @return InstanceHeader|InstanceItems (Ambos Herdam do {@link RecyclerView.ViewHolder})
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // Infla o Layout de Acordo com o Tipo da View
        switch (viewType) {
            case POSITION_HEADER:
                view_item = LayoutInflater.from(viewGroup.getContext())
                        .inflate(id_layout_header, viewGroup, false);
                return new InstanceHeader(view_item);
            case POSITION_LOADING:
                view_item = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.layout_loading, viewGroup, false);
                return new InstanceItems(view_item);
            default:
                view_item = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.layout_recyclerview, viewGroup, false);
                return new InstanceItems(view_item);
        }
    }

    /**
     * Insere e Configura as Informações e Ações dos Itens do Recycler View
     *
     * @param holder   Instancia dos Itens que serão usados na view
     * @param position Indica a posição do Item no RecyclerView
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof InstanceHeader) {
            // Configura o Header
            ((InstanceHeader) holder).txt_header.setText(title_header);
        } else if (holder instanceof InstanceItems && getItemViewType(position) != POSITION_LOADING) {
            // Configura apenas os Itens que possuem textos
            ((InstanceItems) holder).txt_text.setText(list_items.get(position));

            // Exibe uma Snackbar com o Valor do Item que foi Clicado
            ((InstanceItems) holder).txt_text.setOnClickListener(v -> Snackbar
                    .make(view_snackBar, list_items.get(position), Snackbar.LENGTH_LONG)
                    .setAction(view_item.getContext().getString(R.string.option_ok), v1 -> {
                    }).show());
        }
        // Itens não Instanciados ou Sem configurações (ex: "Loading") não possuem configurações
    }

    /**
     * Obtem o Tamanho da Lista de Itens que serão Apresentados
     *
     * @return Numero de Itens na Lista passada
     */
    @Override
    public int getItemCount() {
        return list_items == null || list_items.size() == 0 ? 0 : list_items.size();
    }

    /**
     * Configura o Tipo da View (Header, Loading ou Items)
     *
     * @return 0 | 1 | 2
     */
    @Override
    public int getItemViewType(int position) {
        if (isHeaderRecyclerView(position)) return POSITION_HEADER;
        else if (isLoadingRecyclerView(position)) return POSITION_LOADING;
        return POSITION_ITEM;
    }

    /**
     * Verifica se a View está na Posição Inicial: 0 na posição do ItemCount e 0 na Posição da Lista
     *
     * @param position_item Posição do Item no RecyclerView/List
     * @return true/false
     */
    public boolean isHeaderRecyclerView(int position_item) {
        return position_item == POSITION_HEADER;
    }

    /**
     * Verifica se a View está na Posição de Loading - ocorre de 30 em 30 itens
     *
     * @param position_item Posição do Item no RecyclerView/List
     * @return true/false
     */
    public boolean isLoadingRecyclerView(int position_item) {
        // Verifica se o Numero é Divisivel por 30 (Com exceção dos primeiros 90 Itens)
        if (position_item < MAX_ITEMS_INITIAL) return false;
        return list_items.get(position_item) == null;
    }

    /**
     * Classe que retorna os widgets dos Itens que serão usados, com suas referencias e instancias
     */
    protected static class InstanceItems extends RecyclerView.ViewHolder {

        private final TextView txt_text;

        // Recupera os valores definidos no Layout do RecycleAdapter
        protected InstanceItems(@NonNull View itemView) {
            super(itemView);
            txt_text = itemView.findViewById(R.id.txt_item);
        }
    }

    /**
     * Classe que retorna os widgets do Header (Cabeçalho) que serão usados, com suas referencias
     * e instancias
     */
    protected static class InstanceHeader extends RecyclerView.ViewHolder {

        private final TextView txt_header;

        // Recupera os valores definidos no Layout do RecycleAdapter
        protected InstanceHeader(@NonNull View itemView) {
            super(itemView);
            txt_header = itemView.findViewById(R.id.text_header);
        }
    }
}
