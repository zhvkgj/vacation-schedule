package com.simple.task.vacationschedule.data.repository;

import com.simple.task.vacationschedule.model.employee.Employee;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {
    Optional<Employee> findByLoginAndPersNumber(String login, String persNumber);
    Optional<Employee> findByLogin(String login);
    Optional<Employee> findByPersNumber(String persNumber);
}
