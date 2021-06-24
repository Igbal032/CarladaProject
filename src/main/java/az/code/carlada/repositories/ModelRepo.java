package az.code.carlada.repositories;

import az.code.carlada.models.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ModelRepo extends JpaRepository<Model, Long> {
    List<Model> findByMakeId(Long make_id);
}
