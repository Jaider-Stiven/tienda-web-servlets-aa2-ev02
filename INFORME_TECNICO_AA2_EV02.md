# SERVICIO NACIONAL DE APRENDIZAJE - SENA
## CENTRO DE FORMACIÓN EN TECNOLOGÍAS DE LA INFORMACIÓN
### TECNOLOGÍA EN ANÁLISIS Y DESARROLLO DE SOFTWARE (ADSO)

---

### INFORME TÉCNICO DE EVIDENCIA
# GA7-220501096-AA2-EV02: Módulos de software codificados y probados

**Aprendiz:** Jaider Stiven Londoño Lozada  
**Ficha:** ADSO - Ficha 82  
**Instructor Asignado:** Equipo Ejecutor ADSO  
**Fecha:** Octubre de 2026  

---

## 1. INTRODUCCIÓN

El desarrollo de software empresarial requiere la adopción de arquitecturas robustas y escalables que separen claramente la lógica de negocio, la persistencia de datos y la interfaz de usuario. En el marco de la formación del SENA, el componente formativo orienta el aprendizaje hacia la construcción de aplicaciones web empresariales mediante Java Servlets y JavaServer Pages (JSP).

El presente documento expone de forma detallada la codificación, estructuración y pruebas del módulo web de **Gestión de Ventas e Inventario (TiendaWeb)**. La solución fue desarrollada empleando el patrón arquitectónico Modelo-Vista-Controlador (MVC), integrando formularios HTML dinámicos, servlets con soporte para peticiones HTTP `GET` y `POST`, directivas y librerías de etiquetas estándar (JSTL) en páginas JSP, base de datos relacional MySQL y una batería de pruebas unitarias automatizadas con JUnit 5.

---

## 2. OBJETIVOS

### 2.1 Objetivo General
Codificar y probar el módulo web del sistema de información aplicando tecnologías de Java Servlets, JSP y bases de datos relacionales, tomando como base los artefactos de análisis y diseño previamente elaborados en el ciclo de vida del software.

### 2.2 Objetivos Específicos
1. Estructurar el proyecto bajo una arquitectura multicapa (Modelo, DAO, Controlador/Servlet, Vista/JSP) asegurando el desacoplamiento de componentes.
2. Implementar formularios HTML con mecanismos de transmisión de datos mediante los métodos HTTP `GET` (búsquedas y consultas) y `POST` (creación y modificación de registros).
3. Construir vistas dinámicas haciendo uso de JavaServer Pages (JSP), Expression Language (EL) y JSTL para la presentación limpia de los datos.
4. Establecer la persistencia de datos mediante JDBC conectado a MySQL utilizando sentencias preparadas (`PreparedStatement`) para la mitigación de vulnerabilidades de inyección SQL.
5. Diseñar e implementar pruebas unitarias automatizadas para verificar las reglas de negocio de los modelos.
6. Administrar el ciclo de versiones del código fuente mediante la herramienta Git y repositorios remotos.

---

## 3. ARTEFACTOS DEL CICLO DE SOFTWARE CONSIDERADOS

La construcción del código fuente responde directamente a los productos elaborados en fases previas del proyecto:

### 3.1 Historias de Usuario (HU)
- **HU-01: Gestión del Catálogo de Productos:**  
  *Criterio de Aceptación:* El sistema debe permitir visualizar todos los productos con su stock, código, categoría y precio en moneda local (COP), alertando cuando el stock sea inferior a 5 unidades.
- **HU-02: Registro de Nuevos Productos:**  
  *Criterio de Aceptación:* Formulario web que valide campos obligatorios y guarde la información mediante el método POST hacia el servlet correspondiente.
- **HU-03: Actualización y Baja Lógica:**  
  *Criterio de Aceptación:* Modificación de precios y stock; eliminación mediante desactivación lógica para preservar la integridad referencial histórica de ventas.
- **HU-04: Directorio de Clientes:**  
  *Criterio de Aceptación:* Registro de compradores con documento de identidad único y validaciones de formato de correo y teléfono.

### 3.2 Casos de Uso (CU)
- **CU01: Administrar Productos:** Actores involucrados: Administrador / Cajero.
- **CU02: Consultar Stock:** Actor: Usuario del sistema.
- **CU03: Registrar Cliente:** Actor: Administrador.

