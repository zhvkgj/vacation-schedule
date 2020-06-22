package com.simple.task.vacationschedule.util;

import com.simple.task.vacationschedule.model.employee.Position;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("CheckPosition")
public class CheckPosition {
    public static boolean isManager() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .contains(Position.MANAGER);
    }
}
