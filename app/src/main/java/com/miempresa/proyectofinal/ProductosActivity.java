package com.miempresa.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.miempresa.proyectofinal.DBHelper;
import com.miempresa.proyectofinal.LoginActivity;
import com.miempresa.proyectofinal.Producto;
import com.miempresa.proyectofinal.ProductoAdapter;

import java.util.ArrayList;

public class ProductosActivity extends AppCompatActivity {

    ListView listView;
    BottomNavigationView bottomNavigation;

    DBHelper dbHelper;

    ArrayList<Producto> listaProductos;

    ProductoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.activity_productos);

            // Mapeo de componentes desde el XML
            listView = findViewById(R.id.listView);
            bottomNavigation = findViewById(R.id.bottomNavigation);

            // Configuración inicial del menú de navegación
            bottomNavigation.setSelectedItemId(R.id.nav_productos);

            dbHelper = new DBHelper(this);

            // Carga los productos inicialmente
            cargarProductos();

            // Listener para controlar las opciones de navegación inferior
            bottomNavigation.setOnItemSelectedListener(item -> {

                if(item.getItemId() == R.id.nav_productos){
                    return true;
                }

                else if(item.getItemId() == R.id.nav_agregar){
                    startActivity(new Intent(this, AgregarProductoActivity.class));
                    return true;
                }

                else if(item.getItemId() == R.id.nav_logout){
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                }

                return false;
            });

            // Listener para interactuar con cada fila de la lista de productos
            listView.setOnItemClickListener((parent, view, position, id) -> {

                Producto producto = listaProductos.get(position);

                Intent i = new Intent(this, DetalleProductoActivity.class);

                i.putExtra("id", producto.getId());
                i.putExtra("nombre", producto.getNombre());
                i.putExtra("descripcion", producto.getDescripcion());
                i.putExtra("precio", producto.getPrecio());

                startActivity(i);
            });

        } catch (Exception e){
            // Muestra una alerta emergente en caso de que ocurra un error fatal en onCreate
            android.widget.Toast.makeText(this,
                    e.toString(),
                    android.widget.Toast.LENGTH_LONG).show();
        }
    }

    private void cargarProductos(){
        listaProductos = dbHelper.obtenerProductos();
        adapter = new ProductoAdapter(this, listaProductos);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresca la lista automáticamente cuando regresas a esta pantalla
        cargarProductos();
    }
}
