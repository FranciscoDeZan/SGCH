# Sistema de Gestión Integral para Consignatarios de Haciendas (SGCH) 🐄

Este repositorio contiene el prototipo operacional del sistema **SGCH**, desarrollado como trabajo final integrador para la materia *Seminario de Práctica de Informática* (Universidad Siglo 21).

El SGCH busca digitalizar y optimizar las operaciones de intermediación ganadera en el Nordeste Argentino, abandonando los registros en papel para migrar hacia una solución robusta, centralizada y escalable.

---

## 🏗️ Arquitectura y Tecnologías
El sistema fue construido siguiendo el **Proceso Unificado de Desarrollo (PUD)** y aplica el patrón arquitectónico **MVC (Modelo-Vista-Controlador)**.

* **Backend:** Java 17 + Spring Boot 3.5.x
* **Persistencia de Datos (ORM):** Spring Data JPA / Hibernate
* **Motor de Base de Datos:** MySQL 8.0
* **Frontend (Vistas):** HTML5 + Thymeleaf + Bootstrap 5
* **Manejo de Archivos:** E/S nativa de Java (Generación y recuperación de TXT vía HTTP)
* **Paradigma:** Programación Orientada a Objetos (POO) estricta (Herencia, Polimorfismo, Encapsulamiento, Abstracción).

---

## ⚙️ Características Principales (Casos de Uso)
1. **Gestión de Productores (CU-01):** Alta de clientes con validación de datos y protección contra duplicados.
2. **Registro de Operaciones (CU-02):** Registro transaccional de compra/venta de hacienda relacionando dinámicamente objetos en BD (`@ManyToOne`).
3. **Reglas de Negocio (RN02):** Implementación de algoritmos de control de tiempo (bloqueo de operaciones con más de 2 horas de antigüedad).
4. **Respaldo Físico (I/O):** Exportación y descarga directa desde el navegador de resúmenes operativos en archivos `.txt`.

---

## 🚀 Instalación y Despliegue Local

### Prerrequisitos
* Java Development Kit (JDK) 17 o superior.
* Maven instalado.
* Motor MySQL (XAMPP o MySQL Server).

### Pasos

1. **Clonar el repositorio:**
    git clone https://github.com/FranciscoDeZan/SGCH.git

2. **Preparar la Base de Datos:**
    Abrir MySQL Workbench o phpMyAdmin y ejecutar el script alojado en la raíz del proyecto: `sgch_db.sql`.

3. **Configurar Credenciales:**
    Verificar el archivo `src/main/resources/application.properties` y ajustar la contraseña de la base de datos MySQL local.

4. **Ejecutar la Aplicación:**
    Desde la terminal en la raíz del proyecto, ejecutar:
    ./mvnw spring-boot:run

5. **Acceder al Sistema:**
    Abrir un navegador web e ingresar a: http://localhost:8080/

---

## 👨‍💻 Autor
**Francisco De Zan** - *Legajo: VINF016466* Licenciatura en Informática - Universidad Siglo 21