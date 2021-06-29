package az.code.carlada.daos;

import az.code.carlada.daos.interfaces.DictionaryDAO;


import az.code.carlada.models.Model;
import az.code.carlada.repositories.*;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;

@Component
public class DictionaryDAOImpl<E> implements DictionaryDAO<E> {
    @PersistenceContext
    EntityManager em;
    ModelRepo modelRepo;

    public DictionaryDAOImpl(ModelRepo modelRepo) {
        this.modelRepo = modelRepo;
    }

    @Override
    public List<E> getData(Class<E> clazz) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<E> cq = cb.createQuery(clazz);
        Root<E> root = cq.from(clazz);
        cq.select(root);
        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<E> findAllDataByIds(Iterable<Long> id, Class<E> clazz) {
        if (id == null) return null;
        List<E> data = new ArrayList<>();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<E> cq = cb.createQuery(clazz);
        Root<E> root = cq.from(clazz);
        for (Long l : id) {
            cq.select(root).where(cb.equal(root.get("id"), l));
            data.add(em.createQuery(cq).getSingleResult());
        }
        return data;
    }

    @Override
    public List<Model> getModelsByMake(Long id) {
        return modelRepo.findByMakeId(id);
    }

    @Override
    public E findDataById(Long id, Class<E> clazz) {
        if (id == null) return null;
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<E> cq = cb.createQuery(clazz);
        Root<E> root = cq.from(clazz);
        cq.select(root).where(cb.equal(root.get("id"), id));
        return em.createQuery(cq).getSingleResult();
    }
}
