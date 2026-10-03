package com.tienda.servlet;

import com.tienda.dao.ClienteDAO;
import com.tienda.dao.ProductoDAO;
import com.tienda.modelo.Cliente;
import com.tienda.modelo.Producto;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * IndexServlet - Servlet principal que gestiona el dashboard de inicio.
 *
 * Métodos implementados:
 *   - doGet():  Carga estadísticas y redirige al index.jsp
 *
 * URL: /inicio
 *
 * Evidencia GA7-220501096-AA2-EV02
 * @author Jaider Londoño
 * @version 1.0
 */
@WebServlet(name = "IndexServlet", urlPatterns = {"/inicio", ""})
public class IndexServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ProductoDAO productoDAO;
    private ClienteDAO  clienteDAO;

    @Override
    public void init() throws ServletException {
        productoDAO = new ProductoDAO();
        clienteDAO  = new ClienteDAO();
    }

    /**
     * Método GET: Carga datos del dashboard (conteo de productos, clientes).
     * Redirige la vista al archivo index.jsp.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener datos para el dashboard
        List<Producto> productos = productoDAO.listarTodos();
        List<Cliente>  clientes  = clienteDAO.listarTodos();

        // Contar productos con stock bajo
        long stockBajo = productos.stream()
                .filter(Producto::stockBajo)
                .count();

        // Pasar datos al JSP mediante atributos del request
        request.setAttribute("totalProductos", productos.size());
        request.setAttribute("totalClientes",  clientes.size());
        request.setAttribute("stockBajo",      stockBajo);
        request.setAttribute("productos",      productos.subList(0, Math.min(5, productos.size())));

        // Forward al JSP de inicio
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
        rd.forward(request, response);
    }
}
