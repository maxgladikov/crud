package com.gladikov.crud.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;

import com.gladikov.crud.dto.MentorDto;
import com.gladikov.crud.service.MentorService;
import com.gladikov.crud.util.ServletHandler;

class MentorControllerTest extends CrudControllerTest<MentorDto>{
	
	protected List<MentorDto> givenListOfDto(){
		return List.of(new MentorDto("Ivan","Petrov",100000.0,"c_001"), new MentorDto("Elena","Smirnova",300000.0,"c_002"));
	}
	
	protected MentorDto givenDto(){
		return new MentorDto("Ivan","Petrov",100000.0,"c_001");
	}

	@Override
	@BeforeEach
	void setUp() {
		handler = new ServletHandler<MentorDto>(MentorDto.class);
		handlerSpy = spy(handler);
		service = mock(MentorService.class);
		controller = new MentorController(service,handlerSpy);
	}
	
}
