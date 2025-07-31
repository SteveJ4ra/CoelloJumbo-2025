package com.organizadorcaja.unl.edu.service;

import com.organizadorcaja.unl.edu.model.Cajon;
import com.organizadorcaja.unl.edu.model.HistorialAccion;
import com.organizadorcaja.unl.edu.repository.HistorialRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
@Transactional
public class HistorialService {

    @Inject
    private HistorialRepository historialRepository;

    /**
     * Registra una acción en el historial asociada a un cajón.
     *
     * @param cajon   Cajón relacionado con la acción
     * @param mensaje Mensaje descriptivo de la acción
     */
    public void registrar(Cajon cajon, String mensaje) {
        HistorialAccion accion = new HistorialAccion();
        accion.setCajon(cajon);
        accion.setMensaje(mensaje);
        accion.setFecha(LocalDateTime.now());
        historialRepository.registrar(accion);
    }

    /**
     * Devuelve todo el historial registrado en la base de datos.
     *
     * @return lista de acciones registradas
     */
    public List<HistorialAccion> listarTodo() {
        return historialRepository.listarTodos();
    }

    /**
     * Devuelve el historial filtrado por un cajón en particular.
     *
     * @param cajonId id del cajón
     * @return lista de acciones asociadas al cajón
     */
    public List<HistorialAccion> listarPorCajon(Long cajonId) {
        return historialRepository.listarPorCajon(cajonId);
    }

    /**
     * Elimina un registro del historial.
     *
     * @param id id del registro del historial
     */
    public void eliminar(Long id) {
        historialRepository.eliminar(id);
    }
}
