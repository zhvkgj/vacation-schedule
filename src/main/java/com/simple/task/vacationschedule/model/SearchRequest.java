package com.simple.task.vacationschedule.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class SearchRequest {
    private String persNumber;
    private Date startDate;
    private Date endDate;
}
