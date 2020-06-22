package com.simple.task.vacationschedule.model.vacation.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ReportVacationDto {
    private String fullName;
    private String startDate;
    private String endDate;
}
