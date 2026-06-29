package com.enrique_gabriel.empresa_api.controller;

import com.enrique_gabriel.empresa_api.entity.Empresa;
import com.enrique_gabriel.empresa_api.service.EmpresaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
@RestController
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping("/empresa")
    public Empresa empresa() {
        return empresaService.obtenerEmpresa();
    }
}*/

//Versión para el JPA
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping
    public List<Empresa> listar() {
        return empresaService.listar();
    }

    @PostMapping
    public Empresa crear(@RequestBody Empresa empresa) {
        return empresaService.crear(empresa);
    }
}