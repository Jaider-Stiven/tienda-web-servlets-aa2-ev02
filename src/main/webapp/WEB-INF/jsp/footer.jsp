<%-- 
    footer.jsp - Fragmento de pie de página reutilizable
    Evidencia GA7-220501096-AA2-EV02
    Autor: Jaider Londoño
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    </main><!-- fin .container -->

    <footer class="footer">
        <div class="footer-content">
            <p>
                <strong>TiendaWeb</strong> &mdash; Sistema de Ventas &copy; <%= new java.util.Date().getYear() + 1900 %>
            </p>
            <p class="footer-sub">
                Evidencia GA7-220501096-AA2-EV02 &bull; SENA &bull; Tecnología en Análisis y Desarrollo de Software
            </p>
        </div>
    </footer>

    <script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>
