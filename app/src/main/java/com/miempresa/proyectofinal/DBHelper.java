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
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE productos(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT," +
                "descripcion TEXT," +
                "precio REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertarProducto(String nombre, String descripcion, double precio){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("nombre", nombre);
        values.put("descripcion", descripcion);
        values.put("precio", precio);

        long result = db.insert("productos", null, values);

        return result != -1;
    }

    public ArrayList<Producto> obtenerProductos(){

        ArrayList<Producto> lista = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM productos", null);

        while(cursor.moveToNext()){

            Producto p = new Producto(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getDouble(3)
            );

            lista.add(p);
        }

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
