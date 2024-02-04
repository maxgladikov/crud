package com.gladikov.crud.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ServletUtil {
	
	public static <T> T convertJsonToObject(HttpServletRequest request, Class<T> clazz) throws StreamReadException, DatabindException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(request.getReader(), clazz);
	}
}
