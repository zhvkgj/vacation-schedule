package com.simple.task.vacationschedule.data.service;

import com.simple.task.vacationschedule.data.repository.VacationRepository;
import com.simple.task.vacationschedule.model.SearchRequest;
import com.simple.task.vacationschedule.model.vacation.Vacation;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class VacationService {
    private final VacationRepository vacationRepository;

    public VacationService(VacationRepository vacationRepository) {
        this.vacationRepository = vacationRepository;
    }

    public List<Vacation> getListVacations(SearchRequest searchRequest) {

        List<Vacation> result = Collections.emptyList();
        switch (searchRequest.getSearchType()) {
            case ALL:
                result = vacationRepository.findAllByEmployeePersNumberAndStartDateGreaterThanEqualAndEndDateLessThanEqual(searchRequest.getPersNumber(), searchRequest.getStartDate(), searchRequest.getEndDate());
                break;
            case PERIOD:
                result = vacationRepository.findAllByStartDateGreaterThanEqualAndEndDateLessThanEqual(searchRequest.getStartDate(), searchRequest.getEndDate());
                break;
            case PERS_NUM:
                result = vacationRepository.findAllByEmployeePersNumber(searchRequest.getPersNumber());
                break;
            case EMPTY:
                result = StreamSupport
                        .stream(vacationRepository.findAll().spliterator(), false)
                        .collect(Collectors.toList());
        }

        return result;
    }
}
