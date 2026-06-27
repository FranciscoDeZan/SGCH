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
 * Servicio encargado del manejo de Entrada/Salida (I/O) de archivos.
 */
@Service
public class ArchivoService {

    private static final String RUTA_ARCHIVO = "resumen_operaciones.txt";
    private final OperacionRepository operacionRepository;

    public ArchivoService(OperacionRepository operacionRepository) {
        this.operacionRepository = operacionRepository;
    }

    /**
     * Exporta una lista de operaciones a un archivo de texto utilizando FileWriter y BufferedWriter.
     * @param operaciones Lista (ArrayList) de operaciones a exportar.
     */
    public void exportarResumenDiario(List<Operacion> operaciones) throws IOException {
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
        System.out.println("Archivo generado: " + RUTA_ARCHIVO);
    } catch (IOException e) {
        System.err.println("Error crítico I/O al escribir archivo: " + e.getMessage());
        throw e;  
    }
}
    /**
     * Recupera el archivo de resumen, generándolo de forma segura si aún no existe.
     */
    public Resource recuperarArchivoResumen() throws IOException {
        Path path = Paths.get(RUTA_ARCHIVO);
        if (!Files.exists(path) || Files.size(path) == 0) {
            List<Operacion> operaciones = operacionRepository.findAll();
            exportarResumenDiario(operaciones);
        }

        byte[] data = Files.readAllBytes(path);
        byte[] contenido = data != null ? data : new byte[0];

        return new ByteArrayResource(contenido);
    }
}