package com.simple.task.vacationschedule.data.repository;

import com.simple.task.vacationschedule.model.employee.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    Optional<Employee> findByLoginAndPersNumber(String login, String persNumber);
}
