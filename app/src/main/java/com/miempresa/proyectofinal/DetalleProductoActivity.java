package com.miempresa.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetalleProductoActivity extends AppCompatActivity {

    TextView txtNombre, txtDescripcion, txtPrecio;
    Button editar, eliminar;

    DBHelper dbHelper;

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);

        txtNombre = findViewById(R.id.txtNombre);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        txtPrecio = findViewById(R.id.txtPrecio);

        editar = findViewById(R.id.editar);
        eliminar = findViewById(R.id.eliminar);

        dbHelper = new DBHelper(this);

        Intent i = getIntent();

        id = i.getIntExtra("id",0);

        txtNombre.setText(i.getStringExtra("nombre"));
        txtDescripcion.setText(i.getStringExtra("descripcion"));
        txtPrecio.setText("$" + i.getDoubleExtra("precio",0));

        eliminar.setOnClickListener(v -> {

            dbHelper.eliminarProducto(id);

            finish();
        });

        editar.setOnClickListener(v -> {

            Intent intent = new Intent(this, EditarProductoActivity.class);

            intent.putExtra("id", id);

            startActivity(intent);
        });
    }
}