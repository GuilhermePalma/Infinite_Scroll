package com.example.infinitescrool;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe Principal que controla a Activity Inicial/Principal
 */
public class MainActivity extends AppCompatActivity {

    // Irá Controlar se está carregando mais itens na Lista
    private boolean is_loading = false;

    // Armazenará o Tamanho real da Lista (tamanho da lista - 1)
    private int real_size_list = 0;

    // Itens usados durante o Codigo
    private List<String> list_recyclerView;
    private RecyclerView recyclerView;
    private ManagerRecyclerView managerRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview_index);
        list_recyclerView = new ArrayList<>();
        managerRecyclerView = new ManagerRecyclerView(list_recyclerView, recyclerView,
                R.layout.layout_header, getString(R.string.app_name));

        // Instancia a Lista de Itens com 3 vezes do valor maximo que será exibido
        for (int i = 0; i <= ManagerRecyclerView.MAX_ITEMS_INITIAL; i++) {
            list_recyclerView.add(String.valueOf(i));
        }
        real_size_list = list_recyclerView.size() - 1;

        // Configura o RecyclerView
        setUpRecyclerView();
        setUpScrollRecyclerView();
    }

    /**
     * Configura o RecyclerView (Opções, Cliques, Textos, Layout)
     */
    private void setUpRecyclerView() {
        // Configura a quantidade da Exibição dos Itens
        GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 2);

        // Configura o RecyclerView (Tipo de Layout e Configuração das Ações/Exibição
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(managerRecyclerView);

        // Define a disposição dos Itens no Layout do RecyclerView
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                // Caso seja o "Loading" ou o "Header" retorna um espaço de 2, senão um espaço de 1
                return managerRecyclerView.isLoadingRecyclerView(position) ||
                        managerRecyclerView.isHeaderRecyclerView(position) ? 2 : 1;
            }
        });
    }

    /**
     * Configura o Scroll dos Elementos do RecyclerView
     */
    private void setUpScrollRecyclerView() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                // Está abaixando (dy > 0 = Abaixar da Tela) e não está tendo carregamento na Tela
                if (dy > 0 && !is_loading) {
                    // Obtem o Controlador do Layout e o Tamanho da Lista dos Itens
                    LinearLayoutManager layoutRecycler = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if (layoutRecycler != null && layoutRecycler.findLastCompletelyVisibleItemPosition() == real_size_list) {
                        is_loading = true;
                        loadMoreItems();
                    }
                }
            }
        });
    }

    /**
     * Adiciona a nova quantidade de Itens ao RecyclerView
     */
    private void loadMoreItems() {
        // Realiza as Operações em Background, dando uma pausa de 2 segundos
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {

            // Define um NOVO tamanho da LISTA (novo tamanho = novos itens)
            int next_real_size = real_size_list + ManagerRecyclerView.MAX_ITEMS_LOAD;
            for (int i = real_size_list; i <= next_real_size; i++) {
                list_recyclerView.add(String.valueOf(i));
            }

            // Atualiza os Itens no Recycler View
            recyclerView.post(() -> managerRecyclerView.notifyDataSetChanged());

            // Atualiza o Valor Real da Lista, Notifica o Controlador do RecyclerView que houve Mudança
            real_size_list = list_recyclerView.size() - 1;
            is_loading = false;
        }, 2000);
    }

}
