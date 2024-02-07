package com.gladikov.crud.repository;

import java.util.List;
import java.util.Optional;

import com.gladikov.crud.exception.DaoException;
import com.gladikov.crud.model.Entity;

public interface  CrudRepository<T extends Entity> {
	/**
	 * save specified entity in database
	 * @param entity
	 * @throws DaoException in case of any DBMS error or other inconsistent state 
	 */
	void add(T entity) throws DaoException;
	
	/**
	 * finds all existing entity if type
	 * @return list of enities or empty list if not any
	 * @throws DaoException in case of any DBMS error or other inconsistent state
	 */
	List<T> getAll() throws DaoException;
	
	/**
	 * finds specified entity
	 * @param contractNumber of required entity
	 * @return Optinal of entity or Empty optional if not any
	 * @throws DaoException in case of any DBMS error or other inconsistent state
	 */
	Optional<T> getByContractNumber(String contractNumber) throws DaoException;
	
	/**
	 * updates specified entity
	 * @param entity
	 * @throws DaoException in case of any DBMS error or other inconsistent state
	 */
	void update(T entity) throws DaoException;
	
	/**
	 * deletes specified entity
	 * @param contractNumber
	 * @throws DaoException in case of any DBMS error or other inconsistent state
	 */
	void delete(String contractNumber) throws DaoException;
}
