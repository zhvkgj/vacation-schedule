package com.simple.task.vacationschedule.model.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpsertEmployeeDTO {
    private Date birthday;
    private String fullName;
    private String persNumber;
    private String position;
    private String login;
    private String password;
}
