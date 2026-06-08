package com.consignataria.sgch.model;

import java.time.LocalDateTime;

public class Operacion {
    private Long idOperacion;
    private LocalDateTime fechaHora;
    private Double montoTotal;
    private String tipoHacienda;

    public Operacion(Long idOperacion, LocalDateTime fechaHora, Double montoTotal, String tipoHacienda) {
        this.idOperacion = idOperacion;
        this.fechaHora = fechaHora;
        this.montoTotal = montoTotal;
        this.tipoHacienda = tipoHacienda;
    }

    // Getters y Setters
    public Long getIdOperacion() { return idOperacion; }
    public void setIdOperacion(Long idOperacion) { this.idOperacion = idOperacion; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public Double getMontoTotal() { return montoTotal; }
    public void setMontoTotal(Double montoTotal) { this.montoTotal = montoTotal; }

    public String getTipoHacienda() { return tipoHacienda; }
    public void setTipoHacienda(String tipoHacienda) { this.tipoHacienda = tipoHacienda; }
}