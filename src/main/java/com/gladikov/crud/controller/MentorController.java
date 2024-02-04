package com.gladikov.crud.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gladikov.crud.model.Mentor;
import com.gladikov.crud.service.CrudService;
import com.gladikov.crud.service.MentorService;
import com.gladikov.crud.service.dto.MentorDto;
import com.gladikov.crud.util.ServletUtil;

@WebServlet("/mentor/*")
public class MentorController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private CrudService<MentorDto> service;
	private DataSource ds;

	@Override
	public void init() throws ServletException {
		ServletContext ctx = getServletContext();
		ds = (DataSource) ctx.getAttribute("DataSource");
		service = new MentorService(ds);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getPathInfo().equals("/")){
			var params = request.getParameterMap();
			if (params.isEmpty()) {
				ObjectMapper mapper = new ObjectMapper();
				PrintWriter out = response.getWriter();
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				out.print(mapper.writeValueAsString(service.get()));
				out.flush();
			} else if (params.size() == 1 && params.containsKey("contract")){
				PrintWriter out = response.getWriter();
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				var contract = params.get("contract");
				out.print(service.get(contract[0]));
				out.flush();
			} else 
				response.sendError(HttpServletResponse.SC_BAD_REQUEST); 
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getPathInfo().equals("/")) {
			MentorDto mentor=ServletUtil.convertJsonToObject(request, MentorDto.class);
			service.save(mentor);
			response.setContentType("text/json");
			response.setStatus(201);
		} else 
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}
