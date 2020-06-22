package com.simple.task.vacationschedule.model.employee;

import com.simple.task.vacationschedule.model.vacation.Vacation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date birthday;

    private String fullName;
    private String persNumber;

    @Column(name = "position")
    @Enumerated(EnumType.STRING)
    private Position position;

    private String login;
    private String password;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "employee")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Vacation> vacationsList;
}
