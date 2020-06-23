package com.simple.task.vacationschedule.model.employee.dto;

import com.simple.task.vacationschedule.model.employee.Position;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class UpsertEmployeeDto {
    private Date birthday;
    private String fullName;
    private String persNumber;
    private Position position;
    private String login;
    private String password;
}
