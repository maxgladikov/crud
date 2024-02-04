package com.gladikov.crud.util;

import com.gladikov.crud.model.Mentor;
import com.gladikov.crud.service.dto.MentorDto;

public class Mapper {

	public static MentorDto convertMentorToDto(Mentor m) {
		return new MentorDto(m.getFirstName(), m.getLastName(), m.getSalary(), m.getContractNumber());
	}

	public static Mentor convertDtoToMentor(MentorDto dto) {
		return Mentor.builder()
				.firstName(dto.firstName())
				.lastName(dto.lastName())
				.salary(dto.salary())
				.contractNumber(dto.contractNumber())
				.build();
	}

}
