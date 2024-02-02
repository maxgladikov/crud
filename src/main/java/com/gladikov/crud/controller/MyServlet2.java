package com.gladikov.crud.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/bye")
public class MyServlet2 extends HttpServlet{
	private static final long serialVersionUID = -6453213988990992889L;

	@Override
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
//		int i=Integer.parseInt(req.getParameter("num1"));
		res.getWriter().println("Bye!");
		PrintWriter out = res.getWriter();
		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
//		out.print(employeeJsonString);
		out.flush();
	}
}
