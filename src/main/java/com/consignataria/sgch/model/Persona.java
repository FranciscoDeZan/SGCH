package com.consignataria.sgch.model;

public abstract class Persona {
    // Encapsulamiento: atributos privados
    private Long id;
    private String nombre;
    private String telefono;

    // Constructor
    public Persona(Long id, String nombre, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    // Abstracción y Polimorfismo: Método que las clases hijas deberán implementar
    public abstract String mostrarDetalle();
}