package com.miempresa.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

        id = i.getIntExtra("id", 0);

        txtNombre.setText(i.getStringExtra("nombre"));
        txtDescripcion.setText(i.getStringExtra("descripcion"));
        txtPrecio.setText("$" + i.getDoubleExtra("precio", 0));

        eliminar.setOnClickListener(v -> {

            dbHelper.eliminarProducto(id);

            Toast.makeText(this, "Producto eliminado", Toast.LENGTH_SHORT).show();
            finish();
        });

        // Código unificado: Ahora envía id, nombre, descripción y el precio limpio como Double
        editar.setOnClickListener(v -> {

            Intent intent = new Intent(this, EditarProductoActivity.class);

            intent.putExtra("id", id);
            intent.putExtra("nombre", txtNombre.getText().toString());
            intent.putExtra("descripcion", txtDescripcion.getText().toString());

            String precioTexto = txtPrecio.getText().toString().replace("$", "");
            intent.putExtra("precio", Double.parseDouble(precioTexto));

            startActivity(intent);
        });
    }
}