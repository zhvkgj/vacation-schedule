package com.simple.task.vacationschedule.data.repository;

import com.simple.task.vacationschedule.model.vacation.Vacation;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.Optional;

public interface VacationRepository extends CrudRepository<Vacation, Long> {
    Optional<Vacation> findByEmployeePersNumberAndStartDateBetween(String persNumber, Date startDate, Date endDate);
}
