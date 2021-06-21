package az.code.carlada.repositories;


import az.code.carlada.models.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepo extends JpaRepository<City, Long> {
}
