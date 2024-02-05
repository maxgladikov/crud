package com.gladikov.crud.dto;


public record MentorDto(String firstName, String lastName, double salary, String contractNumber) implements DTO {
	
}
