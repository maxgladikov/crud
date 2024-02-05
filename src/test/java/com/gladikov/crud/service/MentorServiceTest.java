package com.gladikov.crud.service;

import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import org.mockito.Mockito;

import com.gladikov.crud.dto.MentorDto;
import com.gladikov.crud.exception.DaoException;
import com.gladikov.crud.mapper.Mapper;
import com.gladikov.crud.model.Mentor;
import com.gladikov.crud.repository.MentorRepository;

class MentorServiceTest extends CrudServiceTest<MentorDto, Mentor>{
		
	@Override
	MentorDto givenDto() {
		return Mapper.convertMentorToDto(givenEntity());
	}
	
	@Override
	Mentor givenEntity() {
		return Mentor.builder()
						.firstName("TestName")
						.lastName("TestSurname")
						.salary(500.0)
						.contractNumber("C-1")
						.build();
	}

	@Override
	CrudService<MentorDto> getService() throws DaoException {
		repo=Mockito.mock(MentorRepository.class);
		when(repo.getAll()).thenReturn(Collections.singletonList(givenEntity()));
		when(repo.getByContractNumber(givenEntity().getContractNumber())).thenReturn(Optional.ofNullable(givenEntity()));
		return new MentorService((MentorRepository)repo);
	}

}
