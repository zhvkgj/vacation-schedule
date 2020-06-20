package com.simple.task.vacationschedule.security;

import com.simple.task.vacationschedule.data.repository.EmployeeRepository;
import com.simple.task.vacationschedule.model.employee.Employee;
import com.simple.task.vacationschedule.model.employee.Position;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDetailServiceImpl(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    private void initAdmin() {
        List<Employee> managers = employeeRepository.findAllByPosition(Position.MANAGER);

        if (managers.isEmpty()) {
            Employee manager = new Employee();
            manager.setLogin("admin");
            manager.setPassword(passwordEncoder.encode("admin"));
            manager.setPosition(Position.MANAGER);

            employeeRepository.save(manager);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<Employee> byLogin = employeeRepository.findByLogin(login);

        if (byLogin.isPresent()) {
            Employee user = byLogin.get();
            return org.springframework.security.core.userdetails.User
                    .withUsername(login)
                    .password(user.getPassword())
                    .authorities(user.getPosition())
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
