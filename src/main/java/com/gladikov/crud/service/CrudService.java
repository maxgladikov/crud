package com.gladikov.crud.service;

import java.util.List;

import com.gladikov.crud.exception.DaoException;

public interface CrudService<E extends com.gladikov.crud.dto.DTO> {
	
	/**
	 * 
	 * @param finds specified class
	 * @return DTO of specified entity
	 * @throws DaoException in case of any DBMS error or other inconsistent state
	 */
	E get(String contractNumber) throws DaoException;
	
	/**
	 * finds all existed entities of its type
	 * @return list of entities or empty list if not any
	 * @throws DaoException in case of any DBMS error or other inconsistent state
	 */
	List<E> get() throws DaoException;
	
	/**
	 * saves specified entity
	 * @param dto of entity
	 * @throws DaoException in case of any DBMS error or other inconsistent state
	 */
	void  save(E dto) throws DaoException;
	
	/**
	 * updates specified entity
	 * @param dto of entity
	 * @throws DaoException in case of any DBMS error or other inconsistent state
	 */
	void update(E dto) throws DaoException;
	
	/**
	 * deletes specified entity
	 * @param contractNumber
	 * @throws DaoException in case of any DBMS error or other inconsistent state
	 */
	void delete(String contractNumber) throws DaoException;
	
}
