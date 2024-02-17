package com.gladikov.crud.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gladikov.crud.dto.DTO;
import com.gladikov.crud.dto.ErrorResponse;
import com.gladikov.crud.exception.DaoException;
import com.gladikov.crud.service.CrudService;
import com.gladikov.crud.util.ServletHandler;

@ExtendWith(MockitoExtension.class)
abstract class CrudControllerTest<T extends DTO> {
	@Mock protected CrudService<T> service;
	protected CrudController<T> controller;
	protected ServletHandler<T> handler;
	protected ServletHandler<T> handlerSpy;
	@Captor protected ArgumentCaptor<T> captor;
	@Captor protected ArgumentCaptor<String> stringCaptor;
	protected HttpServletRequest request = mock(HttpServletRequest.class);       
	protected HttpServletResponse response = mock(HttpServletResponse.class);
	protected StringWriter stringWriter = new StringWriter();
	protected PrintWriter writer = new PrintWriter(stringWriter);
	protected BufferedReader reader = mock(BufferedReader.class);
	protected T dto;
	
	
	abstract void setUp();
	
	@Test
	void givenServlet_whenProceedGetAll_thenVerify() throws IOException, ServletException, DaoException {
		// given
		when(request.getPathInfo()).thenReturn("/");
		when(request.getParameterMap()).thenReturn(new HashMap<>());
		when(response.getWriter()).thenReturn(writer);
		when(service.get()).thenReturn(givenListOfDto());
		String expected = givenListOfDtoJson();
		// when
		controller.doGet(request, response);
		//then
		assertEquals(expected,stringWriter.toString());
	}
	
	@Test
	void givenServlet_whenProceedGetOne_thenVerify() throws IOException, ServletException, DaoException {
		// given
		String key="c_001";
		String expected= givenDto().json();
		when(request.getPathInfo()).thenReturn("/");
		when(request.getParameterMap()).thenReturn(givenParametersMap(key));
		when(response.getWriter()).thenReturn(writer);
		when(service.get(key)).thenReturn(givenDto());
		// when
		controller.doGet(request, response);
		//then
		assertEquals(expected,stringWriter.toString());
	}
	
	@Test
	void givenServlet_whenProceedGet_thenVerifyNoSuchElement() throws IOException, ServletException, DaoException {
		// given
		String key="c_002";
		String expected= new ErrorResponse("Requested entity does not exist").json();
		when(request.getPathInfo()).thenReturn("/");
		when(request.getParameterMap()).thenReturn(givenParametersMap(key));
		when(response.getWriter()).thenReturn(writer);
		when(service.get(key)).thenThrow(new DaoException(new NoSuchElementException("Requested entity does not exist")));
		// when
		controller.doGet(request, response);
		//then
		assertEquals(expected,stringWriter.toString());
	}
	
	@Test
	void givenServlet_whenProceedGet_thenVerifyBadRequest() throws IOException, ServletException, DaoException {
		// given
		String key="c_002";
		String expected= new ErrorResponse("bad request").json();
		when(request.getPathInfo()).thenReturn("/");
		when(request.getParameterMap()).thenReturn(givenInvalidParametersMap(key));
		when(response.getWriter()).thenReturn(writer);
		// when
		controller.doGet(request, response);
		//then
		assertEquals(expected,stringWriter.toString());
	}
	
	@Test
	void givenServlet_whenProceedGet_thenVerify404() throws IOException, ServletException, DaoException {
		// given
		
		String expected= new ErrorResponse("Requested URL not exist").json();
		when(request.getPathInfo()).thenReturn("/500");
		when(response.getWriter()).thenReturn(writer);
		// when
		controller.doGet(request, response);
		//then
		assertEquals(expected,stringWriter.toString());
	}
	
	@Test
	void givenServlet_whenProceedPost_thenVerify() throws IOException, ServletException, DaoException {
		// given
		T expected= givenDto();
		when(request.getReader()).thenReturn(reader);
		when(reader.lines()).thenReturn(expected.json().lines());
		when(request.getPathInfo()).thenReturn("/");
		when(response.getWriter()).thenReturn(writer);
		// when
		controller.doPost(request, response);
		//then
		verify(service).save(captor.capture());
		assertEquals("{ \"message\": \"success\" }",stringWriter.toString());
		assertEquals(expected,captor.getValue());
	}
	
	@Test
	void givenServlet_whenProceedPost_thenVerifyBadRequest() throws IOException, ServletException, DaoException {
		// given
		String expected= new ErrorResponse("bad request").json();
		when(request.getReader()).thenReturn(reader);
		when(reader.lines()).thenReturn(givenErrorResponse().json().lines());
		when(request.getPathInfo()).thenReturn("/");
		when(response.getWriter()).thenReturn(writer);
		// when
		controller.doPost(request, response);
		//then
		verify(handlerSpy,times(1)).handleBadRequest(any());
		assertEquals(expected,stringWriter.toString());
	}
	
