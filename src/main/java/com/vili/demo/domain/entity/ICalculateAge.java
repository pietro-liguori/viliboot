package com.vili.demo.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.MappedSuperclass;

import java.util.Calendar;
import java.util.Date;

@MappedSuperclass
public interface ICalculateAge {

    Date getDateOfBirth();

    @JsonIgnore
    default Integer getAge() {
        Calendar dateOfBirthCalendar = Calendar.getInstance();
        dateOfBirthCalendar.setTime(getDateOfBirth());
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int birthYear = dateOfBirthCalendar.get(Calendar.YEAR);
        int birthMonth = dateOfBirthCalendar.get(Calendar.MONTH);
        int birthDay = dateOfBirthCalendar.get(Calendar.DAY_OF_MONTH);
        Calendar comp = Calendar.getInstance();
        comp.set(currentYear, birthMonth, birthDay);

        return comp.after(new Date()) ?
                currentYear - birthYear - 1 :
                currentYear - birthYear;
    }
}
