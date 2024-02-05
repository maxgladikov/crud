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
	private long mentorId;
	
	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		
		if (!(o instanceof Student))
			return false;
		Student obj=(Student)o;
		return contractNumber.equals(obj.getContractNumber());
	}
}
