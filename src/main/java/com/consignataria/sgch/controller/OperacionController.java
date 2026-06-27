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

/**
 * COMPONENTE CONTROLADOR - PATRÓN ARCHITECTÓNICO MVC: Clase responsable de interceptar
 * las solicitudes HTTP enviadas por el navegador web del operador, procesar los datos de entrada
 * y despachar las respuestas lógicas vinculando el Modelo con las Vistas (Thymeleaf).
 */
@Controller
@RequestMapping("/operaciones")
public class OperacionController {

    private final IOperacionService operacionService;
    private final IClienteService clienteService;
    private final ArchivoService archivoService;

    /**
     * INYECCIÓN DE DEPENDENCIAS POR CONSTRUCTOR: Promueve el desacoplamiento débil 
     * entre la capa de presentación y las capas de servicios de negocio.
     */
    public OperacionController(IOperacionService operacionService, IClienteService clienteService, ArchivoService archivoService) {
        this.operacionService = operacionService;
        this.clienteService = clienteService;
        this.archivoService = archivoService;
    }

    /**
     * Mapea la petición GET para renderizar el formulario web de registro de operaciones.
     * Carga dinámicamente el listado de productores registrados en la vista.
     */
    @GetMapping("/formulario")
    public String mostrarFormulario(Model model) {
        // Inyecta la lista de clientes para que el dropdown de la vista HTML pueda iterarla
        model.addAttribute("listaClientes", clienteService.obtenerTodos());
        return "vistaFormulario";
    }

    /**
     * Procesa el envío de datos del formulario mediante una petición POST.
     * Aplica validaciones y retorna la pantalla de éxito o error controlado.
     */
    @PostMapping("/registrar")
    public String procesarRegistro(@RequestParam Long idCliente, 
                                   @RequestParam Double montoTotal, 
                                   @RequestParam String tipoHacienda,
                                   @RequestParam Integer cantidad,
                                   Model model) {
        
        // Instancia un nuevo objeto de la clase Operacion con los datos recibidos
        Operacion nuevaOp = new Operacion(LocalDateTime.now(), montoTotal, tipoHacienda, cantidad);
        
        // Delega la ejecución de la transacción y la validación temporal (RN02) a la capa de servicio
        boolean exito = operacionService.registrarOperacion(idCliente, nuevaOp);
        
        if (exito) {
            model.addAttribute("mensaje", "Operación registrada correctamente en la base de datos.");
            return "vistaExito"; 
        } else {
            // Manejo alternativo en la UI ante fallas de validación de negocio o datos inexistentes
            model.addAttribute("error", "Fallo al registrar. Verifique la Regla de Negocio (2 horas) o que el Cliente exista.");
            model.addAttribute("listaClientes", clienteService.obtenerTodos()); // Recarga el dropdown
            return "vistaFormulario"; 
        }
    }

    /**
     * Mapea el acceso a la pantalla de resumen web de operaciones de la jornada.
     * Despacha concurrentemente la generación del archivo físico estructurado en disco.
     */
    @GetMapping("/resumen")
    public String mostrarResumen(Model model) {
        // Recupera la lista transaccional y ejecuta automáticamente la rutina de Entrada/Salida (I/O)
        model.addAttribute("listaOperaciones", operacionService.obtenerResumenYExportar());
        return "vistaResumen";
    }

    /**
     * ENDPOINT DE MANEJO DE ARCHIVOS (I/O): Permite la descarga directa vía HTTP del archivo 
     * físico de respaldo txt almacenado en el disco del servidor local.
     */
    @GetMapping("/descargar-respaldo")
    public ResponseEntity<Resource> descargarResumen() {
        try {
            // Lectura física del archivo persistido en formato binario estructurado
            Resource resource = archivoService.recuperarArchivoResumen();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"resumen_operaciones.txt\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
                    
        } catch (Exception e) {
            // CONTROL DE EXCEPCIONES: Captura cualquier fallo físico I/O de lectura previniendo caídas del servidor
            System.err.println("Error en la descarga del resumen I/O desde la capa web: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}