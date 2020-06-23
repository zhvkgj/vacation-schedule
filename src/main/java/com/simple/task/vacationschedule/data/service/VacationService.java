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

import static com.simple.task.vacationschedule.data.repository.specification.SearchSpecificationsProvider.*;

@Service
public class VacationService {
    private final VacationRepository vacationRepository;
    private final EmployeeRepository employeeRepository;

    public VacationService(VacationRepository vacationRepository,
                           EmployeeRepository employeeRepository) {
        this.vacationRepository = vacationRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<Vacation> getMatchedVacations(SearchRequest searchRequest) {
        Specification<Vacation> searchSpec = null;

        if (searchRequest.getPersNumber() != null) {
            Optional<Employee> employeeWithPersNumber = employeeRepository
                    .findByPersNumber(searchRequest.getPersNumber());
            if (employeeWithPersNumber.isPresent()) {
                searchSpec = persNumberEquals(employeeWithPersNumber.get());
            } else {
                return Collections.emptyList();
            }
        }

        if (searchRequest.getStartDate() != null) {
            if (searchSpec != null) {
                searchSpec = searchSpec.and(isStartDateGreaterThanEqual(searchRequest.getStartDate()));
            } else {
                searchSpec = isStartDateGreaterThanEqual(searchRequest.getStartDate());
            }
        }

        if (searchRequest.getEndDate() != null) {
            if (searchSpec != null) {
                searchSpec = searchSpec.and(isEndDateLessThanEqual(searchRequest.getEndDate()));
            } else {
                searchSpec = isEndDateLessThanEqual(searchRequest.getEndDate());
            }
        }

        return searchSpec != null ? vacationRepository.findAll(searchSpec) :
                StreamSupport.stream(vacationRepository.findAll().spliterator(), false)
                        .collect(Collectors.toList());
    }
}
