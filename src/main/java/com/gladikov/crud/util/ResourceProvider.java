package com.gladikov.crud.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
	private  DataSource dataSource;
	
	public ResourceProvider() {
		profile=reader.read("prod")
				.orElseThrow(() -> new RuntimeException("Requested profile is not availible"));
	}
	
	public ResourceProvider(String profileName) {
		profile=reader.read(profileName)
				.orElseThrow(() -> new RuntimeException("Requested profile is not availible"));
	}
	
	private void initDataSource() {
			DataSource ds = null;
			try {
				Class.forName("org.postgresql.Driver");
				HikariConfig config = new HikariConfig();
				config.setUsername(profile.getUsername());
				config.setPassword(profile.getPassword());
				config.setJdbcUrl(profile.getUrl());
				config.addDataSourceProperty( "cachePrepStmts" , "true" );
		        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
		        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
				ds = new HikariDataSource(config);
			} catch (ClassNotFoundException e) {
				log.error(e.getMessage());
			}
			log.info("DataSource with url {} was successfully created", profile.getUrl());
			dataSource = ds;
	}
	
	private Connection getH2Connection() {
		Connection con = null;
		try {
			con = DriverManager.getConnection(profile.getUrl());
			con.setAutoCommit(true);
			log.info("connection with url={} was issued",profile.getUrl());
		} catch (SQLException e) {
			log.error(e.getMessage());
			throw new RuntimeException("H2 connection is null");
		}
		return con;
	}
	
	private Connection getHikariConnection() {
		if (dataSource == null)
			initDataSource();
		Connection con = null;
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			log.error(e.getMessage());
			throw new RuntimeException("Hikari Connection is null");
		}
		return con;
	}
	
	public synchronized Connection getConnection() {
		Connection con = null;
		if (profile.getUrl().contains("h2")) 
			con = getH2Connection();
		else 
			con = getHikariConnection();
		return con;
	}
	
	
	
	
	
	
	
}
