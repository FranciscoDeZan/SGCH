package com.consignataria.sgch.service;

import com.consignataria.sgch.model.Operacion;
import java.time.LocalDateTime;
import java.time.Duration;

public class GestorOperacionesService implements IOperacionService {

    @Override
    public boolean registrarOperacion(Long idCliente, Operacion op) {
        try {
            // Algoritmo principal: Se llama al método de validación
            validarLimiteTiempo(op.getFechaHora());
            
            // Si no hay excepciones, se procedería a persistir en BD.
            System.out.println("Operación validada y registrada con éxito para el cliente: " + idCliente);
            return true;
            
        } catch (IllegalArgumentException e) {
            // Manejo de excepciones: Se captura el error de regla de negocio
            System.err.println("Error de validación (RN02): " + e.getMessage());
            return false;
        }
    }

    /**
     * Algoritmo de control para la Regla de Negocio RN02.
     * @param fechaOperacion La fecha ingresada por el operador.
     * @throws IllegalArgumentException si excede 2 horas o es futura.
     */
    private void validarLimiteTiempo(LocalDateTime fechaOperacion) throws IllegalArgumentException {
        LocalDateTime ahora = LocalDateTime.now();
        Duration diferencia = Duration.between(fechaOperacion, ahora);
        long minutosTranscurridos = diferencia.toMinutes();
        
        // Estructuras condicionales de control
        if (minutosTranscurridos > 120) {
            // Lanzamiento de excepción si se viola la RN02
            throw new IllegalArgumentException("La operación excede el límite de 2 horas desde su realización. Requiere justificación.");
        } else if (minutosTranscurridos < 0) {
            throw new IllegalArgumentException("La fecha de la operación es inválida (se encuentra en el futuro).");
        }
    }
}