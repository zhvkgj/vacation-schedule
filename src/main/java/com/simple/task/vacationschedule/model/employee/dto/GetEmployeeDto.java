package com.simple.task.vacationschedule.model.employee.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.simple.task.vacationschedule.model.employee.Employee;
import com.simple.task.vacationschedule.model.employee.Position;
import com.simple.task.vacationschedule.model.vacation.dto.GetVacationDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
public class GetEmployeeDto {
    private long id;
    private Date birthday;
    private String fullName;
    private String persNumber;
    private Position position;
    private String login;

    // to avoid infinity cyclic loading
    @JsonIgnoreProperties("employee")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<GetVacationDto> vacationsList;
}
