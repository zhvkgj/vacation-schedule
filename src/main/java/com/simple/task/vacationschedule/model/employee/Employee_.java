package com.simple.task.vacationschedule.model.employee;

import com.simple.task.vacationschedule.model.vacation.Vacation;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@StaticMetamodel(Employee.class)
public class Employee_ {

    public static volatile SingularAttribute<Employee, Long> id;

    public static volatile SingularAttribute<Employee, Date> birthday;

    public static volatile SingularAttribute<Employee, String> fullName;

    public static volatile SingularAttribute<Employee, String> persNumber;

    public static volatile SingularAttribute<Employee, Position> position;

    public static volatile SingularAttribute<Employee, String> login;

    public static volatile SingularAttribute<Employee, String> password;

    public static volatile ListAttribute<Employee, Vacation> vacationsList;
}
