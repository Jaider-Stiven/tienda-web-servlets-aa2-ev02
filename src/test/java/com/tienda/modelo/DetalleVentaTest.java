package com.tienda.modelo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase DetalleVenta.
 * Evidencia GA7-220501096-AA2-EV02: Módulos de software codificados y probados.
 *
 * @author Jaider Stiven Londoño Lozada
 */
public class DetalleVentaTest {

    @Test
    @DisplayName("CP08 - Debe calcular correctamente el subtotal (cantidad * precioUnitario)")
    public void testCalculoSubtotal() {
        DetalleVenta detalle = new DetalleVenta(1, 101, 3, new BigDecimal("45000.00"));
        BigDecimal subtotal = detalle.getSubtotal();

        assertEquals(new BigDecimal("135000.00"), subtotal,
                "El subtotal debe ser 3 * 45000 = 135000");
    }

    @Test
    @DisplayName("CP09 - Debe retornar cero si el precio unitario es nulo")
    public void testSubtotalConPrecioNulo() {
        DetalleVenta detalle = new DetalleVenta();
        detalle.setCantidad(5);
        detalle.setPrecioUnitario(null);

        assertEquals(BigDecimal.ZERO, detalle.getSubtotal(),
                "Si el precio unitario es nulo, el subtotal debe retornar BigDecimal.ZERO");
    }
}
