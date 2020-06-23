package com.simple.task.vacationschedule.data.service;

import com.simple.task.vacationschedule.data.repository.EmployeeRepository;
import com.simple.task.vacationschedule.data.repository.VacationRepository;
import com.simple.task.vacationschedule.model.employee.Employee;
import com.simple.task.vacationschedule.model.vacation.Vacation;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.InstanceNotFoundException;
import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final VacationRepository vacationRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeeService(EmployeeRepository employeeRepository, 
                           VacationRepository vacationRepository, 
                           PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.vacationRepository = vacationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Employee createEmployee(Employee employee) throws EntityExistsException {
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        Optional<Employee> employee1 = employeeRepository
                .findByLoginAndPersNumber(employee.getLogin(), employee.getPersNumber());

        if (employee1.isPresent()) {
            throw new EntityExistsException();
        }

        return employeeRepository.save(employee);
    }

    public Employee updateEmployeeById(long employeeId, Employee newEmployee) throws InstanceNotFoundException {
        Optional<Employee> oldEmployee = employeeRepository.findById(employeeId);
        oldEmployee.orElseThrow(InstanceNotFoundException::new);
        newEmployee.setId(employeeId);

        if (newEmployee.getPassword() == null || newEmployee.getPassword().isEmpty()) {
            newEmployee.setPassword(oldEmployee.get().getPassword());
        } else {
            newEmployee.setPassword(passwordEncoder.encode(newEmployee.getPassword()));
        }

        return employeeRepository.save(newEmployee);
    }

    public Employee deleteEmployeeById(long id) throws InstanceNotFoundException {
        Optional<Employee> employee = employeeRepository.findById(id);
        employee.orElseThrow(InstanceNotFoundException::new);
        employeeRepository.deleteById(id);

        return employee.get();
    }

    public Employee getEmployeeById(long employeeId) throws InstanceNotFoundException {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        employee.orElseThrow(InstanceNotFoundException::new);

        return employee.get();
    }

    public Employee getEmployeeByLogin(String login) throws InstanceNotFoundException {
        Optional<Employee> employee = employeeRepository.findByLogin(login);
        employee.orElseThrow(InstanceNotFoundException::new);

        return employee.get();
    }

    public List<Employee> getAllEmployees() {
        return StreamSupport
                .stream(employeeRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Employee addVacationToEmployee(long employeeId, Vacation vacation) throws InstanceNotFoundException{
        Employee employeeById = this.getEmployeeById(employeeId);
        vacation.setEmployee(employeeById);
        vacationRepository.save(vacation);
        return this.getEmployeeById(employeeId);
    }

    public boolean isLoginAlreadyExist(String login) throws EntityExistsException {
        Optional<Employee> employee = employeeRepository.findByLogin(login);
        return employee.isPresent();
    }

    public boolean isPersNumberAlreadyExist(String persNumber) {
        Optional<Employee> employee = employeeRepository.findByPersNumber(persNumber);
        return employee.isPresent();
    }
}
