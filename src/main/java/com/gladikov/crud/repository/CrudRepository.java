package com.gladikov.crud.repository;

import java.util.List;
import java.util.Optional;

import com.gladikov.crud.model.Entity;

public interface  CrudRepository<T extends Entity> {
	void add(T entity);
	List<T> getAll();
	Optional<T> getByContractNumber(String contractNumber);
	void update(T entity);
	void delete(T entity);
}
