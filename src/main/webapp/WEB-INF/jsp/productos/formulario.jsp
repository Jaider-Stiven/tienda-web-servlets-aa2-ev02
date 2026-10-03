<%-- 
    productos/formulario.jsp - Formulario de Registro/Edición de Producto
    Evidencia GA7-220501096-AA2-EV02
    Autor: Jaider Londoño

    Formulario POST → ProductoServlet.doPost(accion=guardar|actualizar)
    Elementos JSP:
      - Directivas: <%@ page %>, <%@ taglib %>
      - Expression Language: ${producto.campo}
      - JSTL: <c:if>, <c:choose>
      - Scriptlet de validación JS embebido
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ include file="../header.jsp" %>

    <section class="page-header">
        <div class="page-header-content">
            <h1 class="page-title">
                <c:choose>
                    <c:when test="${accion == 'guardar'}">➕ Registrar Producto</c:when>
                    <c:otherwise>✏️ Editar Producto</c:otherwise>
                </c:choose>
            </h1>
        </div>
        <a href="${pageContext.request.contextPath}/productos"
           class="btn btn-outline">← Volver</a>
    </section>

    <section class="card form-card">

        <%-- 
            FORMULARIO POST → ProductoServlet.doPost()
            El campo oculto "accion" indica si es guardar o actualizar.
        --%>
        <form action="${pageContext.request.contextPath}/productos"
              method="POST"
              id="formProducto"
              novalidate>

            <%-- Campo oculto: acción (guardar / actualizar) --%>
            <input type="hidden" name="accion" value="${accion}">

            <%-- Campo oculto: ID (solo para actualizar) --%>
            <c:if test="${accion == 'actualizar'}">
                <input type="hidden" name="idProducto" value="${producto.idProducto}">
            </c:if>

            <div class="form-grid">

                <!-- Código -->
                <div class="form-group">
                    <label for="codigo" class="form-label">
                        Código <span class="required">*</span>
                    </label>
                    <input type="text"
                           id="codigo"
                           name="codigo"
                           value="${producto.codigo}"
                           class="form-input"
                           placeholder="Ej: ELEC-001"
                           maxlength="50"
                           required>
                    <small class="form-hint">Código único identificador del producto</small>
                </div>

                <!-- Nombre -->
                <div class="form-group">
                    <label for="nombre" class="form-label">
                        Nombre <span class="required">*</span>
                    </label>
                    <input type="text"
                           id="nombre"
                           name="nombre"
                           value="${producto.nombre}"
                           class="form-input"
                           placeholder="Nombre del producto"
                           maxlength="150"
                           required>
                </div>

                <!-- Precio -->
                <div class="form-group">
                    <label for="precio" class="form-label">
                        Precio (COP) <span class="required">*</span>
                    </label>
                    <div class="input-prefix-group">
                        <span class="input-prefix">$</span>
                        <input type="number"
                               id="precio"
                               name="precio"
                               value="${producto.precio}"
                               class="form-input input-with-prefix"
                               placeholder="0"
                               min="0"
                               step="100"
                               required>
                    </div>
                </div>

                <!-- Stock -->
                <div class="form-group">
                    <label for="stock" class="form-label">
                        Stock <span class="required">*</span>
                    </label>
                    <input type="number"
                           id="stock"
                           name="stock"
                           value="${producto.stock}"
                           class="form-input"
                           placeholder="0"
                           min="0"
                           required>
                </div>

                <!-- Categoría -->
                <div class="form-group">
                    <label for="idCategoria" class="form-label">
                        Categoría <span class="required">*</span>
                    </label>
                    <%-- 
                        Usamos un select estático. En producción se consultaría
                        la tabla categorias via CategoriaDAO.
                    --%>
                    <select id="idCategoria" name="idCategoria"
                            class="form-input form-select" required>
                        <option value="">-- Seleccione una categoría --</option>
                        <option value="1" ${producto.idCategoria == 1 ? 'selected' : ''}>Electrónica</option>
                        <option value="2" ${producto.idCategoria == 2 ? 'selected' : ''}>Ropa</option>
                        <option value="3" ${producto.idCategoria == 3 ? 'selected' : ''}>Alimentos</option>
                        <option value="4" ${producto.idCategoria == 4 ? 'selected' : ''}>Hogar</option>
                        <option value="5" ${producto.idCategoria == 5 ? 'selected' : ''}>Deportes</option>
                    </select>
                </div>

                <!-- Descripción (ancho completo) -->
                <div class="form-group form-group-full">
                    <label for="descripcion" class="form-label">Descripción</label>
                    <textarea id="descripcion"
                              name="descripcion"
                              class="form-input form-textarea"
                              placeholder="Descripción detallada del producto..."
                              rows="3"
                              maxlength="500">${producto.descripcion}</textarea>
                    <small class="form-hint">Máximo 500 caracteres</small>
                </div>

            </div><!-- fin form-grid -->

            <!-- BOTONES DE ACCIÓN -->
            <div class="form-actions">
                <button type="submit"
                        id="btnGuardar"
                        class="btn btn-primary btn-lg">
                    <c:choose>
                        <c:when test="${accion == 'guardar'}">💾 Guardar Producto</c:when>
                        <c:otherwise>💾 Actualizar Producto</c:otherwise>
                    </c:choose>
                </button>
                <a href="${pageContext.request.contextPath}/productos"
                   class="btn btn-outline btn-lg" id="btnCancelar">✕ Cancelar</a>
            </div>

        </form>

        <%-- Nota pedagógica para la evidencia --%>
        <div class="nota-pedagogica">
            <strong>📌 Nota técnica:</strong> Este formulario usa <code>method="POST"</code>
            para enviar los datos al servlet <code>ProductoServlet.doPost()</code>.
            El campo oculto <code>accion</code> determina si se inserta (guardar) o
            actualiza el registro en la base de datos.
        </div>

    </section>

<%@ include file="../footer.jsp" %>
