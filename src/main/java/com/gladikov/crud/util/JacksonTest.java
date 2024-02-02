package com.gladikov.crud.util;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gladikov.crud.model.Mentor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JacksonTest {
	
	
	public static void main(String[] args) throws JsonProcessingException {
		Mentor mentor1=Mentor.builder().firstName("Max").lastName("Gladikov").salary(500000).contractNumber("C4").build();
		Mentor mentor2=Mentor.builder().firstName("Maxi").lastName("Gladikov").salary(500000).contractNumber("C4").build();
		
		ObjectMapper mapper = new ObjectMapper();
		log.info(mapper.writeValueAsString(List.of(mentor1,mentor2)));
	}
}
