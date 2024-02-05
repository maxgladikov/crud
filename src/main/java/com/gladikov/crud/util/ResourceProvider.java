package com.gladikov.crud.util;

import javax.sql.DataSource;

import com.gladikov.crud.util.profile.Profile;
import com.gladikov.crud.util.profile.ProfileReader;
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
		DataSource ds = null ;
		try {
			Class.forName("org.postgresql.Driver");
			HikariConfig config = new HikariConfig();
			config.setUsername(profile.getUsername());
			config.setPassword(profile.getPassword());
			config.setJdbcUrl(profile.getUrl());
			ds = new HikariDataSource(config);
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage());
		}
		log.info("DataSource with url {} was successfully created", profile.getUrl());
		return ds;
	}
	
	
	
	
	
}
