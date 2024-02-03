package com.gladikov.crud.util;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class ProfileReader implements Serializable {

	public static  Optional<Profile> read(String profileName){
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		Profiles profiles = null;
			try {
				profiles=  mapper.readValue(new File("src/main/resources/application.yml"), Profiles.class);
			} catch (IOException e) {
				log.error(e.getMessage());
			}
			return Optional.ofNullable(profiles.getProfiles().get(profileName));
		}

}
