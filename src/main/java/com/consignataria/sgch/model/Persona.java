package com.consignataria.sgch.model;

import jakarta.persistence.*;

/**
 * PILAR POO - ABSTRACCIÓN: Clase abstracta que define el comportamiento genérico 
 * y los atributos comunes de cualquier persona en el sistema.
 * No puede ser instanciada directamente.
 */
@MappedSuperclass // Indica a JPA que las clases hijas heredarán estas columnas en la BD
public abstract class Persona {
    
    // PILAR POO - ENCAPSULAMIENTO: Atributos privados, accesibles vía getters/setters.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "telefono", nullable = false, unique = true, length = 20)
    private String telefono;

    /**
     * Constructor vacío requerido por el estándar JPA/Hibernate.
     */
    public Persona() {}

    /**
     * Constructor sobrecargado para inicializar atributos heredables.
     */
    public Persona(Long id, String nombre, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    // Getters y Setters (Encapsulamiento)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    /**
     * PILAR POO - ABSTRACCIÓN / POLIMORFISMO: Método abstracto que obliga 
     * a las subclases a definir su propio comportamiento de presentación.
     */
    public abstract String mostrarDetalle();
}