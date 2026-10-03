package com.tienda.modelo;

import java.io.Serializable;

/**
 * Clase Cliente - Modelo de datos para la entidad Cliente.
 * Evidencia GA7-220501096-AA2-EV02
 *
 * @author Jaider Londoño
 * @version 1.0
 */
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    // ─── Atributos ─────────────────────────────────────────────────────────────
    private int    idCliente;
    private String nombre;
    private String apellido;
    private String documento;
    private String correo;
    private String telefono;
    private String direccion;
    private boolean activo;

    // ─── Constructor vacío ─────────────────────────────────────────────────────
    public Cliente() {}

    // ─── Constructor completo ──────────────────────────────────────────────────
    public Cliente(int idCliente, String nombre, String apellido,
                   String documento, String correo, String telefono,
                   String direccion, boolean activo) {
        this.idCliente = idCliente;
        this.nombre    = nombre;
        this.apellido  = apellido;
        this.documento = documento;
        this.correo    = correo;
        this.telefono  = telefono;
        this.direccion = direccion;
        this.activo    = activo;
    }

    // ─── Métodos de negocio ────────────────────────────────────────────────────
    /** Devuelve el nombre completo del cliente */
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    // ─── Getters y Setters ────────────────────────────────────────────────────
    public int getIdCliente()                    { return idCliente; }
    public void setIdCliente(int id)             { this.idCliente = id; }

    public String getNombre()                    { return nombre; }
    public void setNombre(String nombre)         { this.nombre = nombre; }

    public String getApellido()                  { return apellido; }
    public void setApellido(String apellido)     { this.apellido = apellido; }

    public String getDocumento()                 { return documento; }
    public void setDocumento(String documento)   { this.documento = documento; }

    public String getCorreo()                    { return correo; }
    public void setCorreo(String correo)         { this.correo = correo; }

    public String getTelefono()                  { return telefono; }
    public void setTelefono(String telefono)     { this.telefono = telefono; }

    public String getDireccion()                 { return direccion; }
    public void setDireccion(String direccion)   { this.direccion = direccion; }

    public boolean isActivo()                    { return activo; }
    public void setActivo(boolean activo)        { this.activo = activo; }

    @Override
    public String toString() {
        return "Cliente{" +
               "idCliente=" + idCliente +
               ", nombre='" + getNombreCompleto() + '\'' +
               ", documento='" + documento + '\'' +
               '}';
    }
}
