package com.consignataria.sgch.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * PILAR POO - HERENCIA: Cliente hereda los atributos y métodos de la superclase Persona.
 * Representa a un productor ganadero en el dominio del negocio.
 */
@Entity
@Table(name = "clientes")
public class Cliente extends Persona {
    
    @Column(name = "ubicacion_campo", length = 150)
    private String ubicacionCampo;

    @Column(name = "preferencias", columnDefinition = "TEXT")
    private String preferencias;
    
    /**
     * ESTRUCTURAS DE DATOS (Colección Dinámica): Se utiliza un ArrayList para 
     * gestionar el historial en memoria de forma escalable.
     * @Transient indica que esta lista es lógica de Java y no una columna SQL.
     */
    @Transient
    private List<Operacion> historialCompras;

    public Cliente() {
        super();
        this.historialCompras = new ArrayList<>(); // Instanciación de la colección
    }

    public Cliente(Long id, String nombre, String telefono, String ubicacionCampo, String preferencias) {
        super(id, nombre, telefono); 
        this.ubicacionCampo = ubicacionCampo;
        this.preferencias = preferencias;
        this.historialCompras = new ArrayList<>(); 
    }

    // Getters y Setters
    public String getUbicacionCampo() { return ubicacionCampo; }
    public void setUbicacionCampo(String ubicacionCampo) { this.ubicacionCampo = ubicacionCampo; }

    public String getPreferencias() { return preferencias; }
    public void setPreferencias(String preferencias) { this.preferencias = preferencias; }

    public List<Operacion> getHistorialCompras() { return historialCompras; }
    
    public void agregarOperacion(Operacion op) { 
        this.historialCompras.add(op); 
    }

    /**
     * PILAR POO - POLIMORFISMO: Se redefine (@Override) el comportamiento 
     * heredado de Persona para incluir detalles específicos del ganadero.
     */
    @Override
    public String mostrarDetalle() {
        return "Cliente: " + getNombre() + " | Tel: " + getTelefono() + " | Campo: " + this.ubicacionCampo;
    }
}