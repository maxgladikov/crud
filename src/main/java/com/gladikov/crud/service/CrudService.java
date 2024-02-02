package com.gladikov.crud.service;

public interface CrudService<T extends com.gladikov.crud.model.Entity> {
	T get(String contractNumber);
	void save(T entity);
	void update();
	void delete(String contractNumber);
	
}
