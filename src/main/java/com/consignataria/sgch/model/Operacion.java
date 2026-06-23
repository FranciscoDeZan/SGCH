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

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente; // Ahora la operación conoce al Cliente completo

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Column(name = "monto_total", nullable = false)
    private Double montoTotal;

    @Column(name = "tipo_hacienda", nullable = false, length = 50)
    private String tipoHacienda;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad; // El campo faltante, integrado correctamente

    public Operacion() {}

    public Operacion(LocalDateTime fechaHora, Double montoTotal, String tipoHacienda, Integer cantidad) {
        this.fechaHora = fechaHora;
        this.montoTotal = montoTotal;
        this.tipoHacienda = tipoHacienda;
        this.cantidad = cantidad;
    }

    // Getters y Setters
    public Long getIdOperacion() { return idOperacion; }
    public void setIdOperacion(Long idOperacion) { this.idOperacion = idOperacion; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public Double getMontoTotal() { return montoTotal; }
    public void setMontoTotal(Double montoTotal) { this.montoTotal = montoTotal; }

    public String getTipoHacienda() { return tipoHacienda; }
    public void setTipoHacienda(String tipoHacienda) { this.tipoHacienda = tipoHacienda; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
}