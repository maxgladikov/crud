package com.gladikov.crud.service;

import java.util.List;

import com.gladikov.crud.exception.DaoException;

public interface CrudService<E extends com.gladikov.crud.dto.DTO> {
	E get(String contractNumber) throws DaoException;
	List<E> get() throws DaoException;
	void  save(E dto) throws DaoException;
	void update(E dto) throws DaoException;
	void delete(String contractNumber) throws DaoException;
	
}
