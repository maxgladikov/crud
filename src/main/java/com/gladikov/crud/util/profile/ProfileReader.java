package com.gladikov.crud.util.profile;

import java.io.InputStream;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class ProfileReader{
	
	private static String path = "application.yml";
	
	public  Optional<Profile> read(String profileName){
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		Profiles profiles = null;
		InputStream inputStream;
			try {
				inputStream = this.getClass().getClassLoader().getResourceAsStream(path);
				profiles=  mapper.readValue(inputStream, Profiles.class);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			return Optional.ofNullable(profiles.getProfiles().get(profileName));
		}

}
