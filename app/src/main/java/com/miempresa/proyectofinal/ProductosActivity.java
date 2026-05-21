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
        setContentView(R.layout.activity_productos);

        listView = findViewById(R.id.listView);
        bottomNavigation = findViewById(R.id.bottomNavigation);

        dbHelper = new DBHelper(this);

        cargarProductos();

        bottomNavigation.setOnItemSelectedListener(item -> {

            if(item.getItemId() == R.id.nav_productos){

                return true;
            }

            else if(item.getItemId() == R.id.nav_agregar){

                startActivity(new Intent(this, com.miempresa.proyectofinal.AgregarProductoActivity.class));

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

    private void cargarProductos(){

        listaProductos = dbHelper.obtenerProductos();

        adapter = new ProductoAdapter(this, listaProductos);

        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        cargarProductos();
    }
}