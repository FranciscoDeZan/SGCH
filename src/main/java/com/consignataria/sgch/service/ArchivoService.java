package com.consignataria.sgch.service;

import com.consignataria.sgch.model.Operacion;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class ArchivoService {

    private static final String RUTA_ARCHIVO = "resumen_operaciones.txt";

    // 1. MÉTODO PARA GUARDAR (Escritura / Salida)
    public void exportarResumenDiario(List<Operacion> operaciones) {
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
            System.out.println("Archivo de respaldo generado exitosamente.");
            
            // LLAMAMOS AL MÉTODO DE LECTURA PARA CUMPLIR LA CONSIGNA DE "RECUPERAR"
            recuperarResumenDiario();
            
        } catch (IOException e) {
            System.err.println("Error crítico de E/S al escribir: " + e.getMessage());
        }
    }

    // 2. NUEVO MÉTODO PARA RECUPERAR (Lectura / Entrada)
    public void recuperarResumenDiario() {
        System.out.println("\n--- LEYENDO Y RECUPERANDO ARCHIVO TXT ---");
        try (BufferedReader reader = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            String linea;
            // Lee el archivo línea por línea hasta que se acabe
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }
            System.out.println("--- FIN DE RECUPERACIÓN DEL ARCHIVO ---\n");
        } catch (IOException e) {
            System.err.println("Error al intentar recuperar el archivo: " + e.getMessage());
        }
    }
}