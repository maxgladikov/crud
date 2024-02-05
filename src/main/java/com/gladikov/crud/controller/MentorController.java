package com.gladikov.crud.controller;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;

import com.gladikov.crud.dto.MentorDto;
import com.gladikov.crud.service.CrudService;
import com.gladikov.crud.service.MentorService;
import com.gladikov.crud.util.ResourceProvider;
import com.gladikov.crud.util.ServletHandler;

import lombok.NoArgsConstructor;

@WebServlet("/v1/api/university/mentors/*")
@NoArgsConstructor
public class MentorController extends CrudController<MentorDto>{

	MentorController(CrudService<MentorDto> service,ServletHandler<MentorDto> handler){
		super(service, handler);
	}
	
	@Override
	public void init(){
		ServletContext ctx = getServletContext();
		rp = (ResourceProvider) ctx.getAttribute("ResourceProvider");
		service = new MentorService(rp);
		handler = new ServletHandler<>(MentorDto.class);
	}

}
