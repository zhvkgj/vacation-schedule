package com.simple.task.vacationschedule.data.repository;

import com.simple.task.vacationschedule.model.employee.Employee;
import com.simple.task.vacationschedule.model.employee.Position;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {
    Optional<Employee> findByLoginAndPersNumber(String login, String persNumber);
    Optional<Employee> findByLogin(String login);
    Optional<Employee> findByPersNumber(String persNumber);
    List<Employee> findAllByPosition(Position position);
}
