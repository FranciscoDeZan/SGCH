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

/**
 * PATRÓN MVC (Modelo/Servicio): Encapsula la lógica de negocio y las transacciones.
 * Implementa la interfaz IOperacionService aplicando el pilar de Abstracción.
 */
@Service
public class GestorOperacionesService implements IOperacionService {

    private final OperacionRepository operacionRepository;
    private final ArchivoService archivoService; 
    private final ClienteRepository clienteRepository;

    public GestorOperacionesService(OperacionRepository operacionRepository, ArchivoService archivoService, ClienteRepository clienteRepository) {
        this.operacionRepository = operacionRepository;
        this.archivoService = archivoService;
        this.clienteRepository = clienteRepository;
    }

    /**
     * Registra una operación aplicando reglas de negocio y manejo estricto de excepciones.
     */
    @Override
    public boolean registrarOperacion(Long idCliente, Operacion op) {
        try {
            // MANEJO DE EXCEPCIONES: Prevención de NullPointerException
            Objects.requireNonNull(idCliente, "idCliente no puede ser null");

            // Validación de Regla de Negocio RN02
            validarLimiteTiempo(op.getFechaHora());

            // Verifica integridad referencial
            com.consignataria.sgch.model.Cliente clienteEncontrado = clienteRepository.findById(idCliente)
                    .orElseThrow(() -> new IllegalArgumentException("El cliente no existe en la BD"));

            op.setCliente(clienteEncontrado);
            clienteEncontrado.agregarOperacion(op);
            op.calcularComisiones();
            
            // Persistencia
            operacionRepository.save(op);
            System.out.println("Operación persistida con éxito en MySQL para el cliente: " + idCliente);
            return true;

        } catch (IllegalArgumentException e) {
            // Captura errores de validación lógica (ej. Violación de RN02)
            System.err.println("Error de validación (RN02): " + e.getMessage());
            return false;
        } catch (DataAccessException e) {
            // Captura errores de integridad relacional en la base de datos (Spring DAO)
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

    /**
     * ALGORITMO RN02: Valida que la operación no exceda 2 horas de diferencia.
     * @throws IllegalArgumentException si se viola la regla temporal.
     */
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
        if (monto == null || monto < 0) {
            throw new IllegalArgumentException("El monto para el cálculo de saldos no puede ser negativo o nulo.");
        }
        System.out.println("Auditoría de saldo procesada para el flujo de caja. Monto impactado: $" + monto);
    }
}