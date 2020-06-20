package com.simple.task.vacationschedule.data.controller;

import com.simple.task.vacationschedule.data.service.EmployeeService;
import com.simple.task.vacationschedule.model.UserInfo;
import com.simple.task.vacationschedule.model.employee.Employee;
import com.simple.task.vacationschedule.security.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceNotFoundException;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final EmployeeService employeeService;

    public AuthenticationController(AuthenticationManager authenticationManager,
                                    JwtTokenProvider jwtTokenProvider,
                                    EmployeeService employeeService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.employeeService = employeeService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Map<Object, Object>> login(@RequestBody UserInfo userInfo) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userInfo.getLogin(), userInfo.getPassword()));
            Employee user = employeeService.getEmployeeByLogin(userInfo.getLogin());

            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + userInfo.getLogin() + " not found");
            }

            String token = jwtTokenProvider.createToken(userInfo.getLogin());

            Map<Object, Object> response = new HashMap<>();
            response.put("username", userInfo.getLogin());
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (InstanceNotFoundException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
