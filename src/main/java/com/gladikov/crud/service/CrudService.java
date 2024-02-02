package com.gladikov.crud.service;

import java.util.List;

public interface CrudService<T extends com.gladikov.crud.model.Entity> {
	T get(String contractNumber);
	List<T> get();
	void save(T entity);
	void update();
	void delete(String contractNumber);
	
}
