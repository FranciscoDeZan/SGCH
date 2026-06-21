DROP DATABASE IF EXISTS sgch_db;
CREATE DATABASE sgch_db;
USE sgch_db;

-- Tabla de Clientes (IDs como BIGINT para soportar escalabilidad)
CREATE TABLE clientes (
    id_cliente BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) UNIQUE NOT NULL,
    ubicacion_campo VARCHAR(150),
    preferencias TEXT,
    fecha_alta DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de Operaciones (Alineada al modelo Java)
CREATE TABLE operaciones (
    id_operacion BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_cliente BIGINT NOT NULL,
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

-- Tabla de Alertas
CREATE TABLE alertas (
    id_alerta BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_cliente BIGINT NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    mensaje TEXT,
    fecha_generacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    atendida BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_alerta_cliente FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente) ON DELETE CASCADE
);

-- INSERCIÓN DE PRUEBA (Para tener un cliente válido con ID 1)
INSERT INTO clientes (nombre, telefono, ubicacion_campo, preferencias) 
VALUES ('Estancia La Rural', '+5491122334455', 'Pergamino, BA', 'Novillos pesados');