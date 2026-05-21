package com.miempresa.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ProductosActivity extends AppCompatActivity {

    ListView listView;
    FloatingActionButton fab;

    DBHelper dbHelper;

    ArrayList<Producto> listaProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);

        listView = findViewById(R.id.listView);
        fab = findViewById(R.id.fab);

        dbHelper = new DBHelper(this);

        listaProductos = dbHelper.obtenerProductos();

        ArrayList<String> nombres = new ArrayList<>();

        for(Producto p : listaProductos){

            nombres.add(p.getNombre() + " - $" + p.getPrecio());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                nombres
        );

        listView.setAdapter(adapter);

        fab.setOnClickListener(v -> {

            startActivity(new Intent(this, AgregarProductoActivity.class));
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {

            Producto producto = listaProductos.get(position);

            Intent i = new Intent(this, DetalleProductoActivity.class);

            i.putExtra("id", producto.getId());
            i.putExtra("nombre", producto.getNombre());
            i.putExtra("descripcion", producto.getDescripcion());
            i.putExtra("precio", producto.getPrecio());

            startActivity(i);
        });
    }
}