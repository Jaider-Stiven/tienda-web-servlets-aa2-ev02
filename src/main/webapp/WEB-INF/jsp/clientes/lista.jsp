<%-- 
    clientes/lista.jsp - Lista de clientes
    Evidencia GA7-220501096-AA2-EV02
    Autor: Jaider Londoño
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core"      prefix="c"  %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<%@ include file="../header.jsp" %>

    <section class="page-header">
        <div class="page-header-content">
            <h1 class="page-title">👥 ${titulo}</h1>
            <p class="page-subtitle">
                Total: <strong>${fn:length(clientes)}</strong> cliente(s) registrado(s)
            </p>
        </div>
        <a href="${pageContext.request.contextPath}/clientes?accion=nuevo"
           class="btn btn-primary" id="btnNuevoCliente">
            ➕ Nuevo Cliente
        </a>
    </section>

    <section class="card">
        <div class="table-responsive">
            <table class="table" id="tablaClientes">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Nombre Completo</th>
                        <th>Documento</th>
                        <th>Correo</th>
                        <th>Teléfono</th>
                        <th>Dirección</th>
                        <th class="text-center">Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${empty clientes}">
                            <tr>
                                <td colspan="7" class="text-center text-muted empty-state">
                                    <span class="empty-icon">👤</span><br>
                                    No hay clientes registrados.
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="cli" items="${clientes}" varStatus="vs">
                                <tr id="cli-${cli.idCliente}">
                                    <td>${vs.count}</td>
                                    <td>
                                        <div class="client-avatar">
                                            <span class="avatar-initials">
                                                ${fn:substring(cli.nombre,0,1)}${fn:substring(cli.apellido,0,1)}
                                            </span>
                                            <strong>${cli.nombreCompleto}</strong>
                                        </div>
                                    </td>
                                    <td><code class="code-badge">${cli.documento}</code></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty cli.correo}">
                                                <a href="mailto:${cli.correo}" class="link">
                                                    ${cli.correo}
                                                </a>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="text-muted">—</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${not empty cli.telefono ? cli.telefono : '—'}</td>
                                    <td>
                                        <small>${not empty cli.direccion ? cli.direccion : '—'}</small>
                                    </td>
                                    <td class="text-center actions-cell">
                                        <a href="${pageContext.request.contextPath}/clientes?accion=editar&id=${cli.idCliente}"
                                           class="btn btn-warning btn-xs" title="Editar">✏️</a>
                                        <a href="${pageContext.request.contextPath}/clientes?accion=eliminar&id=${cli.idCliente}"
                                           class="btn btn-danger btn-xs"
                                           title="Eliminar"
                                           onclick="return confirmarEliminar('${cli.nombreCompleto}')">🗑️</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </section>

<%@ include file="../footer.jsp" %>
