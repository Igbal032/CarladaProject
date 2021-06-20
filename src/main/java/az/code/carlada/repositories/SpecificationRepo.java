package az.code.carlada.repositories;

import az.code.carlada.models.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpecificationRepo extends JpaRepository<Specification, Long> {
}
