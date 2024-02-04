package com.gladikov.crud.repository;

import com.gladikov.crud.model.Mentor;
import com.gladikov.crud.util.ResourceProvider;

class MentorRepositoryTest extends CrudRepositoryTest<Mentor>{


	@Override
	Mentor getFirstEntity() {
		return Mentor.builder()
				.firstName("TestNameOne")
				.lastName("TestSurnameOne")
				.salary(500.0)
				.contractNumber("C-1")
				.build();
	}
	
	@Override
	Mentor getSecondEntity() {
		return Mentor.builder()
						.firstName("TestNameTwo")
						.lastName("TestSurnameTwo")
						.salary(700.0)
						.contractNumber("C-2")
						.build();
	}

	@Override
	CrudRepository<Mentor> getRepository() {
		return new MentorRepository(new ResourceProvider("test").getDatasource());
	}

}
