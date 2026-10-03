package com.tienda.modelo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase Producto.
 * Evidencia GA7-220501096-AA2-EV02: Módulos de software codificados y probados.
 *
 * @author Jaider Stiven Londoño Lozada
 */
public class ProductoTest {

    @Test
    @DisplayName("CP01 - Debe indicar que tiene stock cuando el inventario es mayor a 0")
    public void testTieneStockPositivo() {
        Producto p = new Producto(1, "ELEC-100", "Teclado Mecánico", "Teclado RGB",
                new BigDecimal("120000"), 10, 1, true);

        assertTrue(p.tieneStock(), "El producto debería registrar stock disponible");
    }

    @Test
    @DisplayName("CP02 - Debe indicar que no tiene stock cuando el inventario es 0")
    public void testTieneStockCero() {
        Producto p = new Producto(2, "ELEC-101", "Monitor 24", "Monitor IPS",
                new BigDecimal("650000"), 0, 1, true);

        assertFalse(p.tieneStock(), "El producto con stock 0 no debe tener disponibilidad");
    }

    @Test
    @DisplayName("CP03 - Debe alertar stock bajo cuando sea menor a 5 unidades")
    public void testStockBajoVerdadero() {
        Producto p = new Producto(3, "ROPA-005", "Gorra Urbana", "Gorra ajustable",
                new BigDecimal("35000"), 3, 2, true);

        assertTrue(p.stockBajo(), "Un producto con 3 unidades debe considerarse con stock bajo (<5)");
    }

    @Test
    @DisplayName("CP04 - No debe alertar stock bajo cuando sea mayor o igual a 5 unidades")
    public void testStockBajoFalso() {
        Producto p = new Producto(4, "ROPA-006", "Sudadera", "Sudadera deportiva",
                new BigDecimal("80000"), 15, 2, true);

        assertFalse(p.stockBajo(), "Un producto con 15 unidades no tiene stock bajo");
    }

    @Test
    @DisplayName("CP05 - Validación de getters y setters de Producto")
    public void testGettersSetters() {
        Producto p = new Producto();
        p.setIdProducto(10);
        p.setCodigo("ALIM-010");
        p.setNombre("Galletas de Avena");
        p.setPrecio(new BigDecimal("4500.50"));
        p.setStock(20);
        p.setIdCategoria(3);
        p.setNombreCategoria("Alimentos");
        p.setActivo(true);

        assertEquals(10, p.getIdProducto());
        assertEquals("ALIM-010", p.getCodigo());
        assertEquals("Galletas de Avena", p.getNombre());
        assertEquals(new BigDecimal("4500.50"), p.getPrecio());
        assertEquals(20, p.getStock());
        assertEquals("Alimentos", p.getNombreCategoria());
        assertTrue(p.isActivo());
    }
}
