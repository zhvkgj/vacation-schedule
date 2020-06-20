package com.simple.task.vacationschedule.model.employee;

import org.springframework.security.core.GrantedAuthority;

public enum Position implements GrantedAuthority {
    MANAGER,
    EMPLOYEE;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
