package az.code.carlada.repositories;

import az.code.carlada.models.Make;
import az.code.carlada.models.Model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MakeRepo extends JpaRepository<Make, Long> {
}
