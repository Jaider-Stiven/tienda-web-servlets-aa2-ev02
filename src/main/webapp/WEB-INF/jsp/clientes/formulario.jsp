<%-- 
    clientes/formulario.jsp - Formulario de Registro/Edición de Cliente
    Evidencia GA7-220501096-AA2-EV02
    Autor: Jaider Londoño

    Formulario POST → ClienteServlet.doPost(accion=guardar|actualizar)
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ include file="../header.jsp" %>

    <section class="page-header">
        <div class="page-header-content">
            <h1 class="page-title">
                <c:choose>
                    <c:when test="${accion == 'guardar'}">➕ Registrar Cliente</c:when>
                    <c:otherwise>✏️ Editar Cliente</c:otherwise>
                </c:choose>
            </h1>
        </div>
        <a href="${pageContext.request.contextPath}/clientes"
           class="btn btn-outline">← Volver</a>
    </section>

    <section class="card form-card">

        <%--
            FORMULARIO POST → ClienteServlet.doPost()
        --%>
        <form action="${pageContext.request.contextPath}/clientes"
              method="POST"
              id="formCliente"
              novalidate>

            <input type="hidden" name="accion" value="${accion}">

            <c:if test="${accion == 'actualizar'}">
                <input type="hidden" name="idCliente" value="${cliente.idCliente}">
            </c:if>

            <div class="form-grid">

                <!-- Nombre -->
                <div class="form-group">
                    <label for="nombre" class="form-label">
                        Nombre(s) <span class="required">*</span>
                    </label>
                    <input type="text"
                           id="nombre"
                           name="nombre"
                           value="${cliente.nombre}"
                           class="form-input"
                           placeholder="Nombres del cliente"
                           maxlength="100"
                           required>
                </div>

                <!-- Apellido -->
                <div class="form-group">
                    <label for="apellido" class="form-label">
                        Apellido(s) <span class="required">*</span>
                    </label>
                    <input type="text"
                           id="apellido"
                           name="apellido"
                           value="${cliente.apellido}"
                           class="form-input"
                           placeholder="Apellidos del cliente"
                           maxlength="100"
                           required>
                </div>

                <!-- Documento -->
                <div class="form-group">
                    <label for="documento" class="form-label">
                        Número de Documento <span class="required">*</span>
                    </label>
                    <input type="text"
                           id="documento"
                           name="documento"
                           value="${cliente.documento}"
                           class="form-input"
                           placeholder="Cédula o NIT"
                           maxlength="20"
                           required
                           pattern="[0-9]{6,15}">
                    <small class="form-hint">Solo números, sin puntos ni guiones</small>
                </div>

                <!-- Correo -->
                <div class="form-group">
                    <label for="correo" class="form-label">Correo Electrónico</label>
                    <input type="email"
                           id="correo"
                           name="correo"
                           value="${cliente.correo}"
                           class="form-input"
                           placeholder="ejemplo@correo.com"
                           maxlength="150">
                </div>

                <!-- Teléfono -->
                <div class="form-group">
                    <label for="telefono" class="form-label">Teléfono</label>
                    <input type="tel"
                           id="telefono"
                           name="telefono"
                           value="${cliente.telefono}"
                           class="form-input"
                           placeholder="300 123 4567"
                           maxlength="20">
                </div>

                <!-- Dirección (ancho completo) -->
                <div class="form-group form-group-full">
                    <label for="direccion" class="form-label">Dirección</label>
                    <input type="text"
                           id="direccion"
                           name="direccion"
                           value="${cliente.direccion}"
                           class="form-input"
                           placeholder="Calle / Carrera / Avenida, Ciudad"
                           maxlength="255">
                </div>

            </div><!-- fin form-grid -->

            <div class="form-actions">
                <button type="submit" id="btnGuardarCliente" class="btn btn-primary btn-lg">
                    <c:choose>
                        <c:when test="${accion == 'guardar'}">💾 Registrar Cliente</c:when>
                        <c:otherwise>💾 Actualizar Cliente</c:otherwise>
                    </c:choose>
                </button>
                <a href="${pageContext.request.contextPath}/clientes"
                   class="btn btn-outline btn-lg" id="btnCancelarCliente">✕ Cancelar</a>
            </div>

        </form>

        <div class="nota-pedagogica">
            <strong>📌 Nota técnica:</strong> Este formulario usa <code>method="POST"</code>
            para enviar los datos al servlet <code>ClienteServlet.doPost()</code>.
            Los campos son validados en el cliente (HTML5) y en el servidor (DAO/SQL).
        </div>

    </section>

<%@ include file="../footer.jsp" %>
