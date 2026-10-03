<%-- 
    index.jsp - Dashboard principal del sistema de ventas
    Evidencia GA7-220501096-AA2-EV02
    Autor: Jaider Londoño

    Elementos JSP usados:
      - Directivas: <%@ page %>, <%@ taglib %>
      - Expresiones EL: ${variable}
      - JSTL: <c:forEach>, <c:if>, <c:choose>, <c:when>
      - Scriptlet: <%= ... %> en el footer
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core"     prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt"      prefix="fmt" %>
<%@ include file="header.jsp" %>

    <!-- ENCABEZADO DE PÁGINA -->
    <section class="page-header">
        <div class="page-header-content">
            <h1 class="page-title">📊 Dashboard</h1>
            <p class="page-subtitle">Resumen general del sistema de ventas</p>
        </div>
    </section>

    <!-- TARJETAS DE ESTADÍSTICAS -->
    <section class="stats-grid">
        <div class="stat-card stat-blue">
            <div class="stat-icon">📦</div>
            <div class="stat-info">
                <h2 class="stat-number">${totalProductos}</h2>
                <p class="stat-label">Productos activos</p>
            </div>
        </div>

        <div class="stat-card stat-green">
            <div class="stat-icon">👥</div>
            <div class="stat-info">
                <h2 class="stat-number">${totalClientes}</h2>
                <p class="stat-label">Clientes registrados</p>
            </div>
        </div>

        <div class="stat-card ${stockBajo > 0 ? 'stat-red' : 'stat-gray'}">
            <div class="stat-icon">⚠️</div>
            <div class="stat-info">
                <h2 class="stat-number">${stockBajo}</h2>
                <p class="stat-label">Productos con stock bajo</p>
            </div>
        </div>
    </section>

    <!-- TABLA: Últimos productos -->
    <section class="card">
        <div class="card-header">
            <h2 class="card-title">📋 Últimos Productos</h2>
            <a href="${pageContext.request.contextPath}/productos" class="btn btn-primary btn-sm">
                Ver todos →
            </a>
        </div>
        <div class="table-responsive">
            <table class="table">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Código</th>
                        <th>Nombre</th>
                        <th>Precio</th>
                        <th>Stock</th>
                        <th>Estado</th>
                    </tr>
                </thead>
                <tbody>
                    <%-- Iteramos con JSTL c:forEach y usamos varStatus --%>
                    <c:choose>
                        <c:when test="${empty productos}">
                            <tr>
                                <td colspan="6" class="text-center text-muted">
                                    No hay productos registrados.
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="p" items="${productos}" varStatus="estado">
                                <tr class="${estado.odd ? 'row-odd' : ''}">
                                    <td>${estado.count}</td>
                                    <td><code class="code-badge">${p.codigo}</code></td>
                                    <td>${p.nombre}</td>
                                    <td>
                                        <fmt:formatNumber value="${p.precio}"
                                            type="currency" currencySymbol="$"
                                            minFractionDigits="0" maxFractionDigits="0"/>
                                    </td>
                                    <td>${p.stock}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${p.stock == 0}">
                                                <span class="badge badge-red">Sin stock</span>
                                            </c:when>
                                            <c:when test="${p.stock < 5}">
                                                <span class="badge badge-yellow">Stock bajo</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge badge-green">Disponible</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </section>

    <!-- ACCESOS RÁPIDOS -->
    <section class="quick-actions">
        <h2 class="section-title">⚡ Acciones Rápidas</h2>
        <div class="actions-grid">
            <a href="${pageContext.request.contextPath}/productos?accion=nuevo"
               class="action-card action-blue">
                <span class="action-icon">➕</span>
                <span class="action-label">Nuevo Producto</span>
            </a>
            <a href="${pageContext.request.contextPath}/clientes?accion=nuevo"
               class="action-card action-green">
                <span class="action-icon">👤</span>
                <span class="action-label">Nuevo Cliente</span>
            </a>
            <a href="${pageContext.request.contextPath}/productos"
               class="action-card action-purple">
                <span class="action-icon">📦</span>
                <span class="action-label">Ver Productos</span>
            </a>
            <a href="${pageContext.request.contextPath}/clientes"
               class="action-card action-orange">
                <span class="action-icon">👥</span>
                <span class="action-label">Ver Clientes</span>
            </a>
        </div>
    </section>

<%@ include file="footer.jsp" %>
