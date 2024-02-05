package com.gladikov.crud.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gladikov.crud.dto.DTO;
import com.gladikov.crud.service.CrudService;
import com.gladikov.crud.util.ResourceProvider;
import com.gladikov.crud.util.ServletHandler;

import lombok.NoArgsConstructor;
@NoArgsConstructor
public abstract class CrudController<T extends DTO> extends HttpServlet {

	private static final long serialVersionUID = 1L;
	protected CrudService<T> service;
	protected ServletHandler<T> handler;
	protected ResourceProvider rp;
	
	CrudController(CrudService<T> service,ServletHandler<T> handler){
		this.service=service;
		this.handler=handler;
	}
	
	@Override
	public abstract void init();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getPathInfo().equals("/")) {
			var params = request.getParameterMap();
			if (params.isEmpty()) {
				handler.handleGetAll(response, service);
			} else if (params.size() == 1 && params.containsKey("contract")) {
				String key = params.get("contract")[0];
				handler.handleGetOne(response, service, key);
			} else
				handler.handleBadRequest(response);
		}else
			handler.handle404(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getPathInfo().equals("/")) {
			handler.handlePost(request, response, service);
		} else
			handler.handle404(request, response);
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		var params = request.getParameterMap();
		if (request.getPathInfo().equals("/")) {
			if ( params.size() == 1 && params.containsKey("contract") )
					handler.handleDelete(request, response, service, params.get("contract")[0]);
				else
					handler.handleBadRequest(response);
		} else
			handler.handle404(request,response);
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getPathInfo().equals("/")) {
			if (request.getParameterMap().isEmpty())
				handler.handlePut(request, response, service);
			else
				handler.handleBadRequest(response);
			}
		else
			handler.handle404(request,response);
	}
}
