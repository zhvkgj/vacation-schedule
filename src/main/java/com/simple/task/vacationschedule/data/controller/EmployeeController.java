package com.simple.task.vacationschedule.data.controller;

import com.simple.task.vacationschedule.data.service.EmployeeService;
import com.simple.task.vacationschedule.model.employee.Employee;
import com.simple.task.vacationschedule.model.employee.Position;
import com.simple.task.vacationschedule.model.employee.dto.GetEmployeeDto;
import com.simple.task.vacationschedule.model.vacation.Vacation;
import com.simple.task.vacationschedule.model.employee.dto.UpsertEmployeeDto;
import com.simple.task.vacationschedule.model.vacation.dto.GetVacationDto;
import com.simple.task.vacationschedule.model.vacation.dto.UpsertVacationDto;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.management.InstanceNotFoundException;
import javax.persistence.EntityExistsException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/employee")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final ModelMapper modelMapper;

    public EmployeeController(EmployeeService employeeService, ModelMapper modelMapper) {
        this.employeeService = employeeService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetEmployeeDto> getEmployeeById(@PathVariable(name = "id") long employeeId) {
        try {
            Employee employee = employeeService.getEmployeeById(employeeId);
            return ResponseEntity.ok(modelMapper.map(employee, GetEmployeeDto.class));
        } catch (InstanceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<GetEmployeeDto>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(
                employees
                .stream()
                .map(e -> modelMapper.map(e, GetEmployeeDto.class))
                .collect(Collectors.toList())
        );
    }

    @GetMapping("/positions")
    public ResponseEntity<Map<Position, String>> getAllCompanyPositions() {
        Map<Position, String> positionWithRussianTranslate = new HashMap<>();

        for (Position pos : Position.values()) {
            positionWithRussianTranslate.put(pos, pos.getRussianTranslate());
        }

        return ResponseEntity.ok(positionWithRussianTranslate);
    }

    @PreAuthorize("@CheckPosition.isManager()")
    @PostMapping("/")
    public ResponseEntity<GetEmployeeDto> createEmployee(@RequestBody UpsertEmployeeDto upsertEmployeeDTO) {
        try {
            Employee employee = employeeService.createEmployee(modelMapper.map(upsertEmployeeDTO, Employee.class));
            return ResponseEntity.ok(modelMapper.map(employee, GetEmployeeDto.class));
        } catch (EntityExistsException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PreAuthorize("@CheckPosition.isManager()")
    @PutMapping("/{id}")
    public ResponseEntity<GetEmployeeDto> updateEmployee(@PathVariable(name = "id") long id,
                                                         @RequestBody UpsertEmployeeDto upsertEmployeeDTO) {
        try {
            Employee employee = employeeService
                    .updateEmployeeById(id, modelMapper.map(upsertEmployeeDTO, Employee.class));

            return ResponseEntity.ok(modelMapper.map(employee, GetEmployeeDto.class));
        } catch (InstanceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    @PreAuthorize("@CheckPosition.isManager()")
    @DeleteMapping("/{id}")
    public ResponseEntity<GetEmployeeDto> deleteEmployee(@PathVariable(name = "id") long employeeId) {
        try {
            Employee employee = employeeService.deleteEmployeeById(employeeId);
            return ResponseEntity.ok(modelMapper.map(employee, GetEmployeeDto.class));
        } catch (InstanceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    @PreAuthorize("@CheckPosition.isManager()")
    @PostMapping("/add/vacation/{id}")
    public ResponseEntity<GetVacationDto> addVacationToEmployee(@PathVariable(name = "id") long employeeId,
                                                                @RequestBody UpsertVacationDto upsertVacationDTO) {
        try {
            Vacation vacation = modelMapper.map(upsertVacationDTO, Vacation.class);
            employeeService.addVacationToEmployee(employeeId, vacation);
            return ResponseEntity.ok(modelMapper.map(vacation, GetVacationDto.class));
        } catch (InstanceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    @PreAuthorize("@CheckPosition.isManager()")
    @GetMapping("/check/login")
    public ResponseEntity<Boolean> isLoginAlreadyExist(@RequestParam(name = "login") String login) {
        return ResponseEntity.ok(employeeService.isLoginAlreadyExist(login));
    }

    @PreAuthorize("@CheckPosition.isManager()")
    @GetMapping("/check/personal/number")
    public ResponseEntity<Boolean> isPersNumberAlreadyExist(@RequestParam(name = "persNumber") String persNumber) {
        return ResponseEntity.ok(employeeService.isPersNumberAlreadyExist(persNumber));
    }
}
