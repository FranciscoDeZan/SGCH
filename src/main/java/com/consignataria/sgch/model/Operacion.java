package com.consignataria.sgch.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "operaciones")
public class Operacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_operacion")
    private Long idOperacion;

    @Column(name = "id_cliente", nullable = false)
    private Long idCliente;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Column(name = "monto_total", nullable = false)
    private Double montoTotal;

    @Column(name = "tipo_hacienda", nullable = false, length = 50)
    private String tipoHacienda;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad; // El campo faltante, integrado correctamente

    public Operacion() {}

    public Operacion(Long idCliente, LocalDateTime fechaHora, Double montoTotal, String tipoHacienda, Integer cantidad) {
        this.idCliente = idCliente;
        this.fechaHora = fechaHora;
        this.montoTotal = montoTotal;
        this.tipoHacienda = tipoHacienda;
        this.cantidad = cantidad;
    }

    // Getters y Setters
    public Long getIdOperacion() { return idOperacion; }
    public void setIdOperacion(Long idOperacion) { this.idOperacion = idOperacion; }

    public Long getIdCliente() { return idCliente; }
    public void setIdCliente(Long idCliente) { this.idCliente = idCliente; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public Double getMontoTotal() { return montoTotal; }
    public void setMontoTotal(Double montoTotal) { this.montoTotal = montoTotal; }

    public String getTipoHacienda() { return tipoHacienda; }
    public void setTipoHacienda(String tipoHacienda) { this.tipoHacienda = tipoHacienda; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
}