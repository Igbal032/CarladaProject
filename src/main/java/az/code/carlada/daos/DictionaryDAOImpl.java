package az.code.carlada.daos;

import az.code.carlada.daos.interfaces.DictionaryDAO;

import az.code.carlada.exceptions.DataNotFound;
import az.code.carlada.models.City;
import az.code.carlada.models.Make;
import az.code.carlada.models.Model;
import az.code.carlada.models.Specification;
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
    MakeRepo makeRepo;
    CityRepo cityRepo;
    SpecificationRepo specRepo;

    public DictionaryDAOImpl(ModelRepo modelRepo, MakeRepo makeRepo, CityRepo cityRepo, SpecificationRepo specRepo) {
        this.modelRepo = modelRepo;
        this.makeRepo = makeRepo;
        this.cityRepo = cityRepo;
        this.specRepo = specRepo;
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
    public List<Specification> findAllSpecificationById(Iterable<Long> id) {
        if (id == null) return null;
        return specRepo.findAllById(id);
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
