package com.simple.task.vacationschedule.data.controller;

import com.simple.task.vacationschedule.data.service.EmployeeService;
import com.simple.task.vacationschedule.model.employee.Employee;
import com.simple.task.vacationschedule.model.employee.dto.GetEmployeeDTO;
import com.simple.task.vacationschedule.model.vacation.Vacation;
import com.simple.task.vacationschedule.model.employee.dto.UpsertEmployeeDTO;
import com.simple.task.vacationschedule.model.vacation.dto.UpsertVacationDTO;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.management.InstanceNotFoundException;
import javax.persistence.EntityExistsException;

@RestController
@RequestMapping(value = "/employee")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final ModelMapper modelMapper;

    public EmployeeController(EmployeeService employeeService, ModelMapper modelMapper) {
        this.employeeService = employeeService;
        this.modelMapper = modelMapper;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public GetEmployeeDTO getEmployeeById(@PathVariable(name = "id") long id) {
        try {
            Employee employeeById = employeeService.getEmployeeById(id);
            return modelMapper.map(employeeById, GetEmployeeDTO.class);
        } catch (InstanceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public GetEmployeeDTO createEmployee(@RequestBody UpsertEmployeeDTO upsertEmployeeDTO) {
        try {
            Employee employee = employeeService.createEmployee(modelMapper.map(upsertEmployeeDTO, Employee.class));
            return modelMapper.map(employee, GetEmployeeDTO.class);
        } catch (EntityExistsException e) {
            // TODO: add appropriate http status
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public GetEmployeeDTO updateEmployee(@PathVariable(name = "id") long id,
                                         @RequestBody UpsertEmployeeDTO upsertEmployeeDTO) {
        try {
            Employee employee = employeeService.updateEmployeeById(id, modelMapper.map(upsertEmployeeDTO, Employee.class));
            return modelMapper.map(employee, GetEmployeeDTO.class);
        } catch (InstanceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public GetEmployeeDTO deleteEmployee(@PathVariable(name = "id") long id) {
        try {
            Employee employeeById = employeeService.deleteEmployeeById(id);
            return modelMapper.map(employeeById, GetEmployeeDTO.class);
        } catch (InstanceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    @RequestMapping(value = "/add/vacation/{id}", method = RequestMethod.POST)
    public GetEmployeeDTO addVacationToEmployee(@PathVariable(name = "id") long id,
                                                @RequestBody UpsertVacationDTO upsertVacationDTO) {
        try {
            Employee employee = employeeService.addVacationToEmployee(id, modelMapper.map(upsertVacationDTO, Vacation.class));
            return modelMapper.map(employee, GetEmployeeDTO.class);
        } catch (InstanceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }
}
