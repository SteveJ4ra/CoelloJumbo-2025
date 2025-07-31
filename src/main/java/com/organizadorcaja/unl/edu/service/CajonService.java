package com.organizadorcaja.unl.edu.service;

import com.organizadorcaja.unl.edu.exception.CapacidadExcedidaException;
import com.organizadorcaja.unl.edu.model.Cajon;
import com.organizadorcaja.unl.edu.model.Objeto;
import com.organizadorcaja.unl.edu.repository.CajonRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@ApplicationScoped
@Transactional
public class CajonService {

    @Inject
    private CajonRepository cajonRepo;

    @Inject
    private HistorialService historialService;

    /**
     * Crea un nuevo cajón y lo persiste en la base de datos.
     */
    public void crearCajon(String nombre, int capacidad) {
        Cajon cajon = new Cajon();
        cajon.setNombre(nombre);
        cajon.setCapacidadMaxima(capacidad);
        cajonRepo.guardar(cajon);
        historialService.registrar(cajon, "Se creó el cajón: " + nombre);
    }

    /**
     * Lista todos los cajones registrados en la base de datos.
     */
    public List<Cajon> listarCajones() {
        return cajonRepo.listarTodos();
    }

    /**
     * Agrega un objeto a un cajón, validando que no se exceda la capacidad.
     */
    public void agregarObjeto(Cajon cajon, Objeto objeto) throws CapacidadExcedidaException {
        if (cajon.estaLleno()) {
            throw new CapacidadExcedidaException("El cajón ya está lleno");
        }
        cajon.getObjetos().add(objeto);
        objeto.setCajon(cajon);
        historialService.registrar(cajon, "Se agregó objeto: " + objeto.getNombre());
    }

    /**
     * Elimina objetos duplicados en un cajón, manteniendo solo los únicos.
     */
    public int eliminarDuplicados(Cajon cajon) {
        Set<Objeto> unicos = new LinkedHashSet<>(cajon.getObjetos());
        int eliminados = cajon.getObjetos().size() - unicos.size();
        cajon.getObjetos().clear();
        cajon.getObjetos().addAll(unicos);
        historialService.registrar(cajon, "Se eliminaron " + eliminados + " objetos duplicados");
        return eliminados;
    }

    /**
     * Obtiene un cajón por su ID.
     */
    public Cajon buscarPorId(Long id) {
        return cajonRepo.buscarPorId(id);
    }

    /**
     * Elimina un cajón de la base de datos.
     */
    public void eliminarCajon(Long id) {
        Cajon cajon = cajonRepo.buscarPorId(id);
        if (cajon != null) {
            cajonRepo.eliminar(cajon);
            historialService.registrar(cajon, "Se eliminó el cajón: " + cajon.getNombre());
        }
    }
}
