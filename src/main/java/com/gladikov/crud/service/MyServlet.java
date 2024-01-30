package com.gladikov.crud.service;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/hello")
public class MyServlet extends HttpServlet{
	private static final long serialVersionUID = -6453213988990992889L;

	@Override
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
//		int i=Integer.parseInt(req.getParameter("num1"));
		res.getWriter().println("Hello!");
	}
}
