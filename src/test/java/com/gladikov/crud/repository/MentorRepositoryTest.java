package com.gladikov.crud.repository;

import com.gladikov.crud.model.Mentor;
import com.gladikov.crud.util.ResourceProvider;

class MentorRepositoryTest extends CrudRepositoryTest<Mentor>{


	@Override
	Mentor getEntity() {
		return Mentor.builder()
						.firstName("TestName")
						.lastName("TestSurname")
						.salary(500.0)
						.contractNumber("C-1")
						.build();
	}

	@Override
	int getNumberOfEntities() {
		return 1;
	}

	@Override
	CrudRepository<Mentor> getRepository() {
		return new MentorRepository(new ResourceProvider("test").getDatasource());
	}

}
