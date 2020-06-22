package com.simple.task.vacationschedule.data.service;

import com.simple.task.vacationschedule.data.repository.EmployeeRepository;
import com.simple.task.vacationschedule.data.repository.VacationRepository;
import com.simple.task.vacationschedule.model.SearchRequest;
import com.simple.task.vacationschedule.model.employee.Employee;
import com.simple.task.vacationschedule.model.vacation.Vacation;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.simple.task.vacationschedule.data.repository.CustomSpecifications.*;

@Service
public class VacationService {
    private final VacationRepository vacationRepository;
    private final EmployeeRepository employeeRepository;

    public VacationService(VacationRepository vacationRepository, EmployeeRepository employeeRepository) {
        this.vacationRepository = vacationRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<Vacation> getListVacations(SearchRequest searchRequest) {
        Specification<Vacation> spec = null;

        if (searchRequest.getPersNumber() != null) {
            Optional<Employee> employeeWithPersNumber =
                    employeeRepository.findByPersNumber(searchRequest.getPersNumber());
            if (employeeWithPersNumber.isPresent()) {
                spec = persNumberEquals(employeeWithPersNumber.get());
            } else {
                return Collections.emptyList();
            }
        }

        if (searchRequest.getStartDate() != null) {
            if (spec != null) {
                spec = spec.and(isStartDateGreaterThanEqual(searchRequest.getStartDate()));
            } else {
                spec = isStartDateGreaterThanEqual(searchRequest.getStartDate());
            }
        }

        if (searchRequest.getEndDate() != null) {
            if (spec != null) {
                spec = spec.and(isEndDateLessThanEqual(searchRequest.getEndDate()));
            } else {
                spec = isEndDateLessThanEqual(searchRequest.getEndDate());
            }
        }

        return spec != null ? vacationRepository.findAll(spec) :
                StreamSupport.stream(vacationRepository.findAll().spliterator(), false)
                        .collect(Collectors.toList());
    }
}
