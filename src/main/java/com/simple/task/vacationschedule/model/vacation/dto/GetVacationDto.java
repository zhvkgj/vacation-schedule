package com.simple.task.vacationschedule.model.vacation.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.simple.task.vacationschedule.model.employee.dto.GetEmployeeDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Data
public class GetVacationDto {
    private long id;
    private Date startDate;
    private Date endDate;

    // to avoid infinity cyclic loading
    @JsonIgnoreProperties("vacationsList")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private GetEmployeeDto employee;
}
