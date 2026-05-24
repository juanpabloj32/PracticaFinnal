package com.miempresa.proyectofinal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class EditarProductoActivity extends AppCompatActivity {

    // MODIFICADO: Se añade "stockEditar" a la declaración de variables
    EditText nombreEditar, descripcionEditar, precioEditar, stockEditar;

    Button actualizar, cambiarImagen;

    ImageView imagenEditar;

    DBHelper dbHelper;

    int id;

    String rutaImagen = "";

    ActivityResultLauncher<String> seleccionarLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_producto);

        nombreEditar = findViewById(R.id.nombreEditar);
        descripcionEditar = findViewById(R.id.descripcionEditar);
        precioEditar = findViewById(R.id.precioEditar);
        // NUEVO: Se conecta el componente del diseño con la variable Java
        stockEditar = findViewById(R.id.stockEditar);

        actualizar = findViewById(R.id.actualizar);

        cambiarImagen = findViewById(R.id.cambiarImagen);

        imagenEditar = findViewById(R.id.imagenEditar);

        dbHelper = new DBHelper(this);

        id = getIntent().getIntExtra("id", 0);

        nombreEditar.setText(
                getIntent().getStringExtra("nombre")
        );

        descripcionEditar.setText(
                getIntent().getStringExtra("descripcion")
        );

        precioEditar.setText(
                String.valueOf(
                        getIntent().getDoubleExtra("precio", 0)
                )
        );

        rutaImagen = getIntent().getStringExtra("imagen");

        // NUEVO: Se carga el stock actual enviado desde el Intent
        stockEditar.setText(
                String.valueOf(
                        getIntent().getIntExtra("stock", 0)
                )
        );

        if(rutaImagen != null && !rutaImagen.isEmpty()){

            File file = new File(rutaImagen);

            if(file.exists()){

                Bitmap bitmap =
                        BitmapFactory.decodeFile(file.getAbsolutePath());

                imagenEditar.setImageBitmap(bitmap);
            }
        }

        seleccionarLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {

                    if(uri != null){

                        try {

                            rutaImagen = guardarImagenInterna(uri);

                            Bitmap bitmap =
                                    BitmapFactory.decodeFile(rutaImagen);

                            imagenEditar.setImageBitmap(bitmap);

                        } catch (Exception e){

                            Toast.makeText(
                                    this,
                                    "Error al cargar imagen",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }
                });

        cambiarImagen.setOnClickListener(v -> {

            seleccionarLauncher.launch("image/*");
        });

        actualizar.setOnClickListener(v -> {

            String nom =
                    nombreEditar.getText().toString().trim();

            String des =
                    descripcionEditar.getText().toString().trim();

            String preTexto =
                    precioEditar.getText().toString().trim();

            // NUEVO: Se obtiene el texto del campo de stock
            String stockTexto =
                    stockEditar.getText().toString().trim();

            // MODIFICADO: Se incluye stockTexto en la condición del IF
            if(nom.isEmpty() ||
                    des.isEmpty() ||
                    preTexto.isEmpty() ||
                    stockTexto.isEmpty()){

                Toast.makeText(
                        this,
                        "Completa todos los campos",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            double pre = Double.parseDouble(preTexto);
            // NUEVO: Se convierte la cadena de texto de stock a tipo entero
            int stock = Integer.parseInt(stockTexto);

            // MODIFICADO: Se pasa 'stock' como último parámetro para actualizar la BD
            boolean actualizado =
                    dbHelper.actualizarProducto(
                            id,
                            nom,
                            des,
                            pre,
                            rutaImagen,
                            stock
                    );

            if(actualizado){

                Toast.makeText(
                        this,
                        "Producto actualizado",
                        Toast.LENGTH_SHORT
                ).show();

                finish();
            }
        });
    }

    private String guardarImagenInterna(Uri uri){

        try {

            InputStream inputStream =
                    getContentResolver().openInputStream(uri);

            Bitmap bitmap =
                    BitmapFactory.decodeStream(inputStream);

            String nombreArchivo =
                    "img_" + System.currentTimeMillis() + ".jpg";

            File archivo =
                    new File(getFilesDir(), nombreArchivo);

            FileOutputStream fos =
                    new FileOutputStream(archivo);

            bitmap.compress(
                    Bitmap.CompressFormat.JPEG,
                    90,
                    fos
            );

            fos.flush();
            fos.close();

            return archivo.getAbsolutePath();

        } catch (Exception e){

            e.printStackTrace();

            return "";
        }
    }
}