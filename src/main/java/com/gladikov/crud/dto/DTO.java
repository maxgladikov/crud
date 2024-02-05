package com.gladikov.crud.dto;

import com.gladikov.crud.mapper.JsonMapper;

public interface DTO {
	String contractNumber();
	
	default String json() {
		return JsonMapper.map(this);
	}
	
}
