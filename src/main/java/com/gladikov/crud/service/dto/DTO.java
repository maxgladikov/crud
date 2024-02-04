package com.gladikov.crud.service.dto;

import com.gladikov.crud.util.JsonMapper;

public interface DTO {
	String contractNumber();
	
	default String json() {
		return JsonMapper.map(this);
	}
	
}
