package com.organizadorcaja.unl.edu.repository;

import com.organizadorcaja.unl.edu.model.HistorialAccion;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class HistorialRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void registrar(HistorialAccion accion) {
        em.persist(accion);
    }

    public HistorialAccion buscarPorId(Long id) {
        return em.find(HistorialAccion.class, id);
    }

    public List<HistorialAccion> listarTodos() {
        return em.createQuery("SELECT h FROM HistorialAccion h ORDER BY h.fecha DESC", HistorialAccion.class)
                .getResultList();
    }

    public List<HistorialAccion> listarPorCajon(Long cajonId) {
        return em.createQuery("SELECT h FROM HistorialAccion h WHERE h.cajon.id = :cajonId ORDER BY h.fecha DESC", HistorialAccion.class)
                .setParameter("cajonId", cajonId)
                .getResultList();
    }

    @Transactional
    public void eliminar(Long id) {
        HistorialAccion accion = em.find(HistorialAccion.class, id);
        if (accion != null) {
            em.remove(accion);
        }
    }
}

