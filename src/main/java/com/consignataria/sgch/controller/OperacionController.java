package com.consignataria.sgch.controller;

import com.consignataria.sgch.model.Operacion;
import com.consignataria.sgch.service.IOperacionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/operaciones")
public class OperacionController {

    private final IOperacionService operacionService;

    public OperacionController(IOperacionService operacionService) {
        this.operacionService = operacionService;
    }

    @GetMapping("/formulario")
    public String mostrarFormulario() {
        return "vistaFormulario";
    }

    @PostMapping("/registrar")
    public String procesarRegistro(@RequestParam Long idCliente, 
                                   @RequestParam Double montoTotal, 
                                   @RequestParam String tipoHacienda,
                                   @RequestParam Integer cantidad,
                                   Model model) {
        
        Operacion nuevaOp = new Operacion(idCliente, LocalDateTime.now(), montoTotal, tipoHacienda, cantidad);
        boolean exito = operacionService.registrarOperacion(idCliente, nuevaOp);
        
        if (exito) {
            model.addAttribute("mensaje", "Operación registrada correctamente en la base de datos.");
            return "vistaExito"; 
        } else {
            model.addAttribute("error", "Fallo al registrar. Verifique la Regla de Negocio (2 horas) o que el Cliente exista.");
            return "vistaFormulario"; 
        }
    }
}