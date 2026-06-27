-- ============================================================================
-- SCRIPT DDL/DML - CAPA DE PERSISTENCIA RELACIONAL
-- SISTEMA DE GESTIÓN INTEGRAL PARA CONSIGNATARIOS DE HACIENDAS (SGCH)
-- MOTOR DE BASE DE DATOS: MySQL 8.0
-- DISEÑO: Modelo relacional normalizado bajo la Tercera Forma Normal (3FN).
-- ============================================================================

DROP DATABASE IF EXISTS sgch_db;
CREATE DATABASE sgch_db;
USE sgch_db;

-- ----------------------------------------------------------------------------
-- TABLA: clientes
-- DESCRIPCIÓN: Almacena los datos maestros de los productores ganaderos.
-- ENTIDAD FUERTE (Independiente)
-- ----------------------------------------------------------------------------
CREATE TABLE clientes (
    id_cliente BIGINT AUTO_INCREMENT PRIMARY KEY, -- Clave Primaria subrogada (Eficiencia de indexación)
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) UNIQUE NOT NULL,        -- Restricción UNIQUE para asegurar unicidad biunívoca
    ubicacion_campo VARCHAR(150),
    preferencias TEXT,                           -- Tipo TEXT para almacenar estructuras de datos variables
    fecha_alta DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- ----------------------------------------------------------------------------
-- TABLA: operaciones
-- DESCRIPCIÓN: Registra el historial transaccional completo de compra/venta.
-- ENTIDAD DEBIL (Dependiente de clientes)
-- ----------------------------------------------------------------------------
CREATE TABLE operaciones (
    id_operacion BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_cliente BIGINT NOT NULL,
    fecha_hora DATETIME NOT NULL,
    monto_total DECIMAL(15,2) NOT NULL,          -- Tipo DECIMAL para resguardar la precisión matemática financiera
    comision_comprador DECIMAL(10,2),
    comision_vendedor DECIMAL(10,2),
    tipo_hacienda VARCHAR(50) NOT NULL,
    cantidad INT NOT NULL,
    peso_promedio DECIMAL(6,2),
    estado_liquidacion VARCHAR(30) DEFAULT 'Pendiente',
    
    -- RESTRICCIÓN DE INTEGRIDAD REFERENCIAL:
    -- 'ON DELETE RESTRICT' impide la baja física de un cliente si posee transacciones históricas,
    -- garantizando la consistencia de los datos contables y de auditoría del negocio.
    CONSTRAINT fk_operacion_cliente 
        FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente) 
        ON DELETE RESTRICT
);

-- ----------------------------------------------------------------------------
-- TABLA: alertas
-- DESCRIPCIÓN: Almacena el historial operativo de notificaciones generadas (CU-03).
-- ENTIDAD OPERATIVA DEPENDIENTE
-- ----------------------------------------------------------------------------
CREATE TABLE alertas (
    id_alerta BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_cliente BIGINT NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    mensaje TEXT,
    fecha_generacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    atendida BOOLEAN DEFAULT FALSE,
    
    -- RESTRICCIÓN DE INTEGRIDAD EN CASCADA:
    -- 'ON DELETE CASCADE' limpia automáticamente las alertas huérfanas si la entidad 
    -- fuerte deja de existir, optimizando el espacio físico en disco de registros efímeros.
    CONSTRAINT fk_alerta_cliente 
        FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente) 
        ON DELETE CASCADE
);

-- ----------------------------------------------------------------------------
-- REGISTROS DE COMPROBACIÓN INICIAL (DML - Semilla de Datos)
-- ----------------------------------------------------------------------------
INSERT INTO clientes (nombre, telefono, ubicacion_campo, preferences) 
VALUES ('Estancia La Rural', '+5491122334455', 'Pergamino, BA', 'Novillos pesados');