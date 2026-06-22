package com.consignataria.sgch.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clientes")
public class Cliente extends Persona {
    
    @Column(name = "ubicacion_campo", length = 150)
    private String ubicacionCampo;

    @Column(name = "preferencias", columnDefinition = "TEXT")
    private String preferencias;
    
    // @Transient le dice a la BD que ignore esta lista, ya que es lógica pura de Java
    @Transient
    private List<Operacion> historialCompras;

    // Constructor vacío exigido por JPA
    public Cliente() {
        super();
        this.historialCompras = new ArrayList<>();
    }

    public Cliente(Long id, String nombre, String telefono, String ubicacionCampo, String preferencias) {
        super(id, nombre, telefono); 
        this.ubicacionCampo = ubicacionCampo;
        this.preferencias = preferencias;
        this.historialCompras = new ArrayList<>(); 
    }

    public String getUbicacionCampo() { return ubicacionCampo; }
    public void setUbicacionCampo(String ubicacionCampo) { this.ubicacionCampo = ubicacionCampo; }

    public String getPreferencias() { return preferencias; }
    public void setPreferencias(String preferencias) { this.preferencias = preferencias; }

    public List<Operacion> getHistorialCompras() { return historialCompras; }
    public void agregarOperacion(Operacion op) { this.historialCompras.add(op); }

    @Override
    public String mostrarDetalle() {
        return "Cliente: " + getNombre() + " | Tel: " + getTelefono() + " | Campo: " + this.ubicacionCampo;
    }
}