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
        // CAMBIO: Versión de la base de datos actualizada a 2
        super(context, DBNAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // CAMBIO: Se agregó la columna "imagen TEXT" a la tabla
        db.execSQL("CREATE TABLE productos(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT," +
                "descripcion TEXT," +
                "precio REAL," +
                "imagen TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Al subir la versión a 2, borramos la tabla vieja para que se cree con la nueva estructura
        db.execSQL("DROP TABLE IF EXISTS productos");
        onCreate(db);
    }

    // CAMBIO: Método adaptado con el parámetro String imagen
    public boolean insertarProducto(String nombre, String descripcion, double precio, String imagen){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("nombre", nombre);
        values.put("descripcion", descripcion);
        values.put("precio", precio);
        // CAMBIO: Inserción de la URI de la imagen en la base de datos
        values.put("imagen", imagen);

        long result = db.insert("productos", null, values);

        return result != -1;
    }

    public ArrayList<Producto> obtenerProductos(){

        ArrayList<Producto> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM productos", null);

        while(cursor.moveToNext()){

            // CAMBIO: Se lee la columna índice 4 (imagen) y se envía al constructor de Producto
            Producto p = new Producto(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getDouble(3),
                    cursor.getString(4)
            );

            lista.add(p);
        }
        cursor.close(); // Buena práctica para liberar memoria del cursor

        return lista;
    }

    public boolean actualizarProducto(int id, String nombre, String descripcion, double precio){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("nombre", nombre);
        values.put("descripcion", descripcion);
        values.put("precio", precio);

        long result = db.update("productos", values, "id=?", new String[]{String.valueOf(id)});

        return result != -1;
    }

    public boolean eliminarProducto(int id){

        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("productos", "id=?", new String[]{String.valueOf(id)});

        return result != -1;
    }
}