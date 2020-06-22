package com.simple.task.vacationschedule.model.employee;

import org.springframework.security.core.GrantedAuthority;

public enum Position implements GrantedAuthority {
    MANAGER("Менеджер"),
    EMPLOYEE("Сотрудник");

    private final String russianTranslate;

    Position(String value) {
        this.russianTranslate = value;
    }

    public String getRussianTranslate() {
        return russianTranslate;
    }

    @Override
    public String getAuthority() {
        return this.name();
    }
}
