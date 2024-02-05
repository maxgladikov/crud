package com.gladikov.crud.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.gladikov.crud.exception.DaoException;
import com.gladikov.crud.model.Entity;

public interface  CrudRepository<T extends Entity> {
	void add(T entity) throws DaoException;
	List<T> getAll() throws DaoException;
	Optional<T> getByContractNumber(String contractNumber) throws DaoException;
	void update(T entity) throws DaoException;
	void delete(String contractNumber) throws DaoException;
}
