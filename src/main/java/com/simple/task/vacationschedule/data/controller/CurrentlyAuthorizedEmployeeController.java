package com.simple.task.vacationschedule.data.controller;

import com.simple.task.vacationschedule.data.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.management.InstanceNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/auth")
public class CurrentlyAuthorizedEmployeeController {
    private final EmployeeService employeeService;

    public CurrentlyAuthorizedEmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/current")
    public ResponseEntity<Map<String, Object>> getCurrentlyAuthorizedEmployeeProperties() {
        try {
            UserDetails principal = (UserDetails)
                    SecurityContextHolder
                            .getContext()
                            .getAuthentication()
                            .getPrincipal();

            List<String> allCurrentEmployeePositions = principal
                    .getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            String employeeUsername = principal.getUsername();
            String fullEmployeeName = employeeService.getEmployeeByLogin(employeeUsername).getFullName();

            Map<String, Object> currentlyAuthorizedEmployeeProperties = new HashMap<>();
            currentlyAuthorizedEmployeeProperties.put("fullName", fullEmployeeName);
            currentlyAuthorizedEmployeeProperties.put("login", employeeUsername);
            currentlyAuthorizedEmployeeProperties.put("roles", allCurrentEmployeePositions);

            return ResponseEntity.ok(currentlyAuthorizedEmployeeProperties);
        } catch (InstanceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }
}
