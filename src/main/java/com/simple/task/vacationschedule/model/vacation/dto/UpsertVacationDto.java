package com.simple.task.vacationschedule.model.vacation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpsertVacationDto {
    private Date startDate;
    private Date endDate;
}
