package com.consignataria.sgch.service;

import com.consignataria.sgch.model.Operacion;
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
 * Servicio encargado del manejo de Entrada/Salida (I/O) de archivos.
 */
@Service
public class ArchivoService {

    private static final String RUTA_ARCHIVO = "resumen_operaciones.txt";

    /**
     * Exporta una lista de operaciones a un archivo de texto utilizando FileWriter y BufferedWriter.
     * @param operaciones Lista (ArrayList) de operaciones a exportar.
     */
    public void exportarResumenDiario(List<Operacion> operaciones) {
        // Uso de try-with-resources para asegurar el cierre del archivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA_ARCHIVO, false))) {
            
            writer.write("--- RESUMEN DIARIO SGCH ---");
            writer.newLine();
            
            for (Operacion op : operaciones) {
                writer.write("ID Op: " + op.getIdOperacion() + 
             " | Productor: " + op.getCliente().getNombre() + // Muestra el nombre!
             " | Cabezas: " + op.getCantidad() +
             " | Hacienda: " + op.getTipoHacienda() +
             " | Monto: $" + op.getMontoTotal());
                writer.newLine();
            }
            System.out.println("Archivo de resumen generado exitosamente en: " + RUTA_ARCHIVO);
            
        } catch (IOException e) {
            System.err.println("Error crítico de Entrada/Salida (I/O) al escribir el archivo: " + e.getMessage());
        }
    }

    /**
     * Recupera (lee) el archivo físico del disco para enviarlo al cliente.
     * CUMPLE CON EL REQUISITO: "Recuperar información relevante".
     */
    public Resource recuperarRespaldoComoRecurso() throws IOException {
        Path path = Paths.get(RUTA_ARCHIVO);
        if (!Files.exists(path)) {
            throw new IOException("El archivo de respaldo no existe aún.");
        }
        byte[] data = Files.readAllBytes(path);
        return new ByteArrayResource(data);
    }
}