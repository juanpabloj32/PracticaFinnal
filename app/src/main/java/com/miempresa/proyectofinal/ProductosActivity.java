package com.miempresa.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ProductosActivity extends AppCompatActivity {

    ListView listView;
    BottomNavigationView bottomNavigation;
    EditText buscador;
    DBHelper dbHelper;

    ArrayList<Producto> listaProductos;
    ArrayList<Producto> listaFiltrada;
    ProductoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);

        // COMPONENTES
        listView = findViewById(R.id.listView);
        bottomNavigation = findViewById(R.id.bottomNavigation);
        buscador = findViewById(R.id.buscador);

        // DB
        dbHelper = new DBHelper(this);

        // LISTAS
        listaProductos = new ArrayList<>();
        listaFiltrada = new ArrayList<>();

        // CARGAR
        cargarProductos();

        // BUSCADOR
        buscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtrarProductos(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // MENU
        bottomNavigation.setSelectedItemId(R.id.nav_productos);

        // REEMPLAZADO: Listener actualizado con la nueva opción "Acerca de"
        bottomNavigation.setOnItemSelectedListener(item -> {

            if(item.getItemId() == R.id.nav_productos){

                return true;

            } else if(item.getItemId() == R.id.nav_agregar){

                startActivity(new Intent(this, AgregarProductoActivity.class));
                return true;

            } else if(item.getItemId() == R.id.nav_acerca){

                // NUEVO: Redirección a la pantalla Acerca de
                startActivity(new Intent(this, AcercaDeActivity.class));
                return true;

            } else if(item.getItemId() == R.id.nav_logout){

                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return true;
            }

            return false;
        });

        // CLICK PRODUCTO
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Producto producto = listaFiltrada.get(position);

            Intent i = new Intent(this, DetalleProductoActivity.class);
            i.putExtra("id", producto.getId());
            i.putExtra("nombre", producto.getNombre());
            i.putExtra("descripcion", producto.getDescripcion());
            i.putExtra("precio", producto.getPrecio());
            i.putExtra("imagen", producto.getImagen());

            // UNIFICADO: Ahora también enviamos el stock al detalle del producto
            i.putExtra("stock", producto.getStock());

            startActivity(i);
        });
    }

    // CARGAR PRODUCTOS
    private void cargarProductos(){
        listaProductos.clear();
        listaProductos.addAll(dbHelper.obtenerProductos());

        listaFiltrada.clear();
        listaFiltrada.addAll(listaProductos);

        if(adapter == null){
            adapter = new ProductoAdapter(this, listaFiltrada);
            listView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    // FILTRAR PRODUCTOS
    private void filtrarProductos(String texto){
        listaFiltrada.clear();

        if(texto.trim().isEmpty()){
            listaFiltrada.addAll(listaProductos);
        } else {
            texto = texto.toLowerCase().trim();

            for(Producto producto : listaProductos){
                String nombre = producto.getNombre().toLowerCase();
                String descripcion = producto.getDescripcion().toLowerCase();

                if(nombre.contains(texto) || descripcion.contains(texto)){
                    listaFiltrada.add(producto);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarProductos();
        filtrarProductos(buscador.getText().toString());
    }
}