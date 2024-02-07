package com.gladikov.crud.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.gladikov.crud.exception.DaoException;
import com.gladikov.crud.model.Sex;
import com.gladikov.crud.model.Student;
import com.gladikov.crud.util.ResourceProvider;

class StudentRepositoryTest extends CrudRepositoryTest<Student>{
	
	@Test
	void givenRepository_whenProceedGetByMentor_thenVerify() throws DaoException {
		// given
		StudentRepository studentRepo= (StudentRepository) repository;
		// when
		// then
		assertEquals(7,studentRepo.getByMentor(1).size());
		assertEquals(7,studentRepo.getByMentor(2).size());
	}
	
	@Override
	Student getEntity() {
		return Student.builder()
				.contractNumber("pc_015")
				.firstName("Ivana")
				.lastName("Ivanova")
				.academicPerformance(5.0)
				.age(33)
				.sex(Sex.values()[1])
				.mentorId(1)
				.build();
	}
	
	@Override
	CrudRepository<Student> getRepository() {
		return new StudentRepository(new ResourceProvider("test"));
	}
	
	@Override
	int getNumberOfElements() {
		return 15;
	}

}
