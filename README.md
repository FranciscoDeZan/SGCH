# Sistema de Gestión Integral para Consignatarios de Haciendas (SGCH) 🐄

Este repositorio contiene el prototipo operacional del sistema **SGCH**, desarrollado como trabajo final integrador para la materia *Seminario de Práctica de Informática* (Universidad Siglo 21).

El SGCH opera como un **Sistema de Procesamiento de Transacciones (TPS)** que busca digitalizar y optimizar las operaciones de intermediación ganadera en el Nordeste Argentino, abandonando los registros en papel para migrar hacia una solución robusta, centralizada y escalable.

---

## 🎥 Demostración del Prototipo
🎬 **https://youtu.be/wHwkmiIw9BE**

El video recorre: el contexto de la problemática comercial, la demostración del registro de clientes y operaciones, la validación en tiempo real de la Regla de Negocio 02 (límite de 2 horas), y la generación del respaldo físico de Entrada/Salida.

---

## 📚 Trazabilidad Académica (Rúbrica AP4)
El código fuente de este repositorio se encuentra estratégicamente documentado (vía JavaDoc y comentarios en línea) para evidenciar la aplicación de los siguientes requerimientos técnicos exigidos por la cátedra:
* **Pilares de la POO:** Aplicación práctica de Herencia, Abstracción, Polimorfismo y Encapsulamiento (ver superclase `Persona.java` y subclase `Cliente.java`).
* **Manejo de Excepciones:** Prevención y captura de errores lógicos y de base de datos (`IllegalArgumentException`, `DataAccessException` en la capa de Servicios).
* **Estructuras de Datos:** Diferenciación entre colecciones dinámicas (`ArrayList`) para el manejo en memoria y persistencia ORM, frente a arreglos estáticos (`byte[]`) para el tratamiento de archivos físicos de E/S.
* **Capa DDL/DML:** Script SQL (`sgch_db.sql`) normalizado en 3FN, con resguardo estricto de integridad referencial (`ON DELETE RESTRICT` y `CASCADE`).

---

## 🏗️ Arquitectura y Tecnologías
El sistema fue construido siguiendo el **Proceso Unificado de Desarrollo (PUD)** y aplica estrictamente el patrón arquitectónico **MVC (Modelo-Vista-Controlador)** para garantizar un bajo acoplamiento.

* **Backend:** Java 17 + Spring Boot 3.5.x
* **Persistencia de Datos (ORM):** Spring Data JPA / Hibernate
* **Motor de Base de Datos:** MySQL 8.0
* **Frontend (Vistas):** HTML5 + Thymeleaf + Bootstrap 5
* **Manejo de Archivos (I/O):** E/S nativa de Java (Escritura mediante `try-with-resources` y recuperación de flujos binarios vía HTTP).

---

## ⚙️ Características Principales (Casos de Uso)
1. **Gestión de Productores (CU-01):** Alta de clientes con validación de datos y protección contra registros duplicados (vía restricciones UNIQUE en BD).
2. **Registro de Operaciones (CU-02):** Registro transaccional de compra/venta de hacienda relacionando dinámicamente objetos en BD (`@ManyToOne`).
3. **Reglas de Negocio (RN02):** Implementación de algoritmos de control de tiempo (bloqueo automático de operaciones con más de 120 minutos de antigüedad).
4. **Respaldo Físico (I/O):** Exportación y descarga directa desde el navegador de resúmenes operativos diarios estructurados en formato `.txt`.

---

## 🚀 Instalación y Despliegue Local

### Prerrequisitos
* Java Development Kit (JDK) 17 o superior.
* Apache Maven instalado en las variables de entorno.
* Motor MySQL 8.0 (XAMPP, MySQL Server local o contenedor Docker).

### Pasos de Ejecución

1. **Clonar el repositorio:**
   ```bash
   git clone [https://github.com/FranciscoDeZan/SGCH.git](https://github.com/FranciscoDeZan/SGCH.git)
   ```

2. **Preparar la Base de Datos:**
   Abrir el gestor de base de datos (MySQL Workbench, DBeaver o phpMyAdmin) y ejecutar el script `sgch_db.sql` alojado en la raíz del proyecto. *Este script ya incluye la estructura 3FN y los datos semilla iniciales.*

3. **Configurar Credenciales:**
   Verificar el archivo `src/main/resources/application.properties` y ajustar la contraseña (`spring.datasource.password`) para que coincida con las credenciales de tu servidor local.

4. **Compilar y Ejecutar:**
   Desde la terminal, posicionado en la raíz del proyecto, limpiar la caché de compilación y levantar el servidor web embebido Tomcat:
   ```bash
   mvn clean compile
   ./mvnw spring-boot:run
   ```

5. **Acceder al Sistema:**
   Abrir un navegador web e ingresar a la interfaz gráfica: [http://localhost:8080/](http://localhost:8080/)

---

## 👨‍💻 Autor
**Francisco De Zan** - *Legajo: VINF016466* Licenciatura en Informática - Universidad Siglo 21
