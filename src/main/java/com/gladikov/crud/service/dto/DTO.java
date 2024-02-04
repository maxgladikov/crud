package com.gladikov.crud.service.dto;

import com.gladikov.crud.util.JsonMapper;

public interface DTO {

	
	default String json() {
		return JsonMapper.map(this);
	}
	
}
