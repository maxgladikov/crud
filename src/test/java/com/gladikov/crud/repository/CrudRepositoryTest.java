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
	private final T testEntityOne = getFirstEntity();
	private final T testEntityTwo = getSecondEntity();
	
	@BeforeEach
	void setUp(){
		
	}
	
	@Test
	void givenEntity_whenProceedAdd_thenVerify() {
		// given
		String contractNum=testEntityOne.getContractNumber();
		// when
		repository.add(testEntityOne);
		Optional<T> opt=repository.getByContractNumber(contractNum);
		T result=opt.get();
		// then
		assertEquals(testEntityOne,result);
	}
	
	@Test
	void givenRepository_whenProceedGetAll_thenVerify() {
		// given
		repository.add(testEntityOne);
		repository.add(testEntityTwo);
		int expected=2;
		// when
		List<T> result=repository.getAll();
		// then
		assertAll(
					() -> assertEquals(expected,result.size()),
					() -> assertTrue(result.containsAll(List.of(testEntityOne,testEntityTwo)))
				);
	}
	
	@Test
	void givenRepository_whenProceedDelete_thenVerify() {
		// given
		repository.add(testEntityOne);
		repository.add(testEntityTwo);
		// when
		repository.delete(testEntityOne);
		// then
		assertFalse(repository.getAll().contains(testEntityOne));
	}
	
	@Test
	void givenRepository_whenProceedUpdate_thenVerify() {
		// given
		repository.add(testEntityOne);
		repository.add(testEntityTwo);
		testEntityOne.setFirstName("Changed");
		// when
		repository.update(testEntityOne);
		// then
		assertEquals(testEntityOne ,repository.getByContractNumber(testEntityOne.getContractNumber()).get());
	}
	
	@AfterEach
	void tearDown(){
		repository.delete(testEntityOne);
		repository.delete(testEntityTwo);
	}
	
	
	
	
	abstract T getFirstEntity();
	abstract T getSecondEntity();
	abstract CrudRepository<T> getRepository();
		
}
