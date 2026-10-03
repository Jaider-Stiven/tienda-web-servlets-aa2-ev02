<%-- 
    productos/lista.jsp - Lista de productos con búsqueda
    Evidencia GA7-220501096-AA2-EV02
    Autor: Jaider Londoño

    Formulario GET → ProductoServlet.doGet(accion=buscar)
    JSTL: c:forEach, c:if, c:choose, c:when, c:url
    EL: ${variable}, ${fn:length()}
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core"      prefix="c"  %>
<%@ taglib uri="jakarta.tags.fmt"       prefix="fmt"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<%@ include file="../header.jsp" %>

    <section class="page-header">
        <div class="page-header-content">
            <h1 class="page-title">📦 ${titulo}</h1>
            <p class="page-subtitle">
                Total: <strong>${fn:length(productos)}</strong> producto(s)
                <c:if test="${not empty busqueda}">
                    &mdash; Búsqueda: "<em>${busqueda}</em>"
                </c:if>
            </p>
        </div>
        <a href="${pageContext.request.contextPath}/productos?accion=nuevo"
           class="btn btn-primary" id="btnNuevoProducto">
            ➕ Nuevo Producto
        </a>
    </section>

    <!-- FORMULARIO DE BÚSQUEDA (método GET) -->
    <section class="search-bar card">
        <form action="${pageContext.request.contextPath}/productos"
              method="GET" id="formBusqueda" class="search-form">
            <input type="hidden" name="accion" value="buscar">
            <div class="form-group-inline">
                <input type="text"
                       id="campoBusqueda"
                       name="nombre"
                       value="${busqueda}"
                       placeholder="Buscar por nombre del producto..."
                       class="form-input search-input"
                       autocomplete="off">
                <button type="submit" class="btn btn-primary" id="btnBuscar">
                    🔍 Buscar
                </button>
                <c:if test="${not empty busqueda}">
                    <a href="${pageContext.request.contextPath}/productos"
                       class="btn btn-outline" id="btnLimpiar">✕ Limpiar</a>
                </c:if>
            </div>
        </form>
    </section>

    <!-- TABLA DE PRODUCTOS -->
    <section class="card">
        <div class="table-responsive">
            <table class="table" id="tablaProductos">
                <thead>
                    <tr>
                        <th>Código</th>
                        <th>Nombre</th>
                        <th>Categoría</th>
                        <th>Precio</th>
                        <th>Stock</th>
                        <th>Estado</th>
                        <th class="text-center">Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${empty productos}">
                            <tr>
                                <td colspan="7" class="text-center text-muted empty-state">
                                    <span class="empty-icon">📭</span><br>
                                    No se encontraron productos.
                                    <c:if test="${not empty busqueda}">
                                        <a href="${pageContext.request.contextPath}/productos">
                                            Ver todos los productos
                                        </a>
                                    </c:if>
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="prod" items="${productos}" varStatus="vs">
                                <tr id="prod-${prod.idProducto}">
                                    <td><code class="code-badge">${prod.codigo}</code></td>
                                    <td>
                                        <strong>${prod.nombre}</strong>
                                        <c:if test="${not empty prod.descripcion}">
                                            <br>
                                            <small class="text-muted">
                                                ${fn:substring(prod.descripcion, 0, 60)}
                                                <c:if test="${fn:length(prod.descripcion) > 60}">...</c:if>
                                            </small>
                                        </c:if>
                                    </td>
                                    <td>
                                        <span class="category-tag">${prod.nombreCategoria}</span>
                                    </td>
                                    <td class="text-right font-mono">
                                        <fmt:formatNumber value="${prod.precio}"
                                            type="currency" currencySymbol="$"
                                            minFractionDigits="0" maxFractionDigits="0"/>
                                    </td>
                                    <td class="text-center">
                                        <span class="${prod.stock < 5 ? 'text-danger' : 'text-success'} font-bold">
                                            ${prod.stock}
                                        </span>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${prod.stock == 0}">
                                                <span class="badge badge-red">Sin stock</span>
                                            </c:when>
                                            <c:when test="${prod.stock < 5}">
                                                <span class="badge badge-yellow">Stock bajo</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge badge-green">Disponible</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="text-center actions-cell">
                                        <%-- Botón EDITAR → GET ?accion=editar&id=N --%>
                                        <a href="${pageContext.request.contextPath}/productos?accion=editar&id=${prod.idProducto}"
                                           class="btn btn-warning btn-xs" title="Editar">✏️</a>
                                        <%-- Botón ELIMINAR → GET ?accion=eliminar&id=N con confirmación JS --%>
                                        <a href="${pageContext.request.contextPath}/productos?accion=eliminar&id=${prod.idProducto}"
                                           class="btn btn-danger btn-xs"
                                           title="Eliminar"
                                           onclick="return confirmarEliminar('${prod.nombre}')">🗑️</a>
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
