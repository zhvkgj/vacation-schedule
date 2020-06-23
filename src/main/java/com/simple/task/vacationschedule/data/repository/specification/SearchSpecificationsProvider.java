package com.simple.task.vacationschedule.data.repository.specification;

import com.simple.task.vacationschedule.model.employee.Employee;
import com.simple.task.vacationschedule.model.vacation.Vacation;
import com.simple.task.vacationschedule.model.vacation.Vacation_;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class SearchSpecificationsProvider {
    public static Specification<Vacation> persNumberEquals(Employee employeeWithPersNumber) {
        return (Specification<Vacation>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Vacation_.employee), employeeWithPersNumber);
    }

    public static Specification<Vacation> isStartDateGreaterThanEqual(Date startDate) {
        return (Specification<Vacation>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get(Vacation_.startDate), startDate);
    }

    public static Specification<Vacation> isEndDateLessThanEqual(Date endDate) {
        return (Specification<Vacation>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get(Vacation_.endDate), endDate);
    }

}
