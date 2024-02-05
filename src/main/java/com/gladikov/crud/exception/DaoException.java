package com.gladikov.crud.exception;

public class DaoException extends Exception{
	private static final long serialVersionUID = -2016727403240676224L;
	public DaoException(Throwable e) {
		super(e.getMessage(),e,true,false);
	}
	public DaoException(String massage) {
		super(massage,null,true,false);
	}
}
