package com.consignataria.sgch.controller;

import com.consignataria.sgch.model.Operacion;
import com.consignataria.sgch.service.ArchivoService;
import com.consignataria.sgch.service.IClienteService;
import com.consignataria.sgch.service.IOperacionService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/operaciones")
public class OperacionController {

    private final IOperacionService operacionService;
    private final IClienteService clienteService;
    private final ArchivoService archivoService;

    public OperacionController(IOperacionService operacionService, IClienteService clienteService, ArchivoService archivoService) {
        this.operacionService = operacionService;
        this.clienteService = clienteService;
        this.archivoService = archivoService;
    }

    @GetMapping("/formulario")
    public String mostrarFormulario(Model model) {
        model.addAttribute("listaClientes", clienteService.obtenerTodos());
        return "vistaFormulario";
    }

    @PostMapping("/registrar")
    public String procesarRegistro(@RequestParam Long idCliente, 
                                   @RequestParam Double montoTotal, 
                                   @RequestParam String tipoHacienda,
                                   @RequestParam Integer cantidad,
                                   Model model) {
        
        Operacion nuevaOp = new Operacion(LocalDateTime.now(), montoTotal, tipoHacienda, cantidad);
        boolean exito = operacionService.registrarOperacion(idCliente, nuevaOp);
        
        if (exito) {
            model.addAttribute("mensaje", "Operación registrada correctamente en la base de datos.");
            return "vistaExito"; 
        } else {
            model.addAttribute("error", "Fallo al registrar. Verifique la Regla de Negocio (2 horas) o que el Cliente exista.");
            return "vistaFormulario"; 
        }
    }
    @GetMapping("/resumen")
    public ResponseEntity<Resource> descargarResumen() {
        try {
            // 1. Cierre del Ciclo I/O: Obligamos al servicio a generar/actualizar el archivo físico
            // con los datos más recientes de la base de datos antes de intentar leerlo.
            operacionService.obtenerResumenYExportar();

            // 2. Recuperamos el recurso físico recién consolidado
            Resource resource = archivoService.recuperarArchivoResumen();

            // 3. Despachamos el archivo al cliente. 
            // Se eliminó el 'Objects.requireNonNull' sobre la constante nativa de Spring 
            // para mantener la pulcritud y el rigor académico del código.
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"resumen_operaciones.txt\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
                    
        } catch (Exception e) {
            System.err.println("Error en la descarga del resumen I/O: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}