package com.gladikov.crud.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;
import static org.mockito.Mockito.any;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gladikov.crud.dto.DTO;
import com.gladikov.crud.exception.DaoException;
import com.gladikov.crud.model.Entity;
import com.gladikov.crud.repository.CrudRepository;

@ExtendWith(MockitoExtension.class)
abstract class CrudServiceTest<T extends DTO,E extends Entity> {
	
	@Captor ArgumentCaptor<String> stringCaptor;
	@Captor ArgumentCaptor<T> dtoCaptor;
	@Captor ArgumentCaptor<E> entityCaptor;
	protected CrudService<T> service;
	protected CrudRepository<E> repo;
	protected T givenDto;
	protected E givenEntity;
	
	CrudServiceTest(){
		try {
			service=getService();
		} catch (DaoException e) {
			e.printStackTrace();
		}
		givenDto=givenDto();
		givenEntity=givenEntity();
	}
	
	@Test
	void givenService_whenProceedGet_thenVerify() throws DaoException {
		// given
		List<T> expected=Collections.singletonList(givenDto);
		// when
		List<T> result=service.get();
		// then
		assertEquals(expected,result);
	}
	
	@Test
	void givenService_whenProceedGetByDto_thenVerify() throws DaoException {
		// given
		// when
		T result=service.get(givenDto.contractNumber());
		// then
		assertEquals(givenDto,result);
	}
	
	@Test
	void givenService_whenProceedSave_thenVerify() throws DaoException {
		// given
		// when
		service.save(givenDto);
		// then
		verify(repo,times(1)).add((E) any(Entity.class));
		verify(repo).add(entityCaptor.capture());
		assertEquals(givenEntity,entityCaptor.getValue());
	}
	
	@Test
	void givenService_whenProceedUpdate_thenVerify() throws DaoException {
		// given
		// when
		service.update(givenDto);
		// then
		verify(repo,times(1)).update((E) any(Entity.class));
		verify(repo).update(entityCaptor.capture());
		assertEquals(givenEntity,entityCaptor.getValue());
	}
	
	@Test
	void givenService_whenProceedDelete_thenVerify() throws DaoException {
		// given
		// when
		service.delete(givenDto.contractNumber());
		// then
		verify(repo,times(1)).delete( Mockito.anyString());
		verify(repo).delete(stringCaptor.capture());
		assertEquals(givenDto.contractNumber(),stringCaptor.getValue());
	}
	
	abstract T givenDto();
	abstract E givenEntity();
	abstract CrudService<T> getService() throws DaoException;

}
