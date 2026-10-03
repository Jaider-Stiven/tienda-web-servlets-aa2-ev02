package com.tienda.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase Conexion - Gestiona la conexión a la base de datos MySQL.
 * Patrón Singleton para reutilizar la conexión.
 *
 * Evidencia GA7-220501096-AA2-EV02
 * @author Jaider Londoño
 * @version 1.0
 */
public class Conexion {

    // ─── Parámetros de conexión ────────────────────────────────────────────────
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL    = "jdbc:mysql://localhost:3306/tienda_db"
                                       + "?useSSL=false&serverTimezone=America/Bogota"
                                       + "&characterEncoding=UTF-8";
    private static final String USUARIO    = "root";
    private static final String CONTRASENA = "root";   // ← Cambia según tu configuración

    // ─── Instancia Singleton ───────────────────────────────────────────────────
    private static Conexion instancia = null;
    private Connection conexion       = null;

    /** Constructor privado - patrón Singleton */
    private Conexion() {
        try {
            Class.forName(DRIVER);
            this.conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            System.out.println("[Conexion] Conectado a la base de datos 'tienda_db'");
        } catch (ClassNotFoundException e) {
            System.err.println("[Conexion] Driver MySQL no encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("[Conexion] Error SQL al conectar: " + e.getMessage());
        }
    }

    /**
     * Devuelve la instancia única de la conexión.
     * @return instancia Singleton de Conexion
     */
    public static Conexion getInstancia() {
        if (instancia == null || instancia.conexion == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

    /**
     * Devuelve el objeto Connection para ejecutar consultas.
     * @return Connection activa
     * @throws SQLException si la conexión está cerrada
     */
    public Connection getConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            instancia = new Conexion();
        }
        return conexion;
    }

    /**
     * Cierra la conexión a la base de datos.
     */
    public void cerrar() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("[Conexion] Conexión cerrada.");
            }
        } catch (SQLException e) {
            System.err.println("[Conexion] Error al cerrar: " + e.getMessage());
        } finally {
            instancia = null;
        }
    }
}
