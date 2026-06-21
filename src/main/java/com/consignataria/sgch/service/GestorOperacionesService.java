package com.consignataria.sgch.service;

import com.consignataria.sgch.model.Operacion;
import com.consignataria.sgch.repository.OperacionRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Duration;

@Service
public class GestorOperacionesService implements IOperacionService {

    private final OperacionRepository operacionRepository;

    // INYECCIÓN POR CONSTRUCTOR (Soluciona la advertencia de @Autowired)
    public GestorOperacionesService(OperacionRepository operacionRepository) {
        this.operacionRepository = operacionRepository;
    }

    @Override
    public boolean registrarOperacion(Long idCliente, Operacion op) {
        // ... (El resto del método se mantiene exactamente igual con los try-catch)
        try {
            validarLimiteTiempo(op.getFechaHora());
            op.setIdCliente(idCliente);
            operacionRepository.save(op);
            System.out.println("Operación persistida con éxito en MySQL para el cliente: " + idCliente);
            return true;
        } catch (IllegalArgumentException e) {
            System.err.println("Error de validación (RN02): " + e.getMessage());
            return false;
        } catch (DataAccessException e) {
            System.err.println("Error crítico al guardar en la base de datos: " + e.getMessage());
            return false;
        }
    }

    private void validarLimiteTiempo(LocalDateTime fechaOperacion) throws IllegalArgumentException {
        // ... (El método validarLimiteTiempo se mantiene igual)
        LocalDateTime ahora = LocalDateTime.now();
        Duration diferencia = Duration.between(fechaOperacion, ahora);
        long minutosTranscurridos = diferencia.toMinutes();
        
        if (minutosTranscurridos > 120) {
            throw new IllegalArgumentException("La operación excede el límite de 2 horas (RN02).");
        } else if (minutosTranscurridos < 0) {
            throw new IllegalArgumentException("La fecha de la operación es futura.");
        }
    }
}