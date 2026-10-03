package com.tienda.modelo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase Cliente.
 * Evidencia GA7-220501096-AA2-EV02: Módulos de software codificados y probados.
 *
 * @author Jaider Stiven Londoño Lozada
 */
public class ClienteTest {

    @Test
    @DisplayName("CP06 - Debe concatenar correctamente el nombre completo del cliente")
    public void testGetNombreCompleto() {
        Cliente cliente = new Cliente(1, "Jaider Stiven", "Londoño Lozada",
                "1020304050", "jaider@correo.com", "3111234567", "Cra 45 # 12-34", true);

        assertEquals("Jaider Stiven Londoño Lozada", cliente.getNombreCompleto(),
                "El método getNombreCompleto debe unir nombre y apellido con un espacio");
    }

    @Test
    @DisplayName("CP07 - Validación de estado activo por defecto y asignación de atributos")
    public void testAtributosCliente() {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(5);
        cliente.setNombre("Carlos");
        cliente.setApellido("Gómez");
        cliente.setDocumento("987654321");
        cliente.setCorreo("carlos@example.com");
        cliente.setTelefono("3005556677");
        cliente.setDireccion("Calle 80 # 10-20");
        cliente.setActivo(true);

        assertEquals(5, cliente.getIdCliente());
        assertEquals("Carlos Gómez", cliente.getNombreCompleto());
        assertEquals("987654321", cliente.getDocumento());
        assertTrue(cliente.isActivo());
    }
}
