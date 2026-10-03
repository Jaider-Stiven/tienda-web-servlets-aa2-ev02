<%-- 
    header.jsp - Fragmento de cabecera reutilizable
    Evidencia GA7-220501096-AA2-EV02
    Autor: Jaider Londoño
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Sistema de Ventas - Evidencia SENA AA2-EV02">
    <title>${not empty titulo ? titulo : 'Sistema de Ventas'} | TiendaWeb</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
</head>
<body>
    <!-- BARRA DE NAVEGACIÓN -->
    <nav class="navbar">
        <div class="navbar-brand">
            <span class="brand-icon">🛒</span>
            <span class="brand-name">TiendaWeb</span>
        </div>
        <ul class="navbar-menu">
            <li><a href="${pageContext.request.contextPath}/inicio"     class="nav-link">🏠 Inicio</a></li>
            <li><a href="${pageContext.request.contextPath}/productos"  class="nav-link">📦 Productos</a></li>
            <li><a href="${pageContext.request.contextPath}/clientes"   class="nav-link">👥 Clientes</a></li>
        </ul>
        <div class="navbar-user">
            <span class="user-badge">SENA | Ficha 82</span>
        </div>
    </nav>

    <!-- MENSAJES FLASH (sesión) -->
    <c:if test="${not empty sessionScope.mensajeExito}">
        <div class="alert alert-success" id="alertaExito">
            ${sessionScope.mensajeExito}
            <button class="alert-close" onclick="this.parentElement.remove()">✕</button>
        </div>
        <c:remove var="mensajeExito" scope="session"/>
    </c:if>
    <c:if test="${not empty sessionScope.mensajeError}">
        <div class="alert alert-error" id="alertaError">
            ${sessionScope.mensajeError}
            <button class="alert-close" onclick="this.parentElement.remove()">✕</button>
        </div>
        <c:remove var="mensajeError" scope="session"/>
    </c:if>

    <main class="container">
