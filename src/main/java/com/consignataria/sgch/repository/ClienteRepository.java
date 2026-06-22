package com.consignataria.sgch.repository;

import com.consignataria.sgch.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}