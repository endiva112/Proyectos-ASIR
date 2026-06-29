package com.enrique_gabriel.empresa_api.service;

import com.enrique_gabriel.empresa_api.entity.Empleado;
import com.enrique_gabriel.empresa_api.entity.Empresa;
import com.enrique_gabriel.empresa_api.entity.MovimientoDinero;
import org.springframework.stereotype.Service;

/*
@Service
public class EmpresaService {

    private final Empresa empresa;

    public EmpresaService() {
        MovimientoDinero mov = new MovimientoDinero(0, "Sin movimientos");
        Empleado emp = new Empleado("Ana", "ana@mail.com", "ADMIN", mov);
        this.empresa = new Empresa("Empresa Demo", "Calle 123", "555-123", "NIT-001", emp);
    }

    public Empresa obtenerEmpresa() {
        return empresa;
    }
}*/

//Versión necesaria para el JPA
import com.enrique_gabriel.empresa_api.repository.EmpresaRepository;

import java.util.List;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    public List<Empresa> listar() {
        return empresaRepository.findAll();
    }

    public Empresa crear(Empresa empresa) {
        return empresaRepository.save(empresa);
    }
}