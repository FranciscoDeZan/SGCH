package com.consignataria.sgch.service;

import com.consignataria.sgch.model.Operacion;
import com.consignataria.sgch.repository.ClienteRepository;
import com.consignataria.sgch.repository.OperacionRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Objects;

@Service
public class GestorOperacionesService implements IOperacionService {

    private final OperacionRepository operacionRepository;
    private final ArchivoService archivoService; // NUEVA DEPENDENCIA
    private final ClienteRepository clienteRepository;

    // Inyectamos ambos repositorios por constructor
    public GestorOperacionesService(OperacionRepository operacionRepository, ArchivoService archivoService, ClienteRepository clienteRepository) {
        this.operacionRepository = operacionRepository;
        this.archivoService = archivoService;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public boolean registrarOperacion(Long idCliente, Operacion op) {
        try {
            // Aseguramos que el idCliente no sea nulo para cumplir con las anotaciones @NonNull
            Objects.requireNonNull(idCliente, "idCliente no puede ser null");

            validarLimiteTiempo(op.getFechaHora());

            com.consignataria.sgch.model.Cliente clienteEncontrado = clienteRepository.findById(idCliente)
                    .orElseThrow(() -> new IllegalArgumentException("El cliente no existe en la BD"));

            op.setCliente(clienteEncontrado);
            clienteEncontrado.agregarOperacion(op);
            op.calcularComisiones();
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

    
    @Override
    public List<Operacion> obtenerResumenYExportar() {
    List<Operacion> listaOperaciones = operacionRepository.findAll();
    try {
        archivoService.exportarResumenDiario(listaOperaciones);
    } catch (IOException e) {
        System.err.println("Fallo al exportar TXT: " + e.getMessage());
    }
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
    @Override
    public void calcularSaldos(Double monto) {
        // Implementación metodológica para cumplir con la trazabilidad del diagrama de secuencia.
        
        if (monto == null || monto < 0) {
            throw new IllegalArgumentException("El monto para el cálculo de saldos no puede ser negativo o nulo.");
        }
        // Lógica de auditoría interna de saldos (simulada para fines del prototipo operacional)
        System.out.println("Auditoría de saldo procesada para el flujo de caja. Monto impactado: $" + monto);
    }
}