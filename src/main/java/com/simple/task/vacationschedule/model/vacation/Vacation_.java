package com.simple.task.vacationschedule.model.vacation;

import com.simple.task.vacationschedule.model.employee.Employee;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@StaticMetamodel(Vacation.class)
public class Vacation_ {

    public static volatile SingularAttribute<Vacation, Long> id;

    public static volatile SingularAttribute<Vacation, Date> startDate;

    public static volatile SingularAttribute<Vacation, Date> endDate;

    public static volatile SingularAttribute<Vacation, Employee> employee;
}
