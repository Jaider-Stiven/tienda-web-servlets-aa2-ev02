package com.tienda.dao;

import com.tienda.modelo.Producto;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ProductoDAO - Acceso a datos para la entidad Producto.
 * Implementa las operaciones CRUD mediante JDBC.
 *
 * Evidencia GA7-220501096-AA2-EV02
 * @author Jaider Londoño
 * @version 1.0
 */
public class ProductoDAO {

    // ─── SQL ───────────────────────────────────────────────────────────────────
    private static final String SQL_LISTAR =
        "SELECT p.id_producto, p.codigo, p.nombre, p.descripcion, " +
        "       p.precio, p.stock, p.id_categoria, p.activo, c.nombre AS categoria " +
        "FROM productos p " +
        "LEFT JOIN categorias c ON p.id_categoria = c.id_categoria " +
        "ORDER BY p.nombre";

    private static final String SQL_BUSCAR_ID =
        "SELECT p.*, c.nombre AS categoria FROM productos p " +
        "LEFT JOIN categorias c ON p.id_categoria = c.id_categoria " +
        "WHERE p.id_producto = ?";

    private static final String SQL_BUSCAR_NOMBRE =
        "SELECT p.*, c.nombre AS categoria FROM productos p " +
        "LEFT JOIN categorias c ON p.id_categoria = c.id_categoria " +
        "WHERE p.nombre LIKE ? ORDER BY p.nombre";

    private static final String SQL_INSERTAR =
        "INSERT INTO productos (codigo, nombre, descripcion, precio, stock, id_categoria) " +
        "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SQL_ACTUALIZAR =
        "UPDATE productos SET codigo=?, nombre=?, descripcion=?, " +
        "precio=?, stock=?, id_categoria=? WHERE id_producto=?";

    private static final String SQL_ELIMINAR =
        "UPDATE productos SET activo = 0 WHERE id_producto = ?";

    // ─── Listar todos ──────────────────────────────────────────────────────────
    /**
     * Obtiene todos los productos activos de la base de datos.
     * Usa HTTP GET → método doGet del servlet
     *
     * @return Lista de productos
     */
    public List<Producto> listarTodos() {
        List<Producto> lista = new ArrayList<>();
        try {
            Connection con = Conexion.getInstancia().getConexion();
            PreparedStatement ps = con.prepareStatement(SQL_LISTAR);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(mapearProducto(rs));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.err.println("[ProductoDAO.listarTodos] " + e.getMessage());
        }
        return lista;
    }

    // ─── Buscar por ID ─────────────────────────────────────────────────────────
    /**
     * Busca un producto por su ID.
     * @param id identificador del producto
     * @return Producto encontrado o null
     */
    public Producto buscarPorId(int id) {
        Producto producto = null;
        try {
            Connection con = Conexion.getInstancia().getConexion();
            PreparedStatement ps = con.prepareStatement(SQL_BUSCAR_ID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                producto = mapearProducto(rs);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.err.println("[ProductoDAO.buscarPorId] " + e.getMessage());
        }
        return producto;
    }

    // ─── Buscar por nombre ─────────────────────────────────────────────────────
    /**
     * Busca productos cuyo nombre contenga el texto indicado (LIKE).
     * @param nombre texto a buscar
     * @return lista de productos que coinciden
     */
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> lista = new ArrayList<>();
        try {
            Connection con = Conexion.getInstancia().getConexion();
            PreparedStatement ps = con.prepareStatement(SQL_BUSCAR_NOMBRE);
            ps.setString(1, "%" + nombre + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(mapearProducto(rs));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.err.println("[ProductoDAO.buscarPorNombre] " + e.getMessage());
        }
        return lista;
    }

    // ─── Insertar ──────────────────────────────────────────────────────────────
    /**
     * Registra un nuevo producto en la base de datos.
     * Usa HTTP POST → método doPost del servlet
     *
     * @param producto datos del nuevo producto
     * @return true si la inserción fue exitosa
     */
    public boolean insertar(Producto producto) {
        boolean resultado = false;
        try {
            Connection con = Conexion.getInstancia().getConexion();
            PreparedStatement ps = con.prepareStatement(SQL_INSERTAR);

            ps.setString(1,     producto.getCodigo());
            ps.setString(2,     producto.getNombre());
            ps.setString(3,     producto.getDescripcion());
            ps.setBigDecimal(4, producto.getPrecio());
            ps.setInt(5,        producto.getStock());
            ps.setInt(6,        producto.getIdCategoria());

            resultado = ps.executeUpdate() > 0;
            ps.close();
        } catch (SQLException e) {
            System.err.println("[ProductoDAO.insertar] " + e.getMessage());
        }
        return resultado;
    }

    // ─── Actualizar ────────────────────────────────────────────────────────────
    /**
     * Actualiza los datos de un producto existente.
     * Usa HTTP POST con parámetro accion=editar
     *
     * @param producto datos actualizados
     * @return true si la actualización fue exitosa
     */
    public boolean actualizar(Producto producto) {
        boolean resultado = false;
        try {
            Connection con = Conexion.getInstancia().getConexion();
            PreparedStatement ps = con.prepareStatement(SQL_ACTUALIZAR);

            ps.setString(1,     producto.getCodigo());
            ps.setString(2,     producto.getNombre());
            ps.setString(3,     producto.getDescripcion());
            ps.setBigDecimal(4, producto.getPrecio());
            ps.setInt(5,        producto.getStock());
            ps.setInt(6,        producto.getIdCategoria());
            ps.setInt(7,        producto.getIdProducto());

            resultado = ps.executeUpdate() > 0;
            ps.close();
        } catch (SQLException e) {
            System.err.println("[ProductoDAO.actualizar] " + e.getMessage());
        }
        return resultado;
    }

    // ─── Eliminar (baja lógica) ────────────────────────────────────────────────
    /**
     * Realiza una baja lógica del producto (activo = 0).
     * @param id identificador del producto a eliminar
     * @return true si se realizó correctamente
     */
    public boolean eliminar(int id) {
        boolean resultado = false;
        try {
            Connection con = Conexion.getInstancia().getConexion();
            PreparedStatement ps = con.prepareStatement(SQL_ELIMINAR);
            ps.setInt(1, id);
            resultado = ps.executeUpdate() > 0;
            ps.close();
        } catch (SQLException e) {
            System.err.println("[ProductoDAO.eliminar] " + e.getMessage());
        }
        return resultado;
    }

    // ─── Mapeo ResultSet → Producto ────────────────────────────────────────────
    private Producto mapearProducto(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setIdProducto(rs.getInt("id_producto"));
        p.setCodigo(rs.getString("codigo"));
        p.setNombre(rs.getString("nombre"));
        p.setDescripcion(rs.getString("descripcion"));
        p.setPrecio(rs.getBigDecimal("precio"));
        p.setStock(rs.getInt("stock"));
        p.setIdCategoria(rs.getInt("id_categoria"));
        p.setActivo(rs.getBoolean("activo"));
        // columna join
        String cat = rs.getString("categoria");
        p.setNombreCategoria(cat != null ? cat : "Sin categoría");
        return p;
    }
}
