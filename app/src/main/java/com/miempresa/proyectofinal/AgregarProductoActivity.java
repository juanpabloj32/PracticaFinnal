package com.miempresa.proyectofinal;


import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AgregarProductoActivity extends AppCompatActivity {

    EditText nombre, descripcion, precio;
    Button guardar;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);

        nombre = findViewById(R.id.nombre);
        descripcion = findViewById(R.id.descripcion);
        precio = findViewById(R.id.precio);
        guardar = findViewById(R.id.guardar);

        dbHelper = new DBHelper(this);

        guardar.setOnClickListener(v -> {

            String nom = nombre.getText().toString();
            String des = descripcion.getText().toString();
            double pre = Double.parseDouble(precio.getText().toString());

            boolean insertado = dbHelper.insertarProducto(nom, des, pre);

            if(insertado){

                Toast.makeText(this, "Producto agregado", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}