package com.gladikov.crud.service;

import java.util.List;

import javax.sql.DataSource;

import com.gladikov.crud.model.Mentor;
import com.gladikov.crud.repository.CrudRepository;
import com.gladikov.crud.repository.MentorRepository;
public class MentorService implements CrudService<Mentor>{
	private final CrudRepository<Mentor> repository;
	
	public MentorService(DataSource ds) {
		repository=new MentorRepository(ds);
	}
	
	@Override
	public Mentor get(String contractNumber) {
		return null;
	}

	@Override
	public void save(Mentor entity) {
		
	}

	@Override
	public void update() {
		
	}

	@Override
	public void delete(String contractNumber) {
		
	}

	@Override
	public List<Mentor> get() {
		return repository.getAll();
	}

}
