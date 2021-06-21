package az.code.carlada.repositories;

import az.code.carlada.models.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecificationRepo extends JpaRepository<Specification, Long> {
}