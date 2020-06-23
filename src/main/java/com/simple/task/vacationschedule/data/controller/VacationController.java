package com.simple.task.vacationschedule.data.controller;

import com.simple.task.vacationschedule.data.repository.VacationRepository;
import com.simple.task.vacationschedule.data.service.VacationService;
import com.simple.task.vacationschedule.model.SearchRequest;
import com.simple.task.vacationschedule.model.vacation.Vacation;
import com.simple.task.vacationschedule.model.vacation.dto.GetVacationDto;
import com.simple.task.vacationschedule.util.MultiFormatReportProvider;
import net.sf.jasperreports.engine.JRException;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@CrossOrigin
@RestController
@RequestMapping(value = "/vacation")
public class VacationController {
    private final VacationService vacationService;
    private final ModelMapper modelMapper;
    private final VacationRepository vacationRepository;

    public VacationController(VacationService vacationService, ModelMapper modelMapper,
                              VacationRepository vacationRepository) {
        this.vacationService = vacationService;
        this.modelMapper = modelMapper;
        this.vacationRepository = vacationRepository;
    }

    @PostMapping("/search")
    public ResponseEntity<List<GetVacationDto>> getMatchedVacations(@RequestBody SearchRequest searchRequest) {
        List<Vacation> vacations = vacationService.getMatchedVacations(searchRequest);
        return ResponseEntity.ok(
                vacations
                .stream()
                .map(vacation -> modelMapper.map(vacation, GetVacationDto.class))
                .collect(Collectors.toList())
        );
    }

    @GetMapping(value = "/report/{format}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] downloadReport(@PathVariable(name = "format") String reportFormat)
            throws JRException {
        List<Vacation> vacations = StreamSupport
                .stream(vacationRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        return MultiFormatReportProvider.exportReport(vacations, reportFormat);
    }
}
