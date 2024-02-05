package com.gladikov.crud.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class ServletUtil {
	private ServletUtil() {}
	public static <T> T convertJsonToObject(HttpServletRequest request, Class<T> clazz) {
		T result=null;
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.AUTO_CLOSE_SOURCE, true);
		try {
			result = mapper.readValue(request.getReader(), clazz);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		log.debug(result.toString());
		return result;
	}
}
