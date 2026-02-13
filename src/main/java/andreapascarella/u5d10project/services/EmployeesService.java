package andreapascarella.u5d10project.services;

import andreapascarella.u5d10project.entities.Employee;
import andreapascarella.u5d10project.exceptions.BadRequestException;
import andreapascarella.u5d10project.payloads.EmployeeDTO;
import andreapascarella.u5d10project.repositories.EmployeesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class EmployeesService {

    private final EmployeesRepository employeesRepository;

    @Autowired
    public EmployeesService(EmployeesRepository employeesRepository) {
        this.employeesRepository = employeesRepository;
    }

    public Employee saveEmployee(EmployeeDTO payload) {
        this.employeesRepository.findByEmail(payload.email()).ifPresent(employee -> {
            throw new BadRequestException("L'email " + employee.getEmail() + " è già in uso!");
        });

        Employee newEmployee = new Employee(payload.username(), payload.name(), payload.surname(), payload.email());

        Employee savedEmployee = this.employeesRepository.save(newEmployee);

        log.info("L'utente con id " + savedEmployee.getEmployeeId() + " è stato salvato correttamente!");

        return savedEmployee;
    }

    public Page<Employee> findAll(int page, int size, String orderBy, String sortCriteria) {
        if (size > 100 || size < 0) size = 10;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size,
                sortCriteria.equals("desc") ? Sort.by(orderBy).descending() : Sort.by(orderBy));
        return this.employeesRepository.findAll(pageable);
    }
}
