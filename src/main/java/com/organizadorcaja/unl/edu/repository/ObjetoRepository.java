package com.organizadorcaja.unl.edu.repository;

import com.organizadorcaja.unl.edu.model.Objeto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ObjetoRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void crear(Objeto objeto) {
        em.persist(objeto);
    }

    @Transactional
    public Objeto actualizar(Objeto objeto) {
        return em.merge(objeto);
    }

    @Transactional
    public void eliminar(Long id) {
        Objeto objeto = em.find(Objeto.class, id);
        if (objeto != null) {
            em.remove(objeto);
        }
    }

    public Objeto buscarPorId(Long id) {
        return em.find(Objeto.class, id);
    }

    public List<Objeto> listarTodos() {
        return em.createQuery("SELECT o FROM Objeto o", Objeto.class).getResultList();
    }

    public List<Objeto> listarPorCajon(Long cajonId) {
        return em.createQuery("SELECT o FROM Objeto o WHERE o.cajon.id = :cajonId", Objeto.class)
                .setParameter("cajonId", cajonId)
                .getResultList();
    }
}

