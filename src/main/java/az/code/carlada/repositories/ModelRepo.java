package az.code.carlada.repositories;

import az.code.carlada.models.Model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelRepo extends JpaRepository<Model,Long> {
}