### 3.3 Modelo de Clases (UML)
Se definieron las entidades del modelo del dominio:
- `Producto` (idProducto, codigo, nombre, descripcion, precio, stock, idCategoria, activo).
- `Cliente` (idCliente, nombre, apellido, documento, correo, telefono, direccion, activo).
- `Venta` (idVenta, idCliente, fechaVenta, total, estado, observaciones).
- `DetalleVenta` (idDetalle, idVenta, idProducto, cantidad, precioUnitario, subtotal).

---

## 4. DESCRIPCIÓN DE LA CODIFICACIÓN DEL MÓDULO

### 4.1 Arquitectura del Proyecto
El proyecto está estructurado con Maven y organizado en los siguientes paquetes:

```
JAIDER_LONDONO_AA2_EV02/
├── pom.xml                                  (Configuración Maven y dependencias)
├── .gitignore                               (Exclusiones para Git)
├── ENLACE_REPOSITORIO.txt                   (Enlace al repositorio remoto)
├── README.md                                (Guía de despliegue y arquitectura)
├── INFORME_TECNICO_AA2_EV02.md              (Este documento de entrega)
├── sql/
│   └── tienda.sql                           (Script DDL/DML de la base de datos)
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/tienda/
│   │   │       ├── modelo/                  (Entidades POJO del negocio)
│   │   │       │   ├── Producto.java
│   │   │       │   ├── Cliente.java
│   │   │       │   ├── Venta.java
│   │   │       │   └── DetalleVenta.java
│   │   │       ├── dao/                     (Capa de acceso a datos JDBC)
│   │   │       │   ├── Conexion.java        (Singleton para conexión MySQL)
│   │   │       │   ├── ProductoDAO.java
│   │   │       │   └── ClienteDAO.java
│   │   │       └── servlet/                 (Controladores Web)
│   │   │           ├── IndexServlet.java    (Controlador del Dashboard)
│   │   │           ├── ProductoServlet.java (Controlador CRUD Productos)
│   │   │           └── ClienteServlet.java  (Controlador CRUD Clientes)
│   │   └── webapp/
│   │       ├── css/
│   │       │   └── estilos.css              (Estilos responsivos con CSS3 y variables)
│   │       ├── js/
│   │       │   └── app.js                   (Validaciones del cliente y alertas)
│   │       └── WEB-INF/
│   │           ├── web.xml                  (Descriptor de despliegue)
│   │           └── jsp/                     (Vistas protegidas en WEB-INF)
│   │               ├── header.jsp           (Barra de navegación y flash alerts)
│   │               ├── footer.jsp           (Pie de página reutilizable)
│   │               ├── index.jsp            (Dashboard interactivo)
│   │               ├── productos/
│   │               │   ├── lista.jsp        (Tabla de productos y búsqueda GET)
│   │               │   └── formulario.jsp   (Formulario de registro/edición POST)
│   │               └── clientes/
│   │                   ├── lista.jsp        (Listado de clientes registrados)
│   │                   └── formulario.jsp   (Formulario de clientes POST)
│   └── test/
│       └── java/
│           └── com/tienda/modelo/           (Batería de pruebas unitarias JUnit 5)
│               ├── ProductoTest.java
│               ├── ClienteTest.java
│               └── DetalleVentaTest.java
```

### 4.2 Implementación de Servlets y Métodos GET y POST

#### A. Manejo de Peticiones GET en `ProductoServlet`
El método `doGet(HttpServletRequest request, HttpServletResponse response)` procesa parámetros de la URL mediante un enrutador interno:
- Parámetro `accion=listar`: Recupera todos los productos llamando a `productoDAO.listarTodos()` y los envía al JSP mediante `request.setAttribute("productos", lista)`.
- Parámetro `accion=buscar`: Toma el parámetro de búsqueda `nombre`, ejecuta `productoDAO.buscarPorNombre(nombre)` y actualiza la lista.
- Parámetro `accion=nuevo`: Inicializa una instancia vacía de `Producto` y despacha el formulario en modo registro.
- Parámetro `accion=editar`: Busca el registro por su `id`, precargando los campos en el formulario.
- Parámetro `accion=eliminar`: Ejecuta la baja lógica del producto en la base de datos y redirecciona con un mensaje flash de confirmación.

#### B. Manejo de Peticiones POST en `ProductoServlet`
El método `doPost(HttpServletRequest request, HttpServletResponse response)` procesa la carga útil de los formularios enviados con `method="POST"`:
- Parámetro oculto `accion=guardar`: Extrae los parámetros (`codigo`, `nombre`, `precio`, `stock`, `idCategoria`), instancia el objeto `Producto` y llama a `productoDAO.insertar(producto)`.
- Parámetro oculto `accion=actualizar`: Extrae los mismos parámetros más el `idProducto`, invocando `productoDAO.actualizar(producto)`.
- Manejo de sesión: Se utiliza `request.getSession().setAttribute("mensajeExito", ...)` implementando el patrón Post-Redirect-Get (PRG) para evitar el reenvío duplicado de formularios al recargar la página.

