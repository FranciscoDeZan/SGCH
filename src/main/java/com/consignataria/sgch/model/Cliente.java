package com.consignataria.sgch.model;

import java.util.ArrayList;
import java.util.List;

// Herencia: Cliente extiende la clase base Persona
public class Cliente extends Persona {
    private String ubicacionCampo;
    private String preferencias;
    
    // Estructura de Datos (Colección) para el historial
    private List<Operacion> historialCompras;

    public Cliente(Long id, String nombre, String telefono, String ubicacionCampo, String preferencias) {
        super(id, nombre, telefono); // Invoca al constructor de Persona
        this.ubicacionCampo = ubicacionCampo;
        this.preferencias = preferencias;
        this.historialCompras = new ArrayList<>(); // Inicialización de la estructura
    }

    public String getUbicacionCampo() { return ubicacionCampo; }
    public void setUbicacionCampo(String ubicacionCampo) { this.ubicacionCampo = ubicacionCampo; }

    public String getPreferencias() { return preferencias; }
    public void setPreferencias(String preferencias) { this.preferencias = preferencias; }

    public List<Operacion> getHistorialCompras() { return historialCompras; }

    public void agregarOperacion(Operacion op) {
        this.historialCompras.add(op);
    }

    // Polimorfismo: Sobrescritura del método abstracto de Persona
    @Override
    public String mostrarDetalle() {
        return "Cliente: " + getNombre() + " | Tel: " + getTelefono() + " | Campo: " + this.ubicacionCampo;
    }
}