package com.gladikov.crud.mapper;

import com.gladikov.crud.dto.MentorDto;
import com.gladikov.crud.dto.StudentDto;
import com.gladikov.crud.model.Mentor;
import com.gladikov.crud.model.Student;

public class Mapper {
	
	private Mapper() {}

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
	
	public static StudentDto convertStudentToDto(Student s) {
		return new StudentDto(s.getFirstName(), s.getLastName(), s.getAge(),s.getSex(),s.getAcademicPerformance(), s.getContractNumber(),s.getMentorId());
	}
	
	public static Student convertDtoToStudent(StudentDto dto) {
		return Student.builder()
				.firstName(dto.firstName())
				.lastName(dto.lastName())
				.age(dto.age())
				.sex(dto.sex())
				.academicPerformance(dto.academicPerformance())
				.contractNumber(dto.contractNumber())
				.mentorId(dto.mentorId())
				.build();
	}

}
