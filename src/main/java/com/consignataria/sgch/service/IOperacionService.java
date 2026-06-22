package com.consignataria.sgch.service;

import com.consignataria.sgch.model.Operacion;
import java.util.List; // Importante agregar esto

public interface IOperacionService {
    boolean registrarOperacion(Long idCliente, Operacion op);
    List<Operacion> obtenerResumenYExportar(); // NUEVO MÉTODO
}