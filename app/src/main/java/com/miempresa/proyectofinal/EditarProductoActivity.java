package com.miempresa.proyectofinal;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditarProductoActivity extends AppCompatActivity {

    EditText nombre, descripcion, precio;
    Button actualizar;

    DBHelper dbHelper;

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_producto);

        nombre = findViewById(R.id.nombreEditar);
        descripcion = findViewById(R.id.descripcionEditar);
        precio = findViewById(R.id.precioEditar);
        actualizar = findViewById(R.id.actualizar);

        dbHelper = new DBHelper(this);

        id = getIntent().getIntExtra("id", 0);

        nombre.setText(getIntent().getStringExtra("nombre"));
        descripcion.setText(getIntent().getStringExtra("descripcion"));
        precio.setText(String.valueOf(
                getIntent().getDoubleExtra("precio", 0)
        ));

        actualizar.setOnClickListener(v -> {

            String nom = nombre.getText().toString();
            String des = descripcion.getText().toString();
            String preTexto = precio.getText().toString();

            if(nom.isEmpty() || des.isEmpty() || preTexto.isEmpty()){

                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            double pre = Double.parseDouble(preTexto);

            boolean actualizado = dbHelper.actualizarProducto(id, nom, des, pre);

            if(actualizado){

                Toast.makeText(this, "Producto actualizado", Toast.LENGTH_SHORT).show();

                finish();
            }
        });
    }
}