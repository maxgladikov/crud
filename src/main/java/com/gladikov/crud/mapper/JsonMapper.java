package com.gladikov.crud.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonMapper {
	private final static ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
	
	public static String map(Object object) {
		String result = null;
		try {
			result = ow.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}
		return result;
	}
}
