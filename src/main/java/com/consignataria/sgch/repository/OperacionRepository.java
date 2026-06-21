package com.consignataria.sgch.repository;

import com.consignataria.sgch.model.Operacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de Spring Data JPA para la entidad Operacion.
 * Abstrae las operaciones de base de datos (CRUD) conectándose a MySQL.
 */
@Repository
public interface OperacionRepository extends JpaRepository<Operacion, Long> {
    // Se pueden agregar consultas personalizadas aquí si el negocio lo requiere
}