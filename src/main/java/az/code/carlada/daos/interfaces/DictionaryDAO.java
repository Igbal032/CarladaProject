package az.code.carlada.daos.interfaces;


import az.code.carlada.models.Model;
import az.code.carlada.models.Specification;

import java.util.List;

public interface DictionaryDAO<E> {
    List<E> getData(Class<E> clazz);

    List<Model> getModelsByMake(Long id);

    E findDataById(Long id, Class<E> clazz);

    List<E> findAllDataByIds(Iterable<Long> id, Class<E> clazz);
}
