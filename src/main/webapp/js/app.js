/**
 * app.js - Funciones JavaScript para el Sistema de Ventas Web
 * Evidencia GA7-220501096-AA2-EV02
 * Autor: Jaider Stiven Londoño Lozada
 * SENA - ADSO
 */

document.addEventListener('DOMContentLoaded', () => {
    // 1. Auto-cierre de alertas flash después de 5 segundos
    const alertas = document.querySelectorAll('.alert');
    alertas.forEach(alerta => {
        setTimeout(() => {
            alerta.style.transition = 'opacity 0.5s ease, transform 0.5s ease';
            alerta.style.opacity = '0';
            alerta.style.transform = 'translateY(-10px)';
            setTimeout(() => alerta.remove(), 500);
        }, 5000);
    });

    // 2. Validación de formularios en el cliente antes de enviar por POST
    const formProducto = document.getElementById('formProducto');
    if (formProducto) {
        formProducto.addEventListener('submit', function (e) {
            const codigo = document.getElementById('codigo');
            const nombre = document.getElementById('nombre');
            const precio = document.getElementById('precio');
            const stock = document.getElementById('stock');
            const idCategoria = document.getElementById('idCategoria');

            let errores = [];

            if (!codigo.value.trim()) errores.push('El código es obligatorio.');
            if (!nombre.value.trim()) errores.push('El nombre es obligatorio.');
            if (precio.value === '' || parseFloat(precio.value) < 0) errores.push('El precio debe ser un número mayor o igual a 0.');
            if (stock.value === '' || parseInt(stock.value, 10) < 0) errores.push('El stock debe ser un entero mayor o igual a 0.');
            if (!idCategoria.value) errores.push('Debe seleccionar una categoría válida.');

            if (errores.length > 0) {
                e.preventDefault();
                alert("⚠️ Por favor corrija los siguientes errores:\n\n• " + errores.join("\n• "));
            }
        });
    }

    const formCliente = document.getElementById('formCliente');
    if (formCliente) {
        formCliente.addEventListener('submit', function (e) {
            const nombre = document.getElementById('nombre');
            const apellido = document.getElementById('apellido');
            const documento = document.getElementById('documento');

            let errores = [];
            if (!nombre.value.trim()) errores.push('El nombre es obligatorio.');
            if (!apellido.value.trim()) errores.push('El apellido es obligatorio.');
            if (!documento.value.trim() || !/^\d{6,15}$/.test(documento.value.trim())) {
                errores.push('El documento debe contener entre 6 y 15 dígitos numéricos.');
            }

            if (errores.length > 0) {
                e.preventDefault();
                alert("⚠️ Por favor verifique el formulario:\n\n• " + errores.join("\n• "));
            }
        });
    }
});

/**
 * Confirmación de eliminación de registro
 * @param {string} nombreItem Nombre del producto o cliente a eliminar
 * @returns {boolean} Confirmación del usuario
 */
function confirmarEliminar(nombreItem) {
    return confirm(`¿Está seguro de que desea eliminar "${nombreItem}"?\nEsta acción realizará una baja en el sistema.`);
}
