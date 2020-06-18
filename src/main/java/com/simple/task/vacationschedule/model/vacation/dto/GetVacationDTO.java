package com.simple.task.vacationschedule.model.vacation.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.simple.task.vacationschedule.model.employee.dto.GetEmployeeDTO;
import lombok.Data;

import java.util.Date;

@Data
public class GetVacationDTO {
    private long id;
    private Date startDate;
    private Date endDate;

    // to avoid infinity cyclic loading
    @JsonIgnoreProperties("vacations")
    private GetEmployeeDTO employee;
}
