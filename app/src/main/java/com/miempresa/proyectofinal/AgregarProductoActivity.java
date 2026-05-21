package com.miempresa.proyectofinal;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.miempresa.proyectofinal.DBHelper;

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
            String preTexto = precio.getText().toString();

            if(nom.isEmpty() || des.isEmpty() || preTexto.isEmpty()){

                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();

                return;
            }

            double pre = Double.parseDouble(preTexto);

            boolean insertado = dbHelper.insertarProducto(nom, des, pre);

            if(insertado){

                Toast.makeText(this, "Producto agregado", Toast.LENGTH_SHORT).show();

                finish();
            }
        });
    }
}