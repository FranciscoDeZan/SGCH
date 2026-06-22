package com.consignataria.sgch.service;

import com.consignataria.sgch.model.Cliente;
import com.consignataria.sgch.repository.ClienteRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class GestorClientesService implements IClienteService {

    private final ClienteRepository clienteRepository;

    public GestorClientesService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public boolean registrarCliente(Cliente cliente) {
        try {
            // Validacion básica (RN: El teléfono no puede estar vacío)
            if (cliente.getTelefono() == null || cliente.getTelefono().trim().isEmpty()) {
                throw new IllegalArgumentException("El teléfono es obligatorio.");
            }
            
            clienteRepository.save(cliente);
            System.out.println("Cliente persistido con éxito: " + cliente.getNombre());
            return true;
            
        } catch (IllegalArgumentException e) {
            System.err.println("Error de validación: " + e.getMessage());
            return false;
        } catch (DataAccessException e) {
            System.err.println("Error en BD (Posible teléfono duplicado): " + e.getMessage());
            return false;
        }
    }
}