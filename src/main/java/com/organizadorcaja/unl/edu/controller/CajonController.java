package com.organizadorcaja.unl.edu.controller;

import com.organizadorcaja.unl.edu.model.Cajon;
import com.organizadorcaja.unl.edu.service.CajonService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class CajonController implements Serializable {

    @Inject
    private CajonService cajonService;

    private List<Cajon> cajones;
    private String nombreNuevoCajon;
    private int capacidadNueva;

    @PostConstruct
    public void init() {
        cajones = cajonService.listarCajones();
    }

    public void crearCajon() {
        cajonService.crearCajon(nombreNuevoCajon, capacidadNueva);
        cajones = cajonService.listarCajones();
    }

    public CajonService getCajonService() {
        return cajonService;
    }

    public void setCajonService(CajonService cajonService) {
        this.cajonService = cajonService;
    }

    public List<Cajon> getCajones() {
        return cajones;
    }

    public void setCajones(List<Cajon> cajones) {
        this.cajones = cajones;
    }

    public String getNombreNuevoCajon() {
        return nombreNuevoCajon;
    }

    public void setNombreNuevoCajon(String nombreNuevoCajon) {
        this.nombreNuevoCajon = nombreNuevoCajon;
    }

    public int getCapacidadNueva() {
        return capacidadNueva;
    }

    public void setCapacidadNueva(int capacidadNueva) {
        this.capacidadNueva = capacidadNueva;
    }
}

