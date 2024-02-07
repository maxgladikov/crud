package com.gladikov.crud.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gladikov.crud.dto.ErrorResponse;

@WebServlet("/v1/api/university/hello/")
public class HelloController extends HttpServlet {
	private static final long serialVersionUID = 7925463475754627368L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.setStatus(200);
		String hello="Hello!";
		out.print(new ErrorResponse(hello).json());
		out.flush();
	}
}
