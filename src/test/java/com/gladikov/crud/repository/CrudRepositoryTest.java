package com.gladikov.crud.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gladikov.crud.exception.DaoException;
import com.gladikov.crud.model.Entity;

abstract class CrudRepositoryTest<T extends Entity> {
	protected CrudRepository<T> repository;
	private T givenEntity;
	
	@BeforeEach
	void setUp() throws DaoException{
		repository=getRepository();
		givenEntity=getEntity();
	}
	
	@Test
	void givenEntity_whenProceedAdd_thenVerify() throws DaoException {
		// given
		String contractNum=givenEntity.getContractNumber();
		// when
		repository.add(givenEntity);
		Optional<T> opt=repository.getByContractNumber(contractNum);
		T result=opt.get();
		// then
		assertEquals(givenEntity,result);
	}
	
	@Test
	void givenRepository_whenProceedGetAll_thenVerify() throws DaoException {
		// given
		repository.add(givenEntity);
		int expected=getNumberOfElements();
		// when
		List<T> result=repository.getAll();
		// then
		assertEquals(expected,result.size());
	}
	
	@Test
	void givenRepository_whenProceedDelete_thenVerify() throws DaoException {
		// given
		repository.add(givenEntity);
		// when
		repository.delete(givenEntity.getContractNumber());
		// then
		assertFalse(repository.getAll().contains(givenEntity));
	}
	
	@Test
	void givenRepository_whenProceedUpdate_thenVerify() throws DaoException {
		// given
		repository.add(givenEntity);
		givenEntity.setFirstName("Changed");
		// when
		repository.update(givenEntity);
		// then
		assertEquals(givenEntity ,repository.getByContractNumber(givenEntity.getContractNumber()).get());
	}
	
	@AfterEach
	void tearDown() throws DaoException{
		repository.delete(givenEntity.getContractNumber());
	}
	
	
	
	
	abstract T getEntity();
	abstract int getNumberOfElements();
	abstract CrudRepository<T> getRepository();
		
}
