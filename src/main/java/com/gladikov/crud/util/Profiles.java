package com.gladikov.crud.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Profiles implements Serializable {

	@JsonProperty("profiles")
	private Map<String, Profile> profiles = new HashMap<>();

}