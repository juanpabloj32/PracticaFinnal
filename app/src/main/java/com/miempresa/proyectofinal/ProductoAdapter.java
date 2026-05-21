package com.miempresa.proyectofinal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ProductoAdapter extends ArrayAdapter<Producto> {

    Context context;
    ArrayList<Producto> listaProductos;

    public ProductoAdapter(Context context, ArrayList<Producto> listaProductos) {

        super(context, 0, listaProductos);

        this.context = context;
        this.listaProductos = listaProductos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){

            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_producto, parent, false);
        }

        Producto producto = listaProductos.get(position);

        TextView txtNombre = convertView.findViewById(R.id.txtNombre);
        TextView txtDescripcion = convertView.findViewById(R.id.txtDescripcion);
        TextView txtPrecio = convertView.findViewById(R.id.txtPrecio);

        txtNombre.setText(producto.getNombre());
        txtDescripcion.setText(producto.getDescripcion());
        txtPrecio.setText("$" + producto.getPrecio());

        return convertView;
    }
}
