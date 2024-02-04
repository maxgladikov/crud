package com.gladikov.crud.service;

import java.util.List;
import java.util.NoSuchElementException;

import javax.sql.DataSource;

import com.gladikov.crud.model.Mentor;
import com.gladikov.crud.repository.CrudRepository;
import com.gladikov.crud.repository.MentorRepository;
import com.gladikov.crud.service.dto.MentorDto;
import com.gladikov.crud.util.Mapper;
public class MentorService implements CrudService<MentorDto>{
	private final CrudRepository<Mentor> repository;
	
	public MentorService(DataSource ds) {
		repository=new MentorRepository(ds);
	}
	
	public MentorService(MentorRepository repository) {
		this.repository=repository;
	}
	
	@Override
	public MentorDto get(MentorDto dto) {
		Mentor mentor= repository.getByContractNumber(dto.contractNumber())
									.orElseThrow(NoSuchElementException::new);
		return Mapper.convertMentorToDto(mentor);
	}

	@Override
	public void save(MentorDto dto) {
		repository.add(Mapper.convertDtoToMentor(dto));
	}

	@Override
	public void update(MentorDto dto) {
		repository.update(Mapper.convertDtoToMentor(dto));
	}

	@Override
	public void delete(MentorDto dto) {
		
	}

	@Override
	public List<MentorDto> get() {
		return repository.getAll().stream().map(Mapper::convertMentorToDto).toList();
	}

}
