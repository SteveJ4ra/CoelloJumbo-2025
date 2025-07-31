package com.organizadorcaja.unl.edu.repository;

import com.organizadorcaja.unl.edu.model.Cajon;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class CajonRepository {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public void guardar(Cajon cajon) {
        em.persist(cajon);
    }

    public Cajon buscarPorId(Long id) {
        return em.find(Cajon.class, id);
    }

    public List<Cajon> listarTodos() {
        return em.createQuery("SELECT c FROM Cajon c", Cajon.class).getResultList();
    }

    @Transactional
    public Cajon actualizar(Cajon cajon) {
        return em.merge(cajon);
    }

    @Transactional
    public void eliminar(Cajon cajon) {
        Cajon cajonRef = cajon;
        if (!em.contains(cajon)) {
            cajonRef = em.merge(cajon); // Lo volvemos "managed" antes de eliminarlo
        }
        em.remove(cajonRef);
    }
}
