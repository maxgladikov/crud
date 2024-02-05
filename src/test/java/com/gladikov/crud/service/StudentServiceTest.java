package com.gladikov.crud.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.gladikov.crud.dto.StudentDto;
import com.gladikov.crud.exception.DaoException;
import com.gladikov.crud.mapper.Mapper;
import com.gladikov.crud.model.Sex;
import com.gladikov.crud.model.Student;
import com.gladikov.crud.repository.StudentRepository;

class StudentServiceTest extends CrudServiceTest<StudentDto, Student>{

	@Test
	void givenService_whenProceedGetByMentor_thenVerify() throws DaoException {
		// given
		StudentRepository studentRepository = (StudentRepository) repo;
		when(studentRepository.getByMentor(1)).thenReturn(Collections.singletonList(givenEntity()));
		StudentService studentService = (StudentService) service;
		StudentDto dto=givenDto();
		List<StudentDto> expected=Collections.singletonList(dto);
		// when
		List<StudentDto> result=studentService.getAllByMentor(1);
		// then
		assertEquals(expected,result);
	}
	
	@Override
	StudentDto givenDto() {
		return Mapper.convertStudentToDto(givenEntity());
	}
	
	@Override
	Student givenEntity() {
		return Student.builder()
				.firstName("TestName")
				.lastName("TestSurname")
				.academicPerformance(5.0)
				.age(25)
				.sex(Sex.values()[0])
				.contractNumber("C-001")
				.mentorId(1)
				.build();
	}

	@Override
	CrudService<StudentDto> getService() throws DaoException {
		repo=Mockito.mock(StudentRepository.class);
	
		when(repo.getAll()).thenReturn(Collections.singletonList(givenEntity()));
		when(repo.getByContractNumber(givenEntity().getContractNumber())).thenReturn(Optional.ofNullable(givenEntity()));
		return new StudentService((StudentRepository)repo);
	}


}
