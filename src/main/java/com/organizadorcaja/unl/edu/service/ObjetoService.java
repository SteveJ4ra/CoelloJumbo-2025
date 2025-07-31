package com.organizadorcaja.unl.edu.service;

import com.organizadorcaja.unl.edu.exception.CapacidadExcedidaException;
import com.organizadorcaja.unl.edu.model.Cajon;
import com.organizadorcaja.unl.edu.model.Objeto;
import com.organizadorcaja.unl.edu.repository.ObjetoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.Comparator;
import java.util.List;

@ApplicationScoped
@Transactional
public class ObjetoService {

    @Inject
    private ObjetoRepository objetoRepo;

    @Inject
    private HistorialService historialService;

    @Inject
    private CajonService cajonService;

    /**
     * Agrega un objeto a un cajón validando capacidad.
     */
    public void agregarObjeto(Cajon cajon, Objeto objeto) throws CapacidadExcedidaException {
        if (cajon.estaLleno()) {
            throw new CapacidadExcedidaException("El cajón ya está lleno");
        }
        objeto.setCajon(cajon);
        objetoRepo.crear(objeto);
        cajon.getObjetos().add(objeto); // mantener consistencia en memoria
        historialService.registrar(cajon, "Se agregó el objeto: " + objeto.getNombre());
    }

    /**
     * Lista todos los objetos en la base de datos.
     */
    public List<Objeto> listarTodos() {
        return objetoRepo.listarTodos();
    }

    /**
     * Lista todos los objetos de un cajón.
     */
    public List<Objeto> listarPorCajon(Long cajonId) {
        return objetoRepo.listarPorCajon(cajonId);
    }

    /**
     * Ordena los objetos de un cajón por tipo.
     */
    public List<Objeto> ordenarPorTipo(Long cajonId) {
        List<Objeto> objetos = objetoRepo.listarPorCajon(cajonId);
        objetos.sort(Comparator.comparing(
                Objeto::getTipo,
                Comparator.nullsLast(Comparator.naturalOrder())
        ));
        return objetos;
    }

    /**
     * Ordena los objetos de un cajón por tamaño.
     */
    public List<Objeto> ordenarPorTamano(Long cajonId) {
        List<Objeto> objetos = objetoRepo.listarPorCajon(cajonId);
        objetos.sort(Comparator.comparing(
                Objeto::getTamano,
                Comparator.nullsLast(Comparator.naturalOrder())
        ));
        return objetos;
    }

    /**
     * Elimina un objeto de un cajón y registra en historial.
     */
    public void eliminarObjeto(Long objetoId) {
        Objeto objeto = objetoRepo.buscarPorId(objetoId);
        if (objeto != null) {
            Cajon cajon = objeto.getCajon();
            objetoRepo.eliminar(objetoId);
            cajon.getObjetos().remove(objeto);
            historialService.registrar(cajon, "Se eliminó el objeto: " + objeto.getNombre());
        }
    }

    /**
     * Busca un objeto por su ID.
     */
    public Objeto buscarPorId(Long id) {
        return objetoRepo.buscarPorId(id);
    }

    /**
     * Actualiza un objeto existente.
     */
    public Objeto actualizarObjeto(Objeto objeto) {
        Objeto actualizado = objetoRepo.actualizar(objeto);
        historialService.registrar(objeto.getCajon(), "Se actualizó el objeto: " + objeto.getNombre());
        return actualizado;
    }

    public void crearObjeto(Long cajonId, Objeto objeto) throws CapacidadExcedidaException {
        Cajon cajon = cajonService.buscarPorId(cajonId);
        if (cajon != null) {
            agregarObjeto(cajon, objeto);
        }
    }
}
