package com.simple.task.vacationschedule.security;

import com.simple.task.vacationschedule.data.repository.EmployeeRepository;
import com.simple.task.vacationschedule.model.employee.Employee;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final EmployeeRepository employeeRepository;

    public UserDetailServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<Employee> byLogin = employeeRepository.findByLogin(login);

        if (byLogin.isPresent()) {
            Employee employee = byLogin.get();
            return org.springframework.security.core.userdetails.User
                    .withUsername(login)
                    .password(employee.getPassword())
                    .authorities(employee.getPosition())
                    .accountExpired(false)
                    .accountLocked(false)
                    .credentialsExpired(false)
                    .disabled(false)
                    .build();
        } else {
            throw new UsernameNotFoundException("User with login " + login + " was not found");
        }
    }
}
