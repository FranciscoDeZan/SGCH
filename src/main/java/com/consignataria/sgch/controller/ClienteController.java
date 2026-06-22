package com.consignataria.sgch.controller;

import com.consignataria.sgch.model.Cliente;
import com.consignataria.sgch.service.IClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private final IClienteService clienteService;

    public ClienteController(IClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/formulario")
    public String mostrarFormulario() {
        return "vistaFormularioCliente";
    }

    @PostMapping("/registrar")
    public String procesarRegistro(@RequestParam String nombre, 
                                   @RequestParam String telefono, 
                                   @RequestParam String ubicacionCampo,
                                   @RequestParam String preferencias,
                                   Model model) {
        
        // El ID viaja nulo porque la BD lo genera automáticamente (AUTO_INCREMENT)
        Cliente nuevoCliente = new Cliente(null, nombre, telefono, ubicacionCampo, preferencias);
        
        boolean exito = clienteService.registrarCliente(nuevoCliente);
        
        if (exito) {
            model.addAttribute("mensaje", "Productor Ganadero registrado correctamente.");
            return "vistaExito"; // Reutilizamos la misma pantalla de éxito de operaciones
        } else {
            model.addAttribute("error", "Fallo al registrar. Verifique que el teléfono no esté duplicado.");
            return "vistaFormularioCliente"; 
        }
    }
}