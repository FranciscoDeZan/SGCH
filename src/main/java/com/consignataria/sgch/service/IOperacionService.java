package com.consignataria.sgch.service;

import com.consignataria.sgch.model.Operacion;

public interface IOperacionService {
    boolean registrarOperacion(Long idCliente, Operacion op);
}