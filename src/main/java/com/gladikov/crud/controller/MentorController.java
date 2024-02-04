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

@WebServlet("/mentor")
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
		ObjectMapper mapper = new ObjectMapper();
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(mapper.writeValueAsString(service.get()));
		out.flush();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}
}
