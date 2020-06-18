package com.simple.task.vacationschedule.data.service;

import com.simple.task.vacationschedule.data.repository.EmployeeRepository;
import com.simple.task.vacationschedule.data.repository.VacationRepository;
import com.simple.task.vacationschedule.model.employee.Employee;
import com.simple.task.vacationschedule.model.vacation.Vacation;
import org.springframework.stereotype.Service;

import javax.management.InstanceNotFoundException;
import javax.persistence.EntityExistsException;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final VacationRepository vacationRepository;

    public EmployeeService(EmployeeRepository employeeRepository, VacationRepository vacationRepository) {
        this.employeeRepository = employeeRepository;
        this.vacationRepository = vacationRepository;
    }

    public Employee createEmployee(Employee employee) throws EntityExistsException {
        Optional<Employee> employee1 =
                employeeRepository.findByLoginAndPersNumber(employee.getLogin(), employee.getPersNumber());

        if (employee1.isPresent()) {
            throw new EntityExistsException();
        }

        return employeeRepository.save(employee);
    }

    public Employee updateEmployeeById(long id, Employee employee) throws InstanceNotFoundException {
        Optional<Employee> employee1 = employeeRepository.findById(id);
        employee1.orElseThrow(InstanceNotFoundException::new);
        employee.setId(id);

        return employeeRepository.save(employee);
    }

    public Employee deleteEmployeeById(long id) throws InstanceNotFoundException {
        Optional<Employee> employee = employeeRepository.findById(id);
        employee.orElseThrow(InstanceNotFoundException::new);
        employeeRepository.deleteById(id);

        return employee.get();
    }

    public Employee getEmployeeById(long id) throws InstanceNotFoundException {
        Optional<Employee> employee = employeeRepository.findById(id);
        employee.orElseThrow(InstanceNotFoundException::new);

        return employee.get();
    }

    public Employee addVacationToEmployee(long id, Vacation vacation) throws InstanceNotFoundException{
        Employee employeeById = this.getEmployeeById(id);
        vacation.setEmployee(employeeById);
        vacationRepository.save(vacation);
        return this.getEmployeeById(id);
    }
}
