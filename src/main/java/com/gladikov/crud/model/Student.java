package com.gladikov.crud.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Student implements Entity{
	private String firstName;
	private String lastName;
	private int age;
	private Sex sex;
	private double academicPerformance;
	private String contractNumber;
}
