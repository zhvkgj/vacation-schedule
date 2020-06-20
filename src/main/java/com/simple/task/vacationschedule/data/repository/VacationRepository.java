package com.simple.task.vacationschedule.data.repository;

import com.simple.task.vacationschedule.model.vacation.Vacation;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface VacationRepository extends CrudRepository<Vacation, Long> {
    List<Vacation> findAllByEmployeePersNumberAndStartDateGreaterThanEqualAndEndDateLessThanEqual(String persNumber, Date startDate, Date endDate);
    List<Vacation> findAllByEmployeePersNumber(String persNumber);
    List<Vacation> findAllByStartDateGreaterThanEqualAndEndDateLessThanEqual(Date startDate, Date endDate);
}
