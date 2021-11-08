package com.example.infinitescrool;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Classe Principal que controla a Activity Inicial/Principal
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configura a quantidade da Exibição dos Itens
        GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 2);

        // Configura o RecyclerView (Tipo de Layout e Configuração das Ações/Exibição
        RecyclerView recyclerView = findViewById(R.id.recyclerview_index);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new ManagerRecyclerView(29));

        // Define a disposição dos Itens no Layout do RecyclerView
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
    }

}
