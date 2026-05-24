package com.miempresa.proyectofinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.util.ArrayList;

public class ProductoAdapter extends ArrayAdapter<Producto> {

    Context context;
    ArrayList<Producto> listaProductos;

    public ProductoAdapter(Context context,
                           ArrayList<Producto> listaProductos) {

        super(context, 0, listaProductos);

        this.context = context;
        this.listaProductos = listaProductos;
    }

    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {

        if(convertView == null){

            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_producto,
                            parent,
                            false);
        }

        Producto producto = listaProductos.get(position);

        TextView txtNombre =
                convertView.findViewById(R.id.txtNombre);

        TextView txtDescripcion =
                convertView.findViewById(R.id.txtDescripcion);

        TextView txtPrecio =
                convertView.findViewById(R.id.txtPrecio);

        // NUEVO: Se conecta el componente del diseño de la fila (item_producto)
        TextView txtStock =
                convertView.findViewById(R.id.txtStock);

        ImageView imgProducto =
                convertView.findViewById(R.id.imgProducto);

        txtNombre.setText(producto.getNombre());

        txtDescripcion.setText(producto.getDescripcion());

        txtPrecio.setText("$" + producto.getPrecio());

        // NUEVO: Se asigna la cantidad de stock en la vista de la fila
        txtStock.setText(
                "Stock: " + producto.getStock()
        );

        if(producto.getImagen() != null &&
                !producto.getImagen().isEmpty()){

            File file = new File(producto.getImagen());

            if(file.exists()){

                Bitmap bitmap =
                        BitmapFactory.decodeFile(
                                file.getAbsolutePath()
                        );

                imgProducto.setImageBitmap(bitmap);
            }
        }

        return convertView;
    }
}