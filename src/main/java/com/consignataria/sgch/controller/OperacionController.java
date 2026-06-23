package com.consignataria.sgch.controller;

import com.consignataria.sgch.model.Operacion;
import com.consignataria.sgch.service.IClienteService;
import com.consignataria.sgch.service.IOperacionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/operaciones")
public class OperacionController {

    private final IOperacionService operacionService;
    private final IClienteService clienteService;

    public OperacionController(IOperacionService operacionService, IClienteService clienteService) {
        this.operacionService = operacionService;
        this.clienteService = clienteService;
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
    // NUEVO CONTROLADOR: Para la pantalla de Resumen
    @GetMapping("/resumen")
    public String mostrarResumen(Model model) {
        // Obtenemos los datos y exportamos el archivo
        var operaciones = operacionService.obtenerResumenYExportar();
        
        // Mandamos el ArrayList al HTML
        model.addAttribute("listaOperaciones", operaciones);
        
        return "vistaResumen"; // Busca vistaResumen.html
    }
}