package com.miempresa.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetalleProductoActivity extends AppCompatActivity {

    // MODIFICADO: Se añade txtStock a la declaración de variables
    TextView txtNombre, txtDescripcion, txtPrecio, txtStock;
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
        // NUEVO: Se conecta el componente del diseño con la variable Java
        txtStock = findViewById(R.id.txtStock);

        editar = findViewById(R.id.editar);
        eliminar = findViewById(R.id.eliminar);

        dbHelper = new DBHelper(this);

        Intent i = getIntent();

        id = i.getIntExtra("id", 0);

        txtNombre.setText(i.getStringExtra("nombre"));
        txtDescripcion.setText(i.getStringExtra("descripcion"));
        txtPrecio.setText("$" + i.getDoubleExtra("precio", 0));

        // NUEVO: Se muestra la cantidad de stock disponible en la interfaz
        txtStock.setText(
                "Stock disponible: " +
                        getIntent().getIntExtra("stock", 0)
        );

        eliminar.setOnClickListener(v -> {

            dbHelper.eliminarProducto(id);

            Toast.makeText(this, "Producto eliminado", Toast.LENGTH_SHORT).show();
            finish();
        });

        editar.setOnClickListener(v -> {

            Intent intent = new Intent(this, EditarProductoActivity.class);

            intent.putExtra("id", id);
            intent.putExtra("nombre", txtNombre.getText().toString());
            intent.putExtra("descripcion", txtDescripcion.getText().toString());
            intent.putExtra("precio", getIntent().getDoubleExtra("precio", 0));
            intent.putExtra("imagen", getIntent().getStringExtra("imagen"));

            // UNIFICADO: Envío del stock hacia la actividad de edición
            intent.putExtra(
                    "stock",
                    getIntent().getIntExtra("stock", 0)
            );

            startActivity(intent);
        });
    }
}