package com.gladikov.crud.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gladikov.crud.model.Entity;

abstract class CrudRepositoryTest<T extends Entity> {
	private final CrudRepository<T> repository=getRepository();
	private final T testEntity = getEntity();
	
	@Test
	void givenEntity_whenProceedAdd_thenVerify() {
		// given
		String contractNum=testEntity.getContractNumber();
		// when
		repository.add(testEntity);
		Optional<T> opt=repository.getByContractNumber(contractNum);
		T result=opt.get();
		// then
		assertEquals(testEntity,result);
	}
	
	@Test
	void givenRepository_whenProceedGetAll_thenVerify() {
		// given
		repository.add(testEntity);
		// when
		int expected=1;
		List<T> result=repository.getAll();
		// then
		assertEquals(expected,result.size());
	}
	
	@AfterEach
	void tearDown(){
		repository.delete(testEntity);
	}
	@BeforeEach
	void setUp(){
		repository.delete(testEntity);
	}
	
	
	
	abstract T getEntity();
	abstract int getNumberOfEntities();
	abstract CrudRepository<T> getRepository();
		
}
