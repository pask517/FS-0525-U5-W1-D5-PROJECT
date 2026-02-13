package andreapascarella.u5d10project.repositories;

import andreapascarella.u5d10project.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeesRepository extends JpaRepository<Employee, UUID> {

    Optional<Employee> findByEmail(String email);

    boolean existsByEmail(String email);
}
