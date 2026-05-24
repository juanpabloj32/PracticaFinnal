package com.miempresa.proyectofinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "Tienda.db";

    public DBHelper(Context context) {
        // CAMBIO: Versión de la base de datos actualizada a 3
        super(context, DBNAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // CAMBIO: Se agregó la columna "stock INTEGER" a la tabla productos
        db.execSQL("CREATE TABLE productos(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT," +
                "descripcion TEXT," +
                "precio REAL," +
                "imagen TEXT," +
                "stock INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Al subir la versión a 3, eliminamos la tabla vieja para que se recree con la nueva estructura
        db.execSQL("DROP TABLE IF EXISTS productos");
        onCreate(db);
    }

    // CAMBIO: Método adaptado con el parámetro int stock e inserción de este valor
    public boolean insertarProducto(String nombre, String descripcion, double precio, String imagen, int stock){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("nombre", nombre);
        values.put("descripcion", descripcion);
        values.put("precio", precio);
        values.put("imagen", imagen);
        values.put("stock", stock);

        long result = db.insert("productos", null, values);

        return result != -1;
    }

    public ArrayList<Producto> obtenerProductos(){

        ArrayList<Producto> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM productos", null);

        while(cursor.moveToNext()){

            // CAMBIO: Se lee la columna índice 5 (stock) y se añade al constructor de Producto
            Producto p = new Producto(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getDouble(3),
                    cursor.getString(4),
                    cursor.getInt(5)
            );

            lista.add(p);
        }
        cursor.close();

        return lista;
    }

    // CAMBIO: Método adaptado para recibir y actualizar también el parámetro 'stock'
    public boolean actualizarProducto(int id, String nombre, String descripcion, double precio, String imagen, int stock){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("nombre", nombre);
        values.put("descripcion", descripcion);
        values.put("precio", precio);
        values.put("imagen", imagen);
        values.put("stock", stock);

        long result = db.update(
                "productos",
                values,
                "id=?",
                new String[]{String.valueOf(id)}
        );

        return result != -1;
    }

    public boolean eliminarProducto(int id){

        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("productos", "id=?", new String[]{String.valueOf(id)});

        return result != -1;
    }
}