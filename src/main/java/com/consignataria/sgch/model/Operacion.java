package com.consignataria.sgch.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * CLASE DE ENTIDAD - CAPA DE MODELO: Representa una transacción comercial 
 * de compra o venta de hacienda dentro del dominio del negocio ganadero.
 * * PILAR POO - ENCAPSULAMIENTO: Todos los atributos son privados y su estado
 * interno se gestiona exclusivamente mediante métodos accesores públicos.
 */
@Entity
@Table(name = "operaciones")
public class Operacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_operacion")
    private Long idOperacion;

    /**
     * RELACIÓN ORIENTADA A OBJETOS: Mapea una relación de muchos a uno (@ManyToOne).
     * Múltiples operaciones pertenecen a un único cliente. El atributo 'nullable = false'
     * exige la presencia de la entidad fuerte para evitar registros huérfanos en la BD.
     */
    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente; 

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Column(name = "monto_total", nullable = false)
    private Double montoTotal;

    @Column(name = "tipo_hacienda", nullable = false, length = 50)
    private String tipoHacienda;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad; 

    @Column(name = "comision_comprador")
    private Double comisionComprador = 0.0;

    @Column(name = "comision_vendedor")
    private Double comisionVendedor = 0.0;

    @Column(name = "peso_promedio")
    private Double pesoPromedio = 0.0;

    @Column(name = "estado_liquidacion")
    private String estadoLiquidacion = "PENDIENTE";

    /**
     * Constructor por defecto requerido obligatoriamente por JPA/Hibernate.
     */
    public Operacion() {}

    /**
     * Constructor sobrecargado para la inicialización estructurada de transacciones 
     * desde la capa de presentación.
     */
    public Operacion(LocalDateTime fechaHora, Double montoTotal, String tipoHacienda, Integer cantidad) {
        this.fechaHora = fechaHora;
        this.montoTotal = montoTotal;
        this.tipoHacienda = tipoHacienda;
        this.cantidad = cantidad;
    }

    /**
     * LÓGICA DE NEGOCIO ENCAPSULADA: El objeto de dominio calcula de forma autónoma
     * sus propias comisiones comerciales (3% comprador, 2% vendedor). 
     * Evita el antipatrón de "modelo anémico" al centralizar la lógica aquí en lugar de en el controlador.
     */
    public void calcularComisiones() {
        if (this.montoTotal != null && this.montoTotal > 0) {
            this.comisionComprador = this.montoTotal * 0.03;
            this.comisionVendedor = this.montoTotal * 0.02;
        } else {
            this.comisionComprador = 0.0;
            this.comisionVendedor = 0.0;
        }
    }

    // =======================================================
    // MÉTODOS ACCESORES (Mantenimiento del Encapsulamiento)
    // =======================================================
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

    public Double getComisionComprador() { return comisionComprador; }
    public void setComisionComprador(Double comisionComprador) { this.comisionComprador = comisionComprador; }

    public Double getComisionVendedor() { return comisionVendedor; }
    public void setComisionVendedor(Double comisionVendedor) { this.comisionVendedor = comisionVendedor; }

    public Double getPesoPromedio() { return pesoPromedio; }
    public void setPesoPromedio(Double pesoPromedio) { this.pesoPromedio = pesoPromedio; }

    public String getEstadoLiquidacion() { return estadoLiquidacion; }
    public void setEstadoLiquidacion(String estadoLiquidacion) { this.estadoLiquidacion = estadoLiquidacion; }
}