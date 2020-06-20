package com.simple.task.vacationschedule.data.controller;

import com.simple.task.vacationschedule.data.service.VacationService;
import com.simple.task.vacationschedule.model.SearchRequest;
import com.simple.task.vacationschedule.model.vacation.Vacation;
import com.simple.task.vacationschedule.model.vacation.dto.GetVacationDto;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/vacation")
public class VacationController {
    private final VacationService vacationService;
    private final ModelMapper modelMapper;

    public VacationController(VacationService vacationService, ModelMapper modelMapper) {
        this.vacationService = vacationService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/search")
    public ResponseEntity<List<GetVacationDto>> search(@RequestBody SearchRequest searchRequest) {

        List<Vacation> vacationList = vacationService.getListVacations(searchRequest);
        return ResponseEntity.ok(
                vacationList
                .stream()
                .map(r -> modelMapper.map(r, GetVacationDto.class))
                .collect(Collectors.toList())
        );
    }
}
