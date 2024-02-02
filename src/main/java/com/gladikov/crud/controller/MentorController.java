package com.gladikov.crud.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gladikov.crud.model.Mentor;
import com.gladikov.crud.service.CrudService;
import com.gladikov.crud.service.MentorService;

@WebServlet("/mentor")
public class MentorController extends HttpServlet{
	CrudService<Mentor> service= new MentorService();
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Mentor mentor=Mentor.builder().firstName("Max").lastName("Gladikov").salary(500000).contractNumber("C4").build();
		ObjectMapper mapper = new ObjectMapper();
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(mapper.writeValueAsString(service.get()));
		out.flush();
	}
	
	 @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
	 }
}