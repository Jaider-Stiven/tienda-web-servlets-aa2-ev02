package com.tienda.servlet;

import com.tienda.dao.ProductoDAO;
import com.tienda.modelo.Producto;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * ProductoServlet - Servlet que gestiona el CRUD de Productos.
 *
 * Métodos implementados:
 *   - doGet():  Listar, buscar y mostrar formularios (acción via parámetro ?accion=...)
 *   - doPost(): Guardar nuevo producto, actualizar existente, eliminar
 *
 * URL: /productos
 *
 * Evidencia GA7-220501096-AA2-EV02
 * @author Jaider Londoño
 * @version 1.0
 */
@WebServlet(name = "ProductoServlet", urlPatterns = "/productos")
public class ProductoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ProductoDAO productoDAO;

    @Override
    public void init() throws ServletException {
        productoDAO = new ProductoDAO();
    }

    // ═══════════════════════════════════════════════════════════════════════════
    //  MÉTODO GET: Listar / Buscar / Mostrar formulario
    // ═══════════════════════════════════════════════════════════════════════════
    /**
     * Maneja solicitudes HTTP GET.
     * Acciones disponibles via ?accion=valor:
     *   - listar    (por defecto): muestra la lista de productos
     *   - nuevo     : muestra el formulario de registro vacío
     *   - editar    : muestra el formulario con datos del producto (requiere ?id=N)
     *   - eliminar  : realiza baja lógica (requiere ?id=N)
     *   - buscar    : filtra productos por nombre (requiere ?nombre=texto)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");
        if (accion == null) accion = "listar";

        switch (accion) {
            case "nuevo":
                mostrarFormularioNuevo(request, response);
                break;

            case "editar":
                mostrarFormularioEditar(request, response);
                break;

            case "eliminar":
                eliminarProducto(request, response);
                break;

            case "buscar":
                buscarProductos(request, response);
                break;

            default:
                listarProductos(request, response);
                break;
        }
    }

    // ═══════════════════════════════════════════════════════════════════════════
    //  MÉTODO POST: Guardar / Actualizar
    // ═══════════════════════════════════════════════════════════════════════════
    /**
     * Maneja solicitudes HTTP POST.
     * Acciones disponibles via campo oculto "accion":
     *   - guardar   : inserta un nuevo producto
     *   - actualizar: actualiza un producto existente
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");
        if (accion == null) accion = "";

        switch (accion) {
            case "guardar":
                guardarProducto(request, response);
                break;

            case "actualizar":
                actualizarProducto(request, response);
                break;

            default:
                response.sendRedirect(request.getContextPath() + "/productos");
                break;
        }
    }

    // ─── Handlers privados ─────────────────────────────────────────────────────

    /** Lista todos los productos activos */
    private void listarProductos(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        List<Producto> lista = productoDAO.listarTodos();
        req.setAttribute("productos", lista);
        req.setAttribute("titulo", "Gestión de Productos");
        forward(req, res, "/WEB-INF/jsp/productos/lista.jsp");
    }

    /** Muestra el formulario de registro vacío */
    private void mostrarFormularioNuevo(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.setAttribute("titulo", "Registrar Producto");
        req.setAttribute("producto", new Producto());
        req.setAttribute("accion", "guardar");
        forward(req, res, "/WEB-INF/jsp/productos/formulario.jsp");
    }

    /** Muestra el formulario de edición con datos del producto */
    private void mostrarFormularioEditar(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Producto producto = productoDAO.buscarPorId(id);

        if (producto == null) {
            setMensaje(req, "error", "Producto no encontrado.");
            res.sendRedirect(req.getContextPath() + "/productos");
            return;
        }

        req.setAttribute("titulo",   "Editar Producto");
        req.setAttribute("producto", producto);
        req.setAttribute("accion",   "actualizar");
        forward(req, res, "/WEB-INF/jsp/productos/formulario.jsp");
    }

    /** Busca productos por nombre */
    private void buscarProductos(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String nombre = req.getParameter("nombre");
        List<Producto> lista = (nombre != null && !nombre.trim().isEmpty())
                ? productoDAO.buscarPorNombre(nombre.trim())
                : productoDAO.listarTodos();

        req.setAttribute("productos", lista);
        req.setAttribute("busqueda",  nombre);
        req.setAttribute("titulo",    "Resultados de Búsqueda");
        forward(req, res, "/WEB-INF/jsp/productos/lista.jsp");
    }

    /** Inserta un nuevo producto (POST) */
    private void guardarProducto(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        Producto producto = construirProducto(req);
        boolean ok = productoDAO.insertar(producto);

        HttpSession session = req.getSession();
        if (ok) {
            session.setAttribute("mensajeExito",
                "✅ Producto '" + producto.getNombre() + "' registrado correctamente.");
        } else {
            session.setAttribute("mensajeError",
                "❌ Error al registrar el producto. Verifica los datos.");
        }
        res.sendRedirect(req.getContextPath() + "/productos");
    }

    /** Actualiza un producto existente (POST) */
    private void actualizarProducto(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        Producto producto = construirProducto(req);
        producto.setIdProducto(Integer.parseInt(req.getParameter("idProducto")));

        boolean ok = productoDAO.actualizar(producto);

        HttpSession session = req.getSession();
        if (ok) {
            session.setAttribute("mensajeExito",
                "✅ Producto '" + producto.getNombre() + "' actualizado correctamente.");
        } else {
            session.setAttribute("mensajeError",
                "❌ Error al actualizar el producto.");
        }
        res.sendRedirect(req.getContextPath() + "/productos");
    }

    /** Realiza baja lógica de un producto (GET con ?accion=eliminar&id=N) */
    private void eliminarProducto(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        boolean ok = productoDAO.eliminar(id);

        HttpSession session = req.getSession();
        if (ok) {
            session.setAttribute("mensajeExito", "✅ Producto eliminado correctamente.");
        } else {
            session.setAttribute("mensajeError", "❌ Error al eliminar el producto.");
        }
        res.sendRedirect(req.getContextPath() + "/productos");
    }

    // ─── Utilidades ────────────────────────────────────────────────────────────

    /** Construye un objeto Producto desde los parámetros del formulario */
    private Producto construirProducto(HttpServletRequest req) {
        Producto p = new Producto();
        p.setCodigo(req.getParameter("codigo").trim().toUpperCase());
        p.setNombre(req.getParameter("nombre").trim());
        p.setDescripcion(req.getParameter("descripcion") != null
                ? req.getParameter("descripcion").trim() : "");
        p.setPrecio(new BigDecimal(req.getParameter("precio")));
        p.setStock(Integer.parseInt(req.getParameter("stock")));
        p.setIdCategoria(Integer.parseInt(req.getParameter("idCategoria")));
        return p;
    }

    /** Pone un mensaje en el request */
    private void setMensaje(HttpServletRequest req, String tipo, String texto) {
        req.setAttribute("mensaje" + (tipo.equals("error") ? "Error" : "Exito"), texto);
    }

    /** Delega la vista a un JSP */
    private void forward(HttpServletRequest req, HttpServletResponse res, String jsp)
            throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher(jsp);
        rd.forward(req, res);
    }
}
