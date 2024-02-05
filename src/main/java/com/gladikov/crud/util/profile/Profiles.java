package com.gladikov.crud.util.profile;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Profiles implements Serializable {

	private static final long serialVersionUID = 1L;
	@JsonProperty("profiles")
	private Map<String, Profile> profiles = new HashMap<>();

}