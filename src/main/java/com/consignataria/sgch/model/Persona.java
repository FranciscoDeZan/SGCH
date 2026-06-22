package com.consignataria.sgch.model;

import jakarta.persistence.*;

// Le dice a JPA que las clases hijas heredarán estas columnas en la BD
@MappedSuperclass
public abstract class Persona {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente") // Se mapea a la columna física id_cliente
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "telefono", nullable = false, unique = true, length = 20)
    private String telefono;

    // Constructor vacío exigido por JPA
    public Persona() {}

    public Persona(Long id, String nombre, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public abstract String mostrarDetalle();
}