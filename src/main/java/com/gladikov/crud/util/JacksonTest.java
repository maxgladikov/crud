package com.gladikov.crud.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gladikov.crud.model.Mentor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JacksonTest {
	
	
	public static void main(String[] args) throws JsonProcessingException {
		Mentor mentor=Mentor.builder().firstName("Max").lastName("Gladikov").salary(500000).contractNumber("C4").build();
		ObjectMapper mapper = new ObjectMapper();
		log.info(mapper.writeValueAsString(mentor));
	}
}
