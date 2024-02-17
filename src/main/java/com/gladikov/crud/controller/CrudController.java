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

	private static final long SERIAL_VERSION_UID = 1L; // константы аппер кейсом
	public static final String CONTRACT_PARAM = "contract";
	public static final String SLASH = "/";
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
			} else if (params.size() == 1 && params.containsKey(CONTRACT_PARAM)) {
				String key = params.get(CONTRACT_PARAM)[0];
				handler.handleGetOne(response, service, key);
			} else
				handler.handleBadRequest(response);
		}else
			handler.handle404(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		if (SLASH.equals(request.getPathInfo())) { // тут лучше сделать зеркально, чтобы не было NPE + вынести строку в константу
			handler.handlePost(request, response, service);
		} else
			handler.handle404(request, response);
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		var params = request.getParameterMap();
		if (request.getPathInfo().equals("/")) {
			if ( params.size() == 1 && params.containsKey(CONTRACT_PARAM) )
					handler.handleDelete(request, response, service, params.get(CONTRACT_PARAM)[0]);
				else
					handler.handleBadRequest(response);
		} else
			handler.handle404(request,response);
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
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
