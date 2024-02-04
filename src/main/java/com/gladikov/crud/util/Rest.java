package com.gladikov.crud.util;

import com.gladikov.crud.service.dto.MentorDto;

public class Rest {

	public static void main(String[] args) {
		System.out.println(new MentorDto("Name","Surname",500.0,"C-1").json());
	}

}
