package com.gladikov.crud.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gladikov.crud.dto.StudentDto;
import com.gladikov.crud.exception.DaoException;
import com.gladikov.crud.service.CrudService;
import com.gladikov.crud.util.ResourceProvider;
import com.gladikov.crud.util.ServletHandler;

import lombok.NoArgsConstructor;

import com.gladikov.crud.service.StudentService;

@WebServlet("/v1/api/university/students/*")
@NoArgsConstructor
public class StudentController extends CrudController<StudentDto>{

	private static final long serialVersionUID = 3101794552976649468L;

	StudentController(CrudService<StudentDto> service, ServletHandler<StudentDto> handler) {
		super(service, handler);
	}

	@Override
	public void init() {
		ServletContext ctx = getServletContext();
		rp = (ResourceProvider) ctx.getAttribute("ResourceProvider");
		service = new StudentService(rp);
		handler = new ServletHandler<>(StudentDto.class);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		var params = request.getParameterMap();
		if (request.getPathInfo().equals("/")) {
			if (params.isEmpty()) {
				handler.handleGetAll(response, service);
			} else if (params.size() == 1 && params.containsKey("contract")) {
				String key = params.get("contract")[0];
				handler.handleGetOne(response, service, key);
			} else
				handler.handleBadRequest(response);
		} else if (request.getPathInfo().equals("/mentor/")) {
			if (params.size() == 1 && params.containsKey("mentor_id")) {
				handleGetByMentor(response,params.get("mentor_id")[0]);
			} else 
				handler.handleBadRequest(response);
		}else
			handler.handle404(request, response);
	}
	
	private void handleGetByMentor(HttpServletResponse response, String key) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		PrintWriter out = response.getWriter();
		StudentService studentService = (StudentService) service;
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		try {
			int intKey = (int) Integer.parseInt(key);
			out.print(mapper.writeValueAsString(studentService.getAllByMentor(intKey)));
			out.flush();
		} catch (JsonProcessingException | DaoException e) {
			handler.handleError(e, out);
			response.setStatus(500);
		} catch (NumberFormatException e) {
			handler.handleBadRequest(response);
		}
		response.setStatus(200);
	}
	
}
