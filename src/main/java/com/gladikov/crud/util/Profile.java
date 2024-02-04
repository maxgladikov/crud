package com.gladikov.crud.util;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Profile implements Serializable{
	private static final long serialVersionUID = 1L;
	@JsonProperty("url")
	private  String url;
	@JsonProperty("username")
	private  String username;
	@JsonProperty("password")
	private  String password;
	@JsonProperty("driver")
	private  String driver;
}
