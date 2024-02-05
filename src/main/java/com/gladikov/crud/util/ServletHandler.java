package com.gladikov.crud.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gladikov.crud.dto.DTO;
import com.gladikov.crud.dto.ErrorResponse;
import com.gladikov.crud.exception.DaoException;
import com.gladikov.crud.service.CrudService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServletHandler <T extends DTO>{
	private ObjectMapper mapper = new ObjectMapper();
	private final Class<T> type;
	
	public ServletHandler(Class<T> type) {
        this.type = type;
   }
	
	public void handle404(HttpServletRequest request,HttpServletResponse response) throws IOException {
		log.error("Requested url="+ request.getRequestURL() +" not exist");
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.setStatus(404);
		out.print(new ErrorResponse("Requested URL not exist").json());
		out.flush();
	}
	
	public void handleBadRequest(HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.setStatus(400);
		out.print(new ErrorResponse("bad request").json());
		out.flush();
	}
	
	public void handleGetAll(HttpServletResponse response, CrudService<T> service) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		try {
			out.print(mapper.writeValueAsString(service.get()));
			out.flush();
		} catch (JsonProcessingException | DaoException e) {
			handleError(e, out);
			response.setStatus(500);
		}
		response.setStatus(200);
	}

	public void handleGetOne(HttpServletResponse response, CrudService<T> service, String key) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try {
			out.print(service.get(key).json());
			out.flush();
		} catch (DaoException e) {
			handleError(e, out);
			response.setStatus(400);
		}
		response.setStatus(200);
	}
	
	public void handlePost(HttpServletRequest request, HttpServletResponse response, CrudService<T> service) {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			T dto =  convertJsonToObject(request);
			service.save(dto);
			out.print("{ \"message\": \"success\" }");
			out.flush();
		} catch (DaoException e) {
			handleError(e, out);
			response.setStatus(500);
		} catch (IOException  e) {
			handleBadRequest(response);
		}
		response.setStatus(201);
	}
	
	public void handleDelete(HttpServletRequest request, HttpServletResponse response, CrudService<T> service, String key) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try {
			service.delete(key);
			out.print("{ \"message\": \"success\" }");
			out.flush();
		} catch (DaoException e) {
			handleError(e, out);
			response.setStatus(404);
		}
		response.setStatus(200);
	}
	
	public void handlePut(HttpServletRequest request, HttpServletResponse response, CrudService<T> service) {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			T dto =  convertJsonToObject(request);
			service.update(dto);
			out.print("{ \"message\": \"success\" }");
			out.flush();
		} catch (DaoException e) {
			handleError(e, out);
			response.setStatus(500);
		} catch (IOException  e) {
			handleBadRequest(response);
		}
		response.setStatus(200);
	}
	
	public void handleError(Exception e,PrintWriter out) {
		log.error(e.getMessage());
		out.print(new ErrorResponse(e.getMessage()).json());
		out.flush();
	}
	
	private  T convertJsonToObject(HttpServletRequest request) throws IOException{
		String reqString = request.getReader().lines().collect(Collectors.joining());
		return mapper.readValue(reqString, type);
	}
	
}
