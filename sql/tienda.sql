-- =========================================================
-- Script SQL: Sistema de Ventas - Tienda Web
-- Evidencia GA7-220501096-AA2-EV02
-- SENA - Tecnología en Análisis y Desarrollo de Software
-- =========================================================

CREATE DATABASE IF NOT EXISTS tienda_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_spanish_ci;

USE tienda_db;

-- ---------------------------------------------------------
-- TABLA: categorias
-- ---------------------------------------------------------
CREATE TABLE IF NOT EXISTS categorias (
    id_categoria   INT AUTO_INCREMENT PRIMARY KEY,
    nombre         VARCHAR(100) NOT NULL,
    descripcion    VARCHAR(255),
    activo         TINYINT(1) DEFAULT 1,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- ---------------------------------------------------------
-- TABLA: productos
-- ---------------------------------------------------------
CREATE TABLE IF NOT EXISTS productos (
    id_producto    INT AUTO_INCREMENT PRIMARY KEY,
    codigo         VARCHAR(50)    NOT NULL UNIQUE,
    nombre         VARCHAR(150)   NOT NULL,
    descripcion    TEXT,
    precio         DECIMAL(12,2)  NOT NULL DEFAULT 0.00,
    stock          INT            NOT NULL DEFAULT 0,
    id_categoria   INT,
    activo         TINYINT(1)     DEFAULT 1,
    fecha_creacion DATETIME       DEFAULT CURRENT_TIMESTAMP,
    fecha_update   DATETIME       ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_prod_cat FOREIGN KEY (id_categoria)
        REFERENCES categorias(id_categoria)
) ENGINE=InnoDB;

-- ---------------------------------------------------------
-- TABLA: clientes
-- ---------------------------------------------------------
CREATE TABLE IF NOT EXISTS clientes (
    id_cliente     INT AUTO_INCREMENT PRIMARY KEY,
    nombre         VARCHAR(100) NOT NULL,
    apellido       VARCHAR(100) NOT NULL,
    documento      VARCHAR(20)  NOT NULL UNIQUE,
    correo         VARCHAR(150),
    telefono       VARCHAR(20),
    direccion      VARCHAR(255),
    activo         TINYINT(1)   DEFAULT 1,
    fecha_creacion DATETIME     DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- ---------------------------------------------------------
-- TABLA: ventas
-- ---------------------------------------------------------
CREATE TABLE IF NOT EXISTS ventas (
    id_venta       INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente     INT,
    fecha_venta    DATETIME     DEFAULT CURRENT_TIMESTAMP,
    total          DECIMAL(14,2) NOT NULL DEFAULT 0.00,
    estado         ENUM('PENDIENTE','PAGADA','CANCELADA') DEFAULT 'PENDIENTE',
    observaciones  TEXT,
    CONSTRAINT fk_venta_cliente FOREIGN KEY (id_cliente)
        REFERENCES clientes(id_cliente)
) ENGINE=InnoDB;

-- ---------------------------------------------------------
-- TABLA: detalle_ventas
-- ---------------------------------------------------------
CREATE TABLE IF NOT EXISTS detalle_ventas (
    id_detalle     INT AUTO_INCREMENT PRIMARY KEY,
    id_venta       INT          NOT NULL,
    id_producto    INT          NOT NULL,
    cantidad       INT          NOT NULL DEFAULT 1,
    precio_unitario DECIMAL(12,2) NOT NULL,
    subtotal       DECIMAL(14,2) GENERATED ALWAYS AS (cantidad * precio_unitario) STORED,
    CONSTRAINT fk_det_venta   FOREIGN KEY (id_venta)    REFERENCES ventas(id_venta),
    CONSTRAINT fk_det_prod    FOREIGN KEY (id_producto) REFERENCES productos(id_producto)
) ENGINE=InnoDB;

-- ---------------------------------------------------------
-- DATOS DE PRUEBA
-- ---------------------------------------------------------
INSERT INTO categorias (nombre, descripcion) VALUES
    ('Electrónica',   'Dispositivos y accesorios electrónicos'),
    ('Ropa',          'Prendas de vestir para toda ocasión'),
    ('Alimentos',     'Productos alimenticios y bebidas'),
    ('Hogar',         'Artículos para el hogar'),
    ('Deportes',      'Artículos deportivos y fitness');

INSERT INTO productos (codigo, nombre, descripcion, precio, stock, id_categoria) VALUES
    ('ELEC-001', 'Audífonos Bluetooth',     'Audífonos inalámbricos con cancelación de ruido', 149900.00, 30, 1),
    ('ELEC-002', 'Cable USB-C 2m',          'Cable de carga rápida y datos USB-C 2 metros',     25000.00, 80, 1),
    ('ELEC-003', 'Mouse Inalámbrico',       'Mouse ergonómico inalámbrico 2.4GHz',               55000.00, 50, 1),
    ('ROPA-001', 'Camiseta Polo',           'Camiseta polo 100% algodón tallas S-XXL',           45000.00, 60, 2),
    ('ROPA-002', 'Jean Clásico',            'Jean clásico corte recto varios colores',           89000.00, 40, 2),
    ('ALIM-001', 'Café Molido 500g',        'Café colombiano de origen único molido fino',       28000.00, 100,3),
    ('ALIM-002', 'Chocolate en Polvo 400g', 'Chocolate soluble para bebidas calientes',          19500.00, 75, 3),
    ('HOGAR-001','Lámpara LED de Mesa',     'Lámpara LED táctil 3 intensidades recargable',      68000.00, 25, 4),
    ('DEPO-001', 'Botella Termo 1L',        'Botella termo acero inoxidable 1 litro',            42000.00, 45, 5),
    ('DEPO-002', 'Guantes de Boxeo 12oz',   'Guantes de entrenamiento cuero sintético',          75000.00, 20, 5);

INSERT INTO clientes (nombre, apellido, documento, correo, telefono, direccion) VALUES
    ('Jaider',   'Londoño',   '1234567890', 'jaider@correo.com',   '3001234567', 'Calle 10 # 20-30, Medellín'),
    ('María',    'García',    '0987654321', 'maria@correo.com',    '3109876543', 'Carrera 5 # 15-20, Bogotá'),
    ('Carlos',   'Martínez',  '1122334455', 'carlos@correo.com',   '3201122334', 'Avenida 3 # 45-67, Cali'),
    ('Ana',      'Rodríguez', '5566778899', 'ana@correo.com',      '3155566778', 'Calle 8 # 30-10, Barranquilla'),
    ('Luis',     'Gómez',     '9988776655', 'luis@correo.com',     '3009988776', 'Transversal 6 # 12-34, Medellín');

INSERT INTO ventas (id_cliente, total, estado) VALUES
    (1, 199900.00, 'PAGADA'),
    (2, 134000.00, 'PAGADA'),
    (3,  55000.00, 'PENDIENTE');

INSERT INTO detalle_ventas (id_venta, id_producto, cantidad, precio_unitario) VALUES
    (1, 1, 1, 149900.00),
    (1, 2, 2, 25000.00),
    (2, 4, 2, 45000.00),
    (2, 6, 1, 28000.00),
    (2, 9, 1, 42000.00) - (2*45000.00 + 28000.00 + 42000.00 - 134000.00),
    (3, 3, 1, 55000.00);

-- Vista resumen de ventas
CREATE OR REPLACE VIEW v_resumen_ventas AS
SELECT
    v.id_venta,
    CONCAT(c.nombre, ' ', c.apellido) AS cliente,
    c.documento,
    v.fecha_venta,
    v.total,
    v.estado,
    COUNT(d.id_detalle) AS num_items
FROM ventas v
JOIN clientes c ON v.id_cliente = c.id_cliente
JOIN detalle_ventas d ON v.id_venta = d.id_venta
GROUP BY v.id_venta, c.nombre, c.apellido, c.documento, v.fecha_venta, v.total, v.estado;
