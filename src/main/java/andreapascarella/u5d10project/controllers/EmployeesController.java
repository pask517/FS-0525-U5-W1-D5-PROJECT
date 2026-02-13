package andreapascarella.u5d10project.controllers;

import andreapascarella.u5d10project.entities.Employee;
import andreapascarella.u5d10project.exceptions.ValidationException;
import andreapascarella.u5d10project.payloads.EmployeeDTO;
import andreapascarella.u5d10project.services.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

    private final EmployeesService employeesService;

    @Autowired
    public EmployeesController(EmployeesService employeesService) {
        this.employeesService = employeesService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody @Validated EmployeeDTO payload, BindingResult validation) {
        if (validation.hasErrors()) {
            List<String> errorsList = validation.getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            throw new ValidationException(errorsList);
        } else {
            return this.employeesService.saveEmployee(payload);
        }
    }

    @GetMapping
    public Page<Employee> findAll(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "surname") String orderBy,
                                  @RequestParam(defaultValue = "asc") String sortCriteria) {

        return this.employeesService.findAll(page, size, orderBy, sortCriteria);
    }


}
