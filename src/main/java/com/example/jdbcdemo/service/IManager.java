package com.example.jdbcdemo.service;

import java.sql.Connection;
import java.util.List;

public interface IManager {

	public Connection getConnection();

	public void clear();

	void deleteOne(long id);

	public List<?> getAll();

}
