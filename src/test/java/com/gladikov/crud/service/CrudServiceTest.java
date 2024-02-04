package com.gladikov.crud.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.gladikov.crud.service.dto.DTO;

abstract class CrudServiceTest<T extends DTO> {
	protected CrudService<T> service=getService();
	
	@Test
	void givenService_whenProceedGet_thenVerify() {
		// given
		T dto=givenDto();
		List<T> expected=Collections.singletonList(dto);
		// when
		List<T> result=service.get();
		// then
		assertEquals(expected,result);
	}
	
	@Test
	void givenService_whenProceedGetByDto_thenVerify() {
		// given
		T expected=givenDto();
		// when
		T result=service.get(expected);
		// then
		assertEquals(expected,result);
	}
	
	
	
	abstract T givenDto();
	abstract CrudService<T> getService();

}
