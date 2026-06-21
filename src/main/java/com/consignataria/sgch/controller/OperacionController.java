package com.consignataria.sgch.controller;

import com.consignataria.sgch.model.Operacion;
import com.consignataria.sgch.service.IOperacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * Controlador MVC que gestiona las peticiones web para Operaciones.
 * Separa la vista (HTML/Thymeleaf) de la lógica de negocio.
 */
@Controller
@RequestMapping("/operaciones")
public class OperacionController {

    @Autowired
    private IOperacionService operacionService;

    // Método para mostrar la pantalla HTML
    @GetMapping("/formulario")
    public String mostrarFormulario() {
        return "vistaFormulario"; // Busca el archivo vistaFormulario.html en templates
    }
    // Simula la recepción de un formulario web para registrar una operación
    @PostMapping("/registrar")
    public String procesarRegistro(@RequestParam Long idCliente, 
                                   @RequestParam Double montoTotal, 
                                   @RequestParam String tipoHacienda,
                                   Model model) {
        
        Operacion nuevaOp = new Operacion(idCliente, LocalDateTime.now(), montoTotal, tipoHacienda);
        
        boolean exito = operacionService.registrarOperacion(idCliente, nuevaOp);
        
        if (exito) {
            model.addAttribute("mensaje", "Operación registrada correctamente.");
            return "vistaExito"; // Retorna el nombre de la vista HTML
        } else {
            model.addAttribute("error", "Fallo al registrar. Verifique las reglas de negocio (RN02).");
            return "vistaFormulario"; 
        }
    }
}