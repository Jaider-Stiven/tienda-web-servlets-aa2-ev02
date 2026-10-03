package com.tienda.modelo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Clase Producto - Modelo de datos para la entidad Producto.
 * Evidencia GA7-220501096-AA2-EV02
 * 
 * @author Jaider Londoño
 * @version 1.0
 */
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    // Atributos del modelo
    private int    idProducto;
    private String codigo;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private int    stock;
    private int    idCategoria;
    private String nombreCategoria;
    private boolean activo;

    // ─── Constructores ────────────────────────────────────────────────────────

    /** Constructor vacío requerido por frameworks */
    public Producto() {}

    /** Constructor completo */
    public Producto(int idProducto, String codigo, String nombre,
                    String descripcion, BigDecimal precio, int stock,
                    int idCategoria, boolean activo) {
        this.idProducto  = idProducto;
        this.codigo      = codigo;
        this.nombre      = nombre;
        this.descripcion = descripcion;
        this.precio      = precio;
        this.stock       = stock;
        this.idCategoria = idCategoria;
        this.activo      = activo;
    }

    // ─── Getters y Setters ────────────────────────────────────────────────────

    public int getIdProducto()              { return idProducto; }
    public void setIdProducto(int id)       { this.idProducto = id; }

    public String getCodigo()               { return codigo; }
    public void setCodigo(String codigo)    { this.codigo = codigo; }

    public String getNombre()               { return nombre; }
    public void setNombre(String nombre)    { this.nombre = nombre; }

    public String getDescripcion()                { return descripcion; }
    public void setDescripcion(String desc)       { this.descripcion = desc; }

    public BigDecimal getPrecio()                 { return precio; }
    public void setPrecio(BigDecimal precio)      { this.precio = precio; }

    public int getStock()                         { return stock; }
    public void setStock(int stock)               { this.stock = stock; }

    public int getIdCategoria()                   { return idCategoria; }
    public void setIdCategoria(int idCategoria)   { this.idCategoria = idCategoria; }

    public String getNombreCategoria()            { return nombreCategoria; }
    public void setNombreCategoria(String n)      { this.nombreCategoria = n; }

    public boolean isActivo()                     { return activo; }
    public void setActivo(boolean activo)         { this.activo = activo; }

    // ─── Métodos de negocio ────────────────────────────────────────────────────

    /**
     * Verifica si el producto tiene stock disponible.
     * @return true si hay al menos 1 unidad en stock
     */
    public boolean tieneStock() {
        return this.stock > 0;
    }

    /**
     * Verifica si el stock es bajo (menos de 5 unidades).
     * @return true si el stock es menor a 5
     */
    public boolean stockBajo() {
        return this.stock < 5;
    }

    @Override
    public String toString() {
        return "Producto{" +
               "idProducto=" + idProducto +
               ", codigo='" + codigo + '\'' +
               ", nombre='" + nombre + '\'' +
               ", precio=" + precio +
               ", stock=" + stock +
               '}';
    }
}
