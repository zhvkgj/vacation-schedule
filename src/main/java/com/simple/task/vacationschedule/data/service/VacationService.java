package com.simple.task.vacationschedule.data.service;

import com.simple.task.vacationschedule.data.repository.VacationRepository;
import org.springframework.stereotype.Service;

@Service
public class VacationService {
    private final VacationRepository vacationRepository;

    public VacationService(VacationRepository vacationRepository) {
        this.vacationRepository = vacationRepository;
    }

}
