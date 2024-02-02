package com.gladikov.crud.repository;

import java.util.List;
import java.util.Optional;

public interface  CrudRepository<T> {
	void create(T entity);
	List<T> read();
	Optional<T> read(String contractNumber);
	void update();
	void delete(String contractNumber);
}
