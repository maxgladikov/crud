package com.gladikov.crud.service;

import java.util.List;

import com.gladikov.crud.model.Mentor;
import com.gladikov.crud.repository.CrudRepository;
import com.gladikov.crud.repository.MentorRepository;

public class MentorService implements CrudService<Mentor>{
	private CrudRepository<Mentor> repository=new MentorRepository();
	@Override
	public Mentor get(String contractNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Mentor entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String contractNumber) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Mentor> get() {
		return repository.read();
	}

}