### 4.3 Elementos de JSP y JSTL Utilizados
1. **Directivas:** `<%@ page %>`, `<%@ taglib uri="jakarta.tags.core" prefix="c" %>`, `<%@ include file="..." %>`.
2. **Expression Language (EL):** Sintaxis `${producto.nombre}`, `${totalProductos}`, `${p.stock < 5 ? 'text-danger' : 'text-success'}`.
3. **Etiquetas JSTL:**
   - `<c:forEach var="prod" items="${productos}" varStatus="vs">` para la iteración de colecciones.
   - `<c:if test="${not empty busqueda}">` para renderizado condicional.
   - `<c:choose>`, `<c:when>`, `<c:otherwise>` para estructuras de decisión múltiple (ej. badges de stock).
   - `<fmt:formatNumber>` para el formateo de monedas y cantidades numéricas.

---

## 5. PLAN Y EJECUCIÓN DE PRUEBAS DEL SOFTWARE

Para cumplir con el componente de **"Módulos probados"**, se diseñó una batería de pruebas automatizadas con **JUnit 5**:

| Código Prueba | Clase / Método Probado | Descripción del Caso de Prueba | Resultado Esperado | Estado |
|---|---|---|---|---|
| **CP-01** | `Producto.tieneStock()` | Stock > 0 en producto nuevo | Retorna `true` | ✅ Aprobado |
| **CP-02** | `Producto.tieneStock()` | Stock = 0 en producto agotado | Retorna `false` | ✅ Aprobado |
| **CP-03** | `Producto.stockBajo()` | Stock = 3 (< 5 unidades) | Retorna `true` (alerta activa) | ✅ Aprobado |
| **CP-04** | `Producto.stockBajo()` | Stock = 15 (>= 5 unidades) | Retorna `false` | ✅ Aprobado |
| **CP-05** | `Producto` (Bean) | Verificación de getters/setters y coherencia de datos | Valores coincidentes | ✅ Aprobado |
| **CP-06** | `Cliente.getNombreCompleto()` | Concatenación de nombre y apellido | Cadena completa con espacio | ✅ Aprobado |
| **CP-07** | `Cliente` (Bean) | Atributos de contacto y documento | Asignación íntegra | ✅ Aprobado |
| **CP-08** | `DetalleVenta.getSubtotal()` | Cálculo `3 * $45.000` | Retorna `$135.000` | ✅ Aprobado |
| **CP-09** | `DetalleVenta.getSubtotal()` | Precio unitario nulo | Retorna `BigDecimal.ZERO` sin fallar | ✅ Aprobado |

---

## 6. HERRAMIENTAS DE VERSIONAMIENTO

El proyecto se administró mediante el sistema de control de versiones distribuido **Git**:
- **Estructura de ramas:** Rama principal `main`.
- **Archivo `.gitignore`:** Configurado para omitir binarios compilados de Maven (`target/`), archivos generados por entornos de desarrollo (`.idea/`, `.vscode/`, `.settings/`) y registros temporales.
- **Historial de confirmaciones (commits):** Confirmaciones atómicas documentando cada capa construida (modelo, dao, servlets, vistas jsp, estilos y pruebas).
- **Repositorio:** Documentado en `ENLACE_REPOSITORIO.txt`.

---

## 7. CONCLUSIONES

1. Se consolidó de manera práctica la arquitectura Modelo-Vista-Controlador (MVC) en el ecosistema Java Web empresarial, logrando una estricta separación de responsabilidades entre el modelo de datos, la capa de acceso a datos (DAO), los controladores (Servlets) y las vistas dinámicas (JSP).
2. El uso adecuado de los métodos HTTP `GET` y `POST` permitió proteger la transmisión de datos sensibles y respetar la semántica del protocolo web, aplicando el patrón Post-Redirect-Get para una experiencia de usuario fluida y libre de duplicación de transacciones.
3. La implementación de pruebas unitarias automatizadas con JUnit 5 garantizó la verificación temprana de las reglas de negocio, asegurando que las entidades respondan correctamente a los casos límite y requisitos del sistema.
4. El versionamiento continuo con Git facilitó la trazabilidad y la preservación del código fuente, cumpliendo con los estándares de la industria del desarrollo de software y los lineamientos del SENA.
