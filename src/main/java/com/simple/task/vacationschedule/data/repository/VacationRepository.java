package com.simple.task.vacationschedule.data.repository;

import com.simple.task.vacationschedule.model.vacation.Vacation;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface VacationRepository extends CrudRepository<Vacation, Long>, JpaSpecificationExecutor<Vacation> {

}
