package com.gladikov.crud.dto;

import com.gladikov.crud.model.Sex;

public record StudentDto(
        String firstName,
        String lastName,
        int age,
        Sex sex,
        double academicPerformance,
        String contractNumber,
        long mentorId
) implements DTO { }
