package com.gladikov.crud.service;

import java.util.List;

import com.gladikov.crud.dto.StudentDto;
import com.gladikov.crud.exception.DaoException;
import com.gladikov.crud.mapper.Mapper;
import com.gladikov.crud.model.Student;
import com.gladikov.crud.repository.CrudRepository;
import com.gladikov.crud.repository.StudentRepository;
import com.gladikov.crud.util.ResourceProvider;

// Сервисы хорошие
public class StudentService implements CrudService<StudentDto>{
	private final CrudRepository<Student> repository;
	
	public StudentService(ResourceProvider rp) {
		repository=new StudentRepository(rp);
	}
	
	public StudentService(StudentRepository repository) {
		this.repository=repository;
	}
	
	@Override
	public StudentDto get(String contractNumber)  throws DaoException {
		Student student= repository.getByContractNumber(contractNumber)
									.orElseThrow(() -> new DaoException("Requested entity does not exist"));
		return Mapper.convertStudentToDto(student);
	}

	@Override
	public void save(StudentDto dto) throws DaoException {
		repository.add(Mapper.convertDtoToStudent(dto));
	}

	@Override
	public void update(StudentDto dto) throws DaoException {
		repository.update(Mapper.convertDtoToStudent(dto));
	}

	@Override
	public void delete(String contractNumber) throws DaoException {
		repository.delete(contractNumber);
	}

	@Override
	public List<StudentDto> get() throws DaoException {
		return repository.getAll().stream().map(Mapper::convertStudentToDto).toList();
	}
	
	public List<StudentDto> getAllByMentor(int mentor_id) throws DaoException {
		StudentRepository repo= (StudentRepository)repository;
		 return repo.getByMentor(mentor_id).stream().map(Mapper::convertStudentToDto).toList();
	}

}
