package com.gladikov.crud.service;

import java.util.List;

public interface CrudService<E extends com.gladikov.crud.service.dto.DTO> {
	E get(String contractNumber);
	List<E> get();
	void save(E dto);
	void update(E dto);
	void delete(E dto);
	
}
