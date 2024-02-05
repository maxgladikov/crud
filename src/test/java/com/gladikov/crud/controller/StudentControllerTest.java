package com.gladikov.crud.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gladikov.crud.dto.StudentDto;
import com.gladikov.crud.exception.DaoException;
import com.gladikov.crud.model.Sex;
import com.gladikov.crud.service.StudentService;
import com.gladikov.crud.util.ServletHandler;

class StudentControllerTest extends CrudControllerTest<StudentDto>{

	@Override
	protected StudentDto givenDto() {
		return new StudentDto("Andrey","Petrov",20,Sex.MALE,3.5,"pc_002",1);
	}

	@Override
	protected List<StudentDto> givenListOfDto() {
		return List.of(givenDto(),new StudentDto("Andrey","Petrov",20,Sex.MALE,3.5,"pc_002",1));
	}

	
	@Override
	@BeforeEach
	void setUp() {
		handler = new ServletHandler<StudentDto>(StudentDto.class);
		handlerSpy = spy(handler);
		service = mock(StudentService.class);
		controller = new StudentController(service,handlerSpy);
	}

	@Test
	void givenServlet_whenProceedGetByMentor_thenVerify() throws IOException, ServletException, DaoException {
		// given
		StudentService studentService = (StudentService) service;
		String expected= givenListOfDtoJson();
		when(request.getPathInfo()).thenReturn("/mentor/");
		Map<String,String[]> map = new HashMap<>();
		map.put(new String("mentor_id"), new String[]{"1"});
		when(request.getParameterMap()).thenReturn(map);
		when(response.getWriter()).thenReturn(writer);
		when(studentService.getAllByMentor(1)).thenReturn(givenListOfDto());
		// when
		controller.doGet(request, response);
		//then
		assertEquals(expected,stringWriter.toString());
	}
}
