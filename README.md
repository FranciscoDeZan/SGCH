# 🐄 SGCH - Sistema de Gestión Integral para Consignatarios de Haciendas

[![Java Version](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.14-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)

Este repositorio contiene el prototipo operacional del sistema **SGCH**, un software de gestión comercial diseñado específicamente para optimizar la trazabilidad de operaciones, la gestión de clientes y la automatización de seguimientos en el sector ganadero. 

Desarrollado como proyecto integrador para la materia **Seminario de Práctica de Informática** (Universidad Siglo 21).

---

## 🚀 Características Principales (Módulos)

* **Gestión de Entidades Comerciales:** Registro y administración de productores y compradores con perfiles detallados (ubicación, preferencias de ganado).
* **Control Transaccional:** Registro de operaciones comerciales con cálculo automático de comisiones y estados de liquidación.
* **Motor de Reglas de Negocio:** Validación estricta de tiempo de carga de operaciones (Regla RN02 - Límite de carga diferida de 2 horas) mediante algoritmos de control de flujo.
* **Trazabilidad y Colecciones:** Historial dinámico de operaciones vinculado a cada cliente.

---

## 🛠️ Stack Tecnológico

El proyecto está construido bajo una arquitectura multicapa (MVC) utilizando las siguientes tecnologías:

* **Lenguaje Core:** Java 17 (LTS)
* **Framework Principal:** Spring Boot 3.5.14
* **Gestor de Dependencias:** Apache Maven
* **Persistencia de Datos:** Spring Data JPA / Hibernate
* **Motor Base de Datos:** MySQL 8.0
* **Frontend (Vistas):** HTML5, CSS3, Thymeleaf

---

## 🧠 Arquitectura y Diseño (POO)

El núcleo del sistema está diseñado respetando estrictamente los 4 pilares de la Programación Orientada a Objetos para garantizar escalabilidad y mantenimiento:

1. **Abstracción:** Definición de contratos de servicio mediante interfaces (ej. `IOperacionService`) para desacoplar la lógica de negocio de los controladores.
2. **Encapsulamiento:** Ocultamiento del estado interno de las entidades de dominio (atributos `private` con acceso vía getters/setters).
3. **Herencia:** Implementación de la superclase abstracta `Persona` que hereda atributos base (nombre, teléfono) a subclases específicas como `Cliente`.
4. **Polimorfismo:** Sobrescritura de métodos (`@Override`) como `mostrarDetalle()` para adaptar el comportamiento según el tipo de entidad.
5. **Robustez:** Manejo estructurado de excepciones (bloques `try-catch` y `throw new IllegalArgumentException`) para proteger la integridad del sistema ante datos inválidos.

---

## ⚙️ Estructura del Repositorio

* `/src/main/java/.../model`: Entidades del dominio y estructuras de datos (`List<Operacion>`).
* `/src/main/java/.../service`: Capa de lógica de negocio y algoritmos de validación.
* `/src/main/java/.../controller`: Controladores HTTP para la gestión de peticiones web.
* `/src/main/resources/templates`: Vistas dinámicas en Thymeleaf.
* `sgch_db.sql`: Script DDL (Creación de tablas con integridad referencial) y DML (Inserción de datos de prueba) para MySQL.

---

## 👨‍💻 Autor

**Francisco De Zan** * **Legajo:** VINF016466
* **Institución:** Universidad Siglo 21
* **Carrera:** Licenciatura en Informática