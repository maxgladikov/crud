package com.gladikov.crud.dto;

import com.gladikov.crud.mapper.JsonMapper;

public record ErrorResponse(String message) {
	public String json() {
		return JsonMapper.map(this);
	}
}
