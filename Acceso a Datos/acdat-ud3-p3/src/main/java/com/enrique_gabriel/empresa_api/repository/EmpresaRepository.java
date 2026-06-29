package com.enrique_gabriel.empresa_api.repository;

import com.enrique_gabriel.empresa_api.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
}