package com.miempresa.proyectofinal;

import android.content.Context;
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

public class AgregarProductoActivity extends AppCompatActivity {

    // MODIFICADO: Se añade "stock" a la declaración de variables
    EditText nombre, descripcion, precio, stock;

    Button guardar, seleccionarImagen;

    ImageView imagenProducto;

    DBHelper dbHelper;

    String rutaImagen = "";

    ActivityResultLauncher<String> seleccionarLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);

        nombre = findViewById(R.id.nombre);
        descripcion = findViewById(R.id.descripcion);
        precio = findViewById(R.id.precio);
        // NUEVO: Se conecta el componente del diseño con la variable Java
        stock = findViewById(R.id.stock);

        guardar = findViewById(R.id.guardar);

        seleccionarImagen = findViewById(R.id.seleccionarImagen);

        imagenProducto = findViewById(R.id.imagenProducto);

        dbHelper = new DBHelper(this);

        seleccionarLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {

                    if(uri != null){

                        try {

                            rutaImagen = guardarImagenInterna(uri);

                            Bitmap bitmap = BitmapFactory.decodeFile(rutaImagen);

                            imagenProducto.setImageBitmap(bitmap);

                            Toast.makeText(
                                    this,
                                    "Imagen seleccionada",
                                    Toast.LENGTH_SHORT
                            ).show();

                        } catch (Exception e){

                            Toast.makeText(
                                    this,
                                    "Error al cargar imagen",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }
                });

        seleccionarImagen.setOnClickListener(v -> {

            seleccionarLauncher.launch("image/*");
        });

        guardar.setOnClickListener(v -> {

            String nom = nombre.getText().toString().trim();
            String des = descripcion.getText().toString().trim();
            String preTexto = precio.getText().toString().trim();
            // NUEVO: Se obtiene el texto del campo de stock
            String stockTexto = stock.getText().toString().trim();

            // MODIFICADO: Se incluye stockTexto en la validación de campos vacíos
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
            int cantidad = Integer.parseInt(stockTexto);

            // MODIFICADO: Se pasa 'cantidad' como parámetro al insertar el producto
            boolean insertado = dbHelper.insertarProducto(
                    nom,
                    des,
                    pre,
                    rutaImagen,
                    cantidad
            );

            if(insertado){

                Toast.makeText(
                        this,
                        "Producto agregado",
                        Toast.LENGTH_SHORT
                ).show();

                finish();

            } else {

                Toast.makeText(
                        this,
                        "Error al guardar",
                        Toast.LENGTH_SHORT
                ).show();
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

            bitmap.compress(Bitmap.CompressFormat.JPEG,
                    90,
                    fos);

            fos.flush();
            fos.close();

            return archivo.getAbsolutePath();

        } catch (Exception e){

            e.printStackTrace();

            return "";
        }
    }
}