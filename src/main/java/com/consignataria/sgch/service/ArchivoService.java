package com.consignataria.sgch.service;

import com.consignataria.sgch.model.Operacion;
import com.consignataria.sgch.repository.OperacionRepository;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Servicio encargado del manejo de Entrada/Salida (I/O) de archivos en texto plano.
 */
@Service
public class ArchivoService {

    private static final String RUTA_ARCHIVO = "resumen_operaciones.txt";
    private final OperacionRepository operacionRepository;

    public ArchivoService(OperacionRepository operacionRepository) {
        this.operacionRepository = operacionRepository;
    }

    /**
     * ESCRITURA (Output): Exporta una colección dinámica (ArrayList) a un archivo físico.
     * Implementa 'try-with-resources' para asegurar el cierre automático del flujo (BufferedWriter).
     */
    public void exportarResumenDiario(List<Operacion> operaciones) throws IOException {
        // El bloque try() asegura que el recurso se libere incluso si ocurre una excepción
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA_ARCHIVO, false))) {
            writer.write("--- RESUMEN DIARIO SGCH ---");
            writer.newLine();
            for (Operacion op : operaciones) {
                writer.write("ID Op: " + op.getIdOperacion() +
                     " | Productor: " + op.getCliente().getNombre() +
                     " | Cabezas: " + op.getCantidad() +
                     " | Hacienda: " + op.getTipoHacienda() +
                     " | Monto: $" + op.getMontoTotal());
                writer.newLine();
            }
            System.out.println("Archivo generado exitosamente en: " + RUTA_ARCHIVO);
        } catch (IOException e) {
            System.err.println("Error crítico I/O al escribir el archivo de respaldo: " + e.getMessage());
            throw e;  
        }
    }

    /**
     * LECTURA (Input): Recupera el archivo de respaldo físico.
     * Utiliza un arreglo estático (byte[]) para manipular el flujo binario de tamaño conocido.
     */
    public Resource recuperarArchivoResumen() throws IOException {
        Path path = Paths.get(RUTA_ARCHIVO);
        
        // Verifica la existencia del archivo, generándolo si es necesario
        if (!Files.exists(path) || Files.size(path) == 0) {
            List<Operacion> operaciones = operacionRepository.findAll();
            exportarResumenDiario(operaciones);
        }

        // ESTRUCTURA DE DATOS (Arreglo Estático): Manejo de flujo binario de longitud fija
        byte[] data = Files.readAllBytes(path);
        byte[] contenido = data != null ? data : new byte[0];

        // Se encapsula el arreglo binario para transferencia vía HTTP
        return new ByteArrayResource(contenido);
    }
}