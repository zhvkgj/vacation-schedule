package com.simple.task.vacationschedule.model.employee.dto;

import com.simple.task.vacationschedule.model.employee.Employee;
import com.simple.task.vacationschedule.model.employee.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpsertEmployeeDto {
    private Date birthday;
    private String fullName;
    private String persNumber;
    private Position position;
    private String login;
    private String password;
}
