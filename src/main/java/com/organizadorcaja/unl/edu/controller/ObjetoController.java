package com.organizadorcaja.unl.edu.controller;

import com.organizadorcaja.unl.edu.exception.CapacidadExcedidaException;
import com.organizadorcaja.unl.edu.model.Cajon;
import com.organizadorcaja.unl.edu.model.Objeto;
import com.organizadorcaja.unl.edu.service.CajonService;
import com.organizadorcaja.unl.edu.service.ObjetoService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class ObjetoController implements Serializable {

    @Inject
    private ObjetoService objetoService;

    @Inject
    private CajonService cajonService;

    private List<Cajon> cajones;          // Lista de cajones para un combo en la vista
    private List<Objeto> objetos;         // Lista de objetos del cajón seleccionado

    private Long cajonSeleccionadoId;     // ID del cajón en el que vamos a trabajar
    private Objeto objetoNuevo;           // Para crear un nuevo objeto
    private Objeto objetoSeleccionado;    // Para editar/eliminar un objeto

    @PostConstruct
    public void init() {
        cajones = cajonService.listarCajones();
        objetoNuevo = new Objeto();
    }

    /**
     * Carga los objetos del cajón seleccionado.
     */
    public void cargarObjetos() {
        if (cajonSeleccionadoId != null) {
            objetos = objetoService.listarPorCajon(cajonSeleccionadoId);
        }
    }

    /**
     * Crea un objeto en el cajón seleccionado.
     */
    public void crearObjeto() {
        try {
            if (cajonSeleccionadoId != null) {
                objetoService.crearObjeto(cajonSeleccionadoId, objetoNuevo);
                mostrarMensaje("Éxito", "Objeto agregado correctamente.");
                objetoNuevo = new Objeto(); // reiniciamos el formulario
                cargarObjetos(); // refrescamos la lista
            }
        } catch (CapacidadExcedidaException e) {
            mostrarMensaje("Error", e.getMessage());
        }
    }

    /**
     * Elimina el objeto seleccionado.
     */
    public void eliminarObjeto(Long objetoId) {
        objetoService.eliminarObjeto(objetoId);
        mostrarMensaje("Éxito", "Objeto eliminado correctamente.");
        cargarObjetos();
    }

    /**
     * Ordena los objetos del cajón seleccionado por tipo.
     */
    public void ordenarPorTipo() {
        if (cajonSeleccionadoId != null) {
            objetos = objetoService.ordenarPorTipo(cajonSeleccionadoId);
            mostrarMensaje("Ordenamiento", "Objetos ordenados por tipo.");
        }
    }

    /**
     * Ordena los objetos del cajón seleccionado por tamaño.
     */
    public void ordenarPorTamano() {
        if (cajonSeleccionadoId != null) {
            objetos = objetoService.ordenarPorTamano(cajonSeleccionadoId);
            mostrarMensaje("Ordenamiento", "Objetos ordenados por tamaño.");
        }
    }

    /**
     * Método helper para mostrar mensajes en la vista JSF.
     */
    private void mostrarMensaje(String titulo, String detalle) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, titulo, detalle);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public List<Cajon> getCajones() {
        return cajones;
    }

    public void setCajones(List<Cajon> cajones) {
        this.cajones = cajones;
    }

    public List<Objeto> getObjetos() {
        return objetos;
    }

    public void setObjetos(List<Objeto> objetos) {
        this.objetos = objetos;
    }

    public Long getCajonSeleccionadoId() {
        return cajonSeleccionadoId;
    }

    public void setCajonSeleccionadoId(Long cajonSeleccionadoId) {
        this.cajonSeleccionadoId = cajonSeleccionadoId;
    }

    public Objeto getObjetoNuevo() {
        return objetoNuevo;
    }

    public void setObjetoNuevo(Objeto objetoNuevo) {
        this.objetoNuevo = objetoNuevo;
    }

    public Objeto getObjetoSeleccionado() {
        return objetoSeleccionado;
    }

    public void setObjetoSeleccionado(Objeto objetoSeleccionado) {
        this.objetoSeleccionado = objetoSeleccionado;
    }
}
