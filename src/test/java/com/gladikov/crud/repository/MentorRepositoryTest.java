package com.gladikov.crud.repository;

import com.gladikov.crud.model.Mentor;
import com.gladikov.crud.util.ResourceProvider;

class MentorRepositoryTest extends CrudRepositoryTest<Mentor>{

	@Override
	Mentor getEntity() {
		return Mentor.builder()
				.firstName("TestNameOne")
				.lastName("TestSurnameOne")
				.salary(500.0)
				.contractNumber("C-1")
				.build();
	}

	@Override
	CrudRepository<Mentor> getRepository() {
		return new MentorRepository(new ResourceProvider("test"));
	}
	
	@Override
	int getNumberOfElements() {
		return 3;
	}

}
