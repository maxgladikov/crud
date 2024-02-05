package com.gladikov.crud.util;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.gladikov.crud.dto.MentorDto;

class ServletUtilTest {
	
	HttpServletRequest req=Mockito.mock(HttpServletRequest.class);
	
	@Test
	void givenJsonStringOfMentor_whenProceedConvertToObject_thenVerify() throws StreamReadException, DatabindException, IOException {
		// given
		MentorDto expected=givenMentorDto();
		String json=givenMentorJsonString();
		var br=new BufferedReader(new InputStreamReader(new ByteArrayInputStream(json.getBytes()), "UTF-8"));
		when(req.getReader()).thenReturn(br);
		// when
		MentorDto result=(MentorDto) ServletUtil.convertJsonToObject(req, expected.getClass());
		//then
		assertEquals(expected,result);
		
	}
	
	@Test
	void givenJsonStringOfStudent_whenProceedConvertToObject_thenVerify() throws StreamReadException, DatabindException, IOException {
		// given
		MentorDto expected=givenStudentDto();
		String json=givenStudentJsonString();
		var br=new BufferedReader(new InputStreamReader(new ByteArrayInputStream(json.getBytes()), "UTF-8"));
		when(req.getReader()).thenReturn(br);
		// when
		MentorDto result=(MentorDto) ServletUtil.convertJsonToObject(req, expected.getClass());
		//then
		assertEquals(expected,result);
		
	}
	
	MentorDto givenMentorDto() {
		return new MentorDto("Agata","Rakova",100000.0,"c_003");
	}
	String givenMentorJsonString() {
		return """
				{"firstName":"Agata","lastName":"Rakova","salary":100000.0,"contractNumber":"c_003"}
				"""
				;
	}
	
	 MentorDto givenStudentDto() {
		 return new MentorDto("Agata","Rakova",100000.0,"c_003");
	 }
	String givenStudentJsonString() {
		return """
				{"firstName":"Agata","lastName":"Rakova","salary":100000.0,"contractNumber":"c_003"}
				"""
		;
	}

}
