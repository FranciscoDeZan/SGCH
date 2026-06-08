-- A)	Creación de Estructura (DDL - Data Definition Language)
-- Creación de la base de datos
CREATE DATABASE IF NOT EXISTS sgch_db;
USE sgch_db;

-- Tabla de Clientes
CREATE TABLE clientes (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) UNIQUE NOT NULL,
    ubicacion_campo VARCHAR(150),
    preferencias TEXT,
    fecha_alta DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de Operaciones (Vinculada a Clientes - ON DELETE RESTRICT)
CREATE TABLE operaciones (
    id_operacion INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    fecha_hora DATETIME NOT NULL,
    monto_total DECIMAL(15,2) NOT NULL,
    comision_comprador DECIMAL(10,2),
    comision_vendedor DECIMAL(10,2),
    tipo_hacienda VARCHAR(50) NOT NULL,
    cantidad INT NOT NULL,
    peso_promedio DECIMAL(6,2),
    estado_liquidacion VARCHAR(30) DEFAULT 'Pendiente',
    CONSTRAINT fk_operacion_cliente FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente) ON DELETE RESTRICT
);

-- Tabla de Alertas (Vinculada a Clientes - ON DELETE CASCADE)
CREATE TABLE alertas (
    id_alerta INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    mensaje TEXT,
    fecha_generacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    atendida BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_alerta_cliente FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente) ON DELETE CASCADE
);
-- B)	Manipulación de Datos (DML - Data Manipulation Language)

-- 1. INSERCIÓN: Registrar un nuevo cliente (CU-01)
INSERT INTO clientes (nombre, telefono, ubicacion_campo, preferencias) 
VALUES ('Estancia La Rural', '+5491122334455', 'Pergamino, BA', 'Novillos pesados, compra en marzo');

-- 2. INSERCIÓN: Registrar una operación (CU-02) para el cliente ID 1
INSERT INTO operaciones (id_cliente, fecha_hora, monto_total, comision_comprador, tipo_hacienda, cantidad, peso_promedio) 
VALUES (1, '2026-05-15 10:30:00', 5000000.00, 150000.00, 'Novillos', 100, 420.50);

-- 3. CONSULTA: Obtener el historial completo de operaciones de un cliente específico, mostrando su nombre
SELECT c.nombre, o.fecha_hora, o.tipo_hacienda, o.monto_total 
FROM clientes c
JOIN operaciones o ON c.id_cliente = o.id_cliente
WHERE c.id_cliente = 1
ORDER BY o.fecha_hora DESC;

-- 4. BORRADO: Eliminar una operación errónea (sujeto a validación de reglas de negocio)
DELETE FROM operaciones WHERE id_operacion = 1;
