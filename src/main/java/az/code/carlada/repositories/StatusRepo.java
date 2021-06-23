package az.code.carlada.repositories;

import az.code.carlada.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepo extends JpaRepository<Status, Long>
{
    Status getStatusByStatusName(String statusName);
}
