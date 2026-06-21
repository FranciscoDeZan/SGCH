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

    // INYECCIÓN POR CONSTRUCTOR (Soluciona la advertencia de @Autowired)
    public OperacionController(IOperacionService operacionService) {
        this.operacionService = operacionService;
    }

    // MÉTODO PARA MOSTRAR LA PANTALLA
    @GetMapping("/formulario")
    public String mostrarFormulario() {
        return "vistaFormulario";
    }

    // MÉTODO PARA PROCESAR EL GUARDADO
    @PostMapping("/registrar")
    public String procesarRegistro(@RequestParam Long idCliente, 
                                   @RequestParam Double montoTotal, 
                                   @RequestParam String tipoHacienda,
                                   Model model) {
        
        Operacion nuevaOp = new Operacion(idCliente, LocalDateTime.now(), montoTotal, tipoHacienda);
        boolean exito = operacionService.registrarOperacion(idCliente, nuevaOp);
        
        if (exito) {
            model.addAttribute("mensaje", "Operación registrada correctamente.");
            return "vistaExito"; 
        } else {
            model.addAttribute("error", "Fallo al registrar. Verifique las reglas de negocio (RN02).");
            return "vistaFormulario"; 
        }
    }
}