	@Test
	void givenServlet_whenProceedPost_thenVerify404() throws IOException, ServletException, DaoException {
		// given
	     String expected= new ErrorResponse("Requested URL not exist").json();
	     when(request.getPathInfo()).thenReturn("/val");
	     when(response.getWriter()).thenReturn(writer);
	     // when
	     controller.doPost(request, response);
	     //then
	     assertEquals(expected,stringWriter.toString());
	}
	
	@Test
	void givenServlet_whenProceedDelete_thenVerify() throws IOException, ServletException, DaoException {
		// given
		String key="c_001";
		when(response.getWriter()).thenReturn(writer);
		when(request.getPathInfo()).thenReturn("/");
		when(request.getParameterMap()).thenReturn(givenParametersMap(key));
		// when
		controller.doDelete(request, response);
		//then
		verify(service).delete(stringCaptor.capture());
		assertEquals(key,stringCaptor.getValue());
	}
	
	@Test
	void givenServlet_whenProceedDelete_thenVerifyBadRequest() throws IOException, ServletException, DaoException {
		// given
		String expected= new ErrorResponse("bad request").json();
		when(request.getPathInfo()).thenReturn("/");
		when(response.getWriter()).thenReturn(writer);
		when(request.getParameterMap()).thenReturn(givenInvalidParametersMap("val"));
		// when
		controller.doDelete(request, response);
		//then
		verify(handlerSpy,times(1)).handleBadRequest(any());
		assertEquals(expected,stringWriter.toString());
	}
	
	@Test
	void givenServlet_whenProceedDelete_thenVerify404() throws IOException, ServletException, DaoException {
		// given
	     String expected= new ErrorResponse("Requested URL not exist").json();
	     when(request.getPathInfo()).thenReturn("/val");
	     when(response.getWriter()).thenReturn(writer);
	     // when
	     controller.doDelete(request, response);
	     //then
	     assertEquals(expected,stringWriter.toString());
	}
	
	@Test
	void givenServlet_whenProceedPut_thenVerify() throws IOException, ServletException, DaoException {
		// given
		T expected= givenDto();
		when(request.getReader()).thenReturn(reader);
		when(reader.lines()).thenReturn(expected.json().lines());
		when(request.getPathInfo()).thenReturn("/");
		when(response.getWriter()).thenReturn(writer);
		// when
		controller.doPut(request, response);
		//then
		verify(service).update(captor.capture());
		assertEquals("{ \"message\": \"success\" }",stringWriter.toString());
		assertEquals(expected,captor.getValue());
	}
	
	@Test
	void givenServlet_whenProceedPut_thenVerifyBadRequest() throws IOException, ServletException, DaoException {
		// given
		String expected= new ErrorResponse("bad request").json();
		when(request.getPathInfo()).thenReturn("/");
		when(response.getWriter()).thenReturn(writer);
		when(request.getParameterMap()).thenReturn(givenInvalidParametersMap("val"));
		// when
		controller.doPut(request, response);
		//then
		verify(handlerSpy,times(1)).handleBadRequest(any());
		assertEquals(expected,stringWriter.toString());
	}
	
	@Test
	void givenServlet_whenProceedPutWrongInput_thenVerifyBadRequest() throws IOException, ServletException, DaoException {
		// given
		String expected= new ErrorResponse("bad request").json();
		when(request.getPathInfo()).thenReturn("/");
		when(response.getWriter()).thenReturn(writer);
		when(request.getReader()).thenReturn(reader);
		when(reader.lines()).thenReturn(expected.lines());
		// when
		controller.doPut(request, response);
		//then
		verify(handlerSpy,times(1)).handleBadRequest(any());
		assertEquals(expected,stringWriter.toString());
	}
	
	@Test
	void givenServlet_whenProceedPut_thenVerify404() throws IOException, ServletException, DaoException {
		// given
	     String expected= new ErrorResponse("Requested URL not exist").json();
	     when(request.getPathInfo()).thenReturn("/val");
	     when(response.getWriter()).thenReturn(writer);
	     // when
	     controller.doPut(request, response);
	     //then
	     assertEquals(expected,stringWriter.toString());
	}
	
	@AfterEach
	void cleanUp() {
		clear();
	}
	
	private Map<String,String[]> givenParametersMap(String value){
		String[] values = {value}; 
		Map<String,String[]> map= new HashMap<>();
		map.put("contract", values);
		return map;
	}
	
	private Map<String,String[]> givenInvalidParametersMap(String value){
		String[] values = {value}; 
		Map<String,String[]> map= new HashMap<>();
		map.put("contracts", values);
		return map;
	}
	
	protected String givenListOfDtoJson() {
		String result=null;
		try {
			result = new ObjectMapper().writeValueAsString(givenListOfDto());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	abstract protected List<T> givenListOfDto();
	
	abstract protected T givenDto();
	
	protected ErrorResponse givenErrorResponse(){
		return new ErrorResponse("Error");
	}
	
	private void clear() {
		stringWriter.getBuffer().setLength(0);
	}
}
