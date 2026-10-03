package com.tienda.servlet;

import com.tienda.dao.ClienteDAO;
import com.tienda.modelo.Cliente;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

/**
 * ClienteServlet - Servlet que gestiona el CRUD de Clientes.
 *
 * Métodos implementados:
 *   - doGet():  Listar, buscar y mostrar formularios
 *   - doPost(): Guardar nuevo cliente, actualizar existente
 *
 * URL: /clientes
 *
 * Evidencia GA7-220501096-AA2-EV02
 * @author Jaider Londoño
 * @version 1.0
 */
@WebServlet(name = "ClienteServlet", urlPatterns = "/clientes")
public class ClienteServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ClienteDAO clienteDAO;

    @Override
    public void init() throws ServletException {
        clienteDAO = new ClienteDAO();
    }

    // ═══════════════════════════════════════════════════════════════════════════
    //  MÉTODO GET
    // ═══════════════════════════════════════════════════════════════════════════
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
                eliminarCliente(request, response);
                break;
            default:
                listarClientes(request, response);
                break;
        }
    }

    // ═══════════════════════════════════════════════════════════════════════════
    //  MÉTODO POST
    // ═══════════════════════════════════════════════════════════════════════════
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");
        if (accion == null) accion = "";

        switch (accion) {
            case "guardar":
                guardarCliente(request, response);
                break;
            case "actualizar":
                actualizarCliente(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/clientes");
                break;
        }
    }

    // ─── Handlers ──────────────────────────────────────────────────────────────

    private void listarClientes(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        List<Cliente> lista = clienteDAO.listarTodos();
        req.setAttribute("clientes", lista);
        req.setAttribute("titulo", "Gestión de Clientes");
        forward(req, res, "/WEB-INF/jsp/clientes/lista.jsp");
    }

    private void mostrarFormularioNuevo(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.setAttribute("titulo",  "Registrar Cliente");
        req.setAttribute("cliente", new Cliente());
        req.setAttribute("accion",  "guardar");
        forward(req, res, "/WEB-INF/jsp/clientes/formulario.jsp");
    }

    private void mostrarFormularioEditar(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Cliente cliente = clienteDAO.buscarPorId(id);

        if (cliente == null) {
            req.getSession().setAttribute("mensajeError", "Cliente no encontrado.");
            res.sendRedirect(req.getContextPath() + "/clientes");
            return;
        }

        req.setAttribute("titulo",  "Editar Cliente");
        req.setAttribute("cliente", cliente);
        req.setAttribute("accion",  "actualizar");
        forward(req, res, "/WEB-INF/jsp/clientes/formulario.jsp");
    }

    private void guardarCliente(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        Cliente c = construirCliente(req);
        boolean ok = clienteDAO.insertar(c);
        HttpSession session = req.getSession();
        if (ok) {
            session.setAttribute("mensajeExito",
                "✅ Cliente '" + c.getNombreCompleto() + "' registrado correctamente.");
        } else {
            session.setAttribute("mensajeError",
                "❌ Error al registrar el cliente. El documento ya puede existir.");
        }
        res.sendRedirect(req.getContextPath() + "/clientes");
    }

    private void actualizarCliente(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        Cliente c = construirCliente(req);
        c.setIdCliente(Integer.parseInt(req.getParameter("idCliente")));
        boolean ok = clienteDAO.actualizar(c);
        HttpSession session = req.getSession();
        if (ok) {
            session.setAttribute("mensajeExito",
                "✅ Cliente '" + c.getNombreCompleto() + "' actualizado correctamente.");
        } else {
            session.setAttribute("mensajeError", "❌ Error al actualizar el cliente.");
        }
        res.sendRedirect(req.getContextPath() + "/clientes");
    }

    private void eliminarCliente(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        boolean ok = clienteDAO.eliminar(id);
        HttpSession session = req.getSession();
        if (ok) {
            session.setAttribute("mensajeExito", "✅ Cliente eliminado correctamente.");
        } else {
            session.setAttribute("mensajeError", "❌ Error al eliminar el cliente.");
        }
        res.sendRedirect(req.getContextPath() + "/clientes");
    }

    // ─── Utilidades ────────────────────────────────────────────────────────────

    private Cliente construirCliente(HttpServletRequest req) {
        Cliente c = new Cliente();
        c.setNombre(req.getParameter("nombre").trim());
        c.setApellido(req.getParameter("apellido").trim());
        c.setDocumento(req.getParameter("documento").trim());
        c.setCorreo(req.getParameter("correo") != null ? req.getParameter("correo").trim() : "");
        c.setTelefono(req.getParameter("telefono") != null ? req.getParameter("telefono").trim() : "");
        c.setDireccion(req.getParameter("direccion") != null ? req.getParameter("direccion").trim() : "");
        return c;
    }

    private void forward(HttpServletRequest req, HttpServletResponse res, String jsp)
            throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher(jsp);
        rd.forward(req, res);
    }
}
