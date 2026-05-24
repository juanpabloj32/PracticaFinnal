package com.miempresa.proyectofinal;

public class Producto {

    int id;
    String nombre;
    String descripcion;
    double precio;
    String imagen;
    int stock;

    public Producto() {
    }

    public Producto(int id,
                    String nombre,
                    String descripcion,
                    double precio,
                    String imagen,
                    int stock) {

        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagen = imagen;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public String getImagen() {
        return imagen;
    }

    public int getStock() {
        return stock;
    }
}