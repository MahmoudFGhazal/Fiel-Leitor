package com.mahas.command.pre.rules.logs;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

@Component
public class BirthdayValidator {
    public String verifyBirthdayDate(LocalDate birthday) {
        if (birthday == null) {
            return "Data de nascimento não pode ser nula";
        }

        if (!birthday.isBefore(LocalDate.now())) {
            return "Data de nascimento deve ser anterior à data atual";
        }

        return null;
    }
}
