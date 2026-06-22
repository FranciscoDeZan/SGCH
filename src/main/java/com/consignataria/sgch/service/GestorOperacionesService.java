package com.consignataria.sgch.service;

import com.consignataria.sgch.model.Operacion;
import com.consignataria.sgch.repository.OperacionRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.List;

@Service
public class GestorOperacionesService implements IOperacionService {

    private final OperacionRepository operacionRepository;
    private final ArchivoService archivoService; // NUEVA DEPENDENCIA

    // Inyectamos ambos repositorios por constructor
    public GestorOperacionesService(OperacionRepository operacionRepository, ArchivoService archivoService) {
        this.operacionRepository = operacionRepository;
        this.archivoService = archivoService;
    }

    @Override
    public boolean registrarOperacion(Long idCliente, Operacion op) {
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
            System.err.println("Error crítico en base de datos: " + e.getMessage());
            return false;
        }
    }

    // NUEVO MÉTODO: Trae datos de BD, activa el ArrayList y exporta el archivo TXT
    @Override
    public List<Operacion> obtenerResumenYExportar() {
        List<Operacion> listaOperaciones = operacionRepository.findAll();
        
        // Cumplimos con la consigna de Manejo de Archivos (I/O)
        archivoService.exportarResumenDiario(listaOperaciones);
        
        return listaOperaciones;
    }

    private void validarLimiteTiempo(LocalDateTime fechaOperacion) throws IllegalArgumentException {
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