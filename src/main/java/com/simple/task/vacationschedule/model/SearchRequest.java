package com.simple.task.vacationschedule.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequest {
    private SearchType searchType;

    private String persNumber;
    private Date startDate;
    private Date endDate;

    public enum SearchType {
        PERS_NUM,
        PERIOD,
        ALL,
        EMPTY
    }
}
