package com.tienda.dao;

import com.tienda.modelo.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ClienteDAO - Acceso a datos para la entidad Cliente.
 * Implementa las operaciones CRUD mediante JDBC.
 *
 * Evidencia GA7-220501096-AA2-EV02
 * @author Jaider Londoño
 * @version 1.0
 */
public class ClienteDAO {

    private static final String SQL_LISTAR =
        "SELECT * FROM clientes WHERE activo = 1 ORDER BY apellido, nombre";

    private static final String SQL_BUSCAR_ID =
        "SELECT * FROM clientes WHERE id_cliente = ?";

    private static final String SQL_INSERTAR =
        "INSERT INTO clientes (nombre, apellido, documento, correo, telefono, direccion) " +
        "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SQL_ACTUALIZAR =
        "UPDATE clientes SET nombre=?, apellido=?, documento=?, " +
        "correo=?, telefono=?, direccion=? WHERE id_cliente=?";

    private static final String SQL_ELIMINAR =
        "UPDATE clientes SET activo = 0 WHERE id_cliente = ?";

    // ─── Listar todos ──────────────────────────────────────────────────────────
    public List<Cliente> listarTodos() {
        List<Cliente> lista = new ArrayList<>();
        try {
            Connection con = Conexion.getInstancia().getConexion();
            PreparedStatement ps = con.prepareStatement(SQL_LISTAR);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearCliente(rs));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.err.println("[ClienteDAO.listarTodos] " + e.getMessage());
        }
        return lista;
    }

    // ─── Buscar por ID ─────────────────────────────────────────────────────────
    public Cliente buscarPorId(int id) {
        Cliente cliente = null;
        try {
            Connection con = Conexion.getInstancia().getConexion();
            PreparedStatement ps = con.prepareStatement(SQL_BUSCAR_ID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cliente = mapearCliente(rs);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.err.println("[ClienteDAO.buscarPorId] " + e.getMessage());
        }
        return cliente;
    }

    // ─── Insertar ──────────────────────────────────────────────────────────────
    public boolean insertar(Cliente cliente) {
        boolean resultado = false;
        try {
            Connection con = Conexion.getInstancia().getConexion();
            PreparedStatement ps = con.prepareStatement(SQL_INSERTAR);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getDocumento());
            ps.setString(4, cliente.getCorreo());
            ps.setString(5, cliente.getTelefono());
            ps.setString(6, cliente.getDireccion());
            resultado = ps.executeUpdate() > 0;
            ps.close();
        } catch (SQLException e) {
            System.err.println("[ClienteDAO.insertar] " + e.getMessage());
        }
        return resultado;
    }

    // ─── Actualizar ────────────────────────────────────────────────────────────
    public boolean actualizar(Cliente cliente) {
        boolean resultado = false;
        try {
            Connection con = Conexion.getInstancia().getConexion();
            PreparedStatement ps = con.prepareStatement(SQL_ACTUALIZAR);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getDocumento());
            ps.setString(4, cliente.getCorreo());
            ps.setString(5, cliente.getTelefono());
            ps.setString(6, cliente.getDireccion());
            ps.setInt(7,    cliente.getIdCliente());
            resultado = ps.executeUpdate() > 0;
            ps.close();
        } catch (SQLException e) {
            System.err.println("[ClienteDAO.actualizar] " + e.getMessage());
        }
        return resultado;
    }

    // ─── Eliminar (baja lógica) ────────────────────────────────────────────────
    public boolean eliminar(int id) {
        boolean resultado = false;
        try {
            Connection con = Conexion.getInstancia().getConexion();
            PreparedStatement ps = con.prepareStatement(SQL_ELIMINAR);
            ps.setInt(1, id);
            resultado = ps.executeUpdate() > 0;
            ps.close();
        } catch (SQLException e) {
            System.err.println("[ClienteDAO.eliminar] " + e.getMessage());
        }
        return resultado;
    }

    // ─── Mapeo ─────────────────────────────────────────────────────────────────
    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setIdCliente(rs.getInt("id_cliente"));
        c.setNombre(rs.getString("nombre"));
        c.setApellido(rs.getString("apellido"));
        c.setDocumento(rs.getString("documento"));
        c.setCorreo(rs.getString("correo"));
        c.setTelefono(rs.getString("telefono"));
        c.setDireccion(rs.getString("direccion"));
        c.setActivo(rs.getBoolean("activo"));
        return c;
    }
}
