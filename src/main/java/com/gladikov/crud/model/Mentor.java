package com.gladikov.crud.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Mentor implements Entity{
	private String firstName;
	private String lastName;
	private double salary;
	private String contractNumber;
	
	@Override
	public boolean equals(Object o) {
	    if (o == this)
	        return true;
	    if (!(o instanceof Mentor))
	        return false;
	    Mentor other = (Mentor)o;
	    return this.contractNumber.equals(other.contractNumber);
	}
}
