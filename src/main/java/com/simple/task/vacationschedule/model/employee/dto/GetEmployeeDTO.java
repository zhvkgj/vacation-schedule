package com.simple.task.vacationschedule.model.employee.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.simple.task.vacationschedule.model.vacation.dto.GetVacationDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class GetEmployeeDTO {
    private long id;
    private Date birthday;
    private String fullName;
    private String persNumber;
    private String position;
    private String login;

    // to avoid infinity cyclic loading
    @JsonIgnoreProperties("employee")
    private List<GetVacationDTO> vacations;
}
