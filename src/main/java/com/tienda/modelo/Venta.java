package com.tienda.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Clase Venta - Modelo de datos para la entidad Venta.
 * Evidencia GA7-220501096-AA2-EV02
 *
 * @author Jaider Londoño
 * @version 1.0
 */
public class Venta implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum Estado { PENDIENTE, PAGADA, CANCELADA }

    // ─── Atributos ─────────────────────────────────────────────────────────────
    private int             idVenta;
    private int             idCliente;
    private String          nombreCliente;
    private LocalDateTime   fechaVenta;
    private BigDecimal      total;
    private Estado          estado;
    private String          observaciones;
    private List<DetalleVenta> detalles;

    // ─── Constructor vacío ─────────────────────────────────────────────────────
    public Venta() {}

    // ─── Constructor completo ──────────────────────────────────────────────────
    public Venta(int idVenta, int idCliente, LocalDateTime fechaVenta,
                 BigDecimal total, Estado estado) {
        this.idVenta    = idVenta;
        this.idCliente  = idCliente;
        this.fechaVenta = fechaVenta;
        this.total      = total;
        this.estado     = estado;
    }

    // ─── Getters y Setters ────────────────────────────────────────────────────
    public int getIdVenta()                     { return idVenta; }
    public void setIdVenta(int id)              { this.idVenta = id; }

    public int getIdCliente()                   { return idCliente; }
    public void setIdCliente(int id)            { this.idCliente = id; }

    public String getNombreCliente()            { return nombreCliente; }
    public void setNombreCliente(String n)      { this.nombreCliente = n; }

    public LocalDateTime getFechaVenta()        { return fechaVenta; }
    public void setFechaVenta(LocalDateTime f)  { this.fechaVenta = f; }

    public BigDecimal getTotal()                { return total; }
    public void setTotal(BigDecimal total)      { this.total = total; }

    public Estado getEstado()                   { return estado; }
    public void setEstado(Estado estado)        { this.estado = estado; }

    public String getObservaciones()            { return observaciones; }
    public void setObservaciones(String obs)    { this.observaciones = obs; }

    public List<DetalleVenta> getDetalles()              { return detalles; }
    public void setDetalles(List<DetalleVenta> detalles) { this.detalles = detalles; }

    @Override
    public String toString() {
        return "Venta{idVenta=" + idVenta + ", cliente=" + nombreCliente +
               ", total=" + total + ", estado=" + estado + '}';
    }
}
