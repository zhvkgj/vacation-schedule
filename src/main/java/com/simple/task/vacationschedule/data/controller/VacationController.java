package com.simple.task.vacationschedule.data.controller;

import com.simple.task.vacationschedule.data.service.VacationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/vacation")
public class VacationController {
    // TODO: implement
    private final VacationService vacationService;

    public VacationController(VacationService vacationService) {
        this.vacationService = vacationService;
    }
}
