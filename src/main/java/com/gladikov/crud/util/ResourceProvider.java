package com.gladikov.crud.util;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResourceProvider {
	private final Profile profile;
	private final ProfileReader reader=new ProfileReader();
	
	public ResourceProvider() {
		profile=reader.read("prod")
				.orElseThrow(() -> new RuntimeException("Requested profile is not availible"));
	}
	
	public ResourceProvider(String profileName) {
		profile=reader.read(profileName)
				.orElseThrow(() -> new RuntimeException("Requested profile is not availible"));
	}
	
	public DataSource getDatasource() {
		HikariConfig config = new HikariConfig();
		config.setUsername(profile.getUsername());
		config.setPassword(profile.getPassword());
		config.setJdbcUrl(profile.getUrl());
		DataSource ds = new HikariDataSource(config);
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage());
		}
		log.info("DataSource with url {} was successfully created", profile.getUrl());
		return ds;
	}
	
	
	
	
}
