package com.consignataria.sgch.service;

import com.consignataria.sgch.model.Cliente;
import java.util.List;

public interface IClienteService {
    boolean registrarCliente(Cliente cliente);
    List<Cliente> obtenerTodos();
}