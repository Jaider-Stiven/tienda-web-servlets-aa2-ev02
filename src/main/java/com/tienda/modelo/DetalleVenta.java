package com.tienda.modelo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Clase DetalleVenta - Línea de detalle dentro de una venta.
 * Evidencia GA7-220501096-AA2-EV02
 *
 * @author Jaider Londoño
 * @version 1.0
 */
public class DetalleVenta implements Serializable {

    private static final long serialVersionUID = 1L;

    // ─── Atributos ─────────────────────────────────────────────────────────────
    private int        idDetalle;
    private int        idVenta;
    private int        idProducto;
    private String     nombreProducto;
    private int        cantidad;
    private BigDecimal precioUnitario;

    // ─── Constructor vacío ─────────────────────────────────────────────────────
    public DetalleVenta() {}

    // ─── Constructor completo ──────────────────────────────────────────────────
    public DetalleVenta(int idVenta, int idProducto, int cantidad,
                        BigDecimal precioUnitario) {
        this.idVenta        = idVenta;
        this.idProducto     = idProducto;
        this.cantidad       = cantidad;
        this.precioUnitario = precioUnitario;
    }

    // ─── Métodos calculados ────────────────────────────────────────────────────
    /**
     * Calcula el subtotal del detalle: cantidad × precio unitario.
     * @return subtotal como BigDecimal
     */
    public BigDecimal getSubtotal() {
        if (precioUnitario == null) return BigDecimal.ZERO;
        return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }

    // ─── Getters y Setters ────────────────────────────────────────────────────
    public int getIdDetalle()                          { return idDetalle; }
    public void setIdDetalle(int id)                   { this.idDetalle = id; }

    public int getIdVenta()                            { return idVenta; }
    public void setIdVenta(int id)                     { this.idVenta = id; }

    public int getIdProducto()                         { return idProducto; }
    public void setIdProducto(int id)                  { this.idProducto = id; }

    public String getNombreProducto()                  { return nombreProducto; }
    public void setNombreProducto(String n)            { this.nombreProducto = n; }

    public int getCantidad()                           { return cantidad; }
    public void setCantidad(int cantidad)              { this.cantidad = cantidad; }

    public BigDecimal getPrecioUnitario()              { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precio)   { this.precioUnitario = precio; }
}
