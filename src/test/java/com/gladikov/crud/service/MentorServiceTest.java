package com.gladikov.crud.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.gladikov.crud.model.Mentor;
import com.gladikov.crud.repository.MentorRepository;
import com.gladikov.crud.service.dto.MentorDto;

class MentorServiceTest extends CrudServiceTest<MentorDto>{
		private MentorRepository repo;
	
	@Test
	void givenService_whenProceedSave_thenVerify() {
		// given
		doNothing().when(repo).add(givenEntity());
		
		int expectedRepoAddCalls=1;
		//when
		service.save(givenDto());
		//then
		verify(repo,times(expectedRepoAddCalls)).add(givenEntity());
	}
	
	@Test
	void givenService_whenProceedUpdate_thenVerify() {
		// given
		doNothing().when(repo).update(givenEntity());
		int expectedRepoUpdateCalls=1;
		//when
		service.update(givenDto());
		//then
		verify(repo,times(expectedRepoUpdateCalls)).update(givenEntity());
	}
	
	@Override
	MentorDto givenDto() {
		return new MentorDto("TestName", "TestSurname", 500.0, "C-1");
	}
	
	private Mentor givenEntity() {
		return Mentor.builder()
						.firstName("TestName")
						.lastName("TestSurname")
						.salary(500.0)
						.contractNumber("C-1")
						.build();
	}

	@Override
	CrudService<MentorDto> getService() {
		repo=Mockito.mock(MentorRepository.class);
		when(repo.getAll()).thenReturn(Collections.singletonList(givenEntity()));
		when(repo.getByContractNumber(givenEntity().getContractNumber())).thenReturn(Optional.ofNullable(givenEntity()));
		return new MentorService(repo);
	}

}
