package com.gladikov.crud.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


import com.gladikov.crud.exception.DaoException;
import com.gladikov.crud.model.Mentor;
import com.gladikov.crud.util.ResourceProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// тут все очень хорошо, молодец, что прологировал все
@Slf4j
@RequiredArgsConstructor
public class MentorRepository implements CrudRepository<Mentor> {
	private final ResourceProvider ds;
	@Override
	public void add(Mentor entity) throws DaoException {
		String query = "INSERT INTO mentors (first_name, last_name, contract_number,salary) VALUES (?, ?, ?, ?)";
		try (			
				Connection connection = ds.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);
			){
				statement.setString(1, entity.getFirstName());
				statement.setString(2, entity.getLastName());
				statement.setString(3, entity.getContractNumber());
				statement.setDouble(4, entity.getSalary());
				statement.executeUpdate();
				log.info("Entity {} {} #{} created.", entity.getFirstName(), entity.getLastName(),entity.getContractNumber());
		} catch(SQLException e) {
			throw new DaoException(e);
		}

	}

	@Override
	public Optional<Mentor> getByContractNumber(String contractNumber) throws DaoException {
		String query = "SELECT	first_name, last_name, salary  FROM mentors WHERE contract_number=?"; // это лучше в константу
		try (
				Connection connection = ds.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)
		) {
			statement.setString(1, contractNumber);
			ResultSet rs = statement.executeQuery();
			if (rs.isBeforeFirst()) {
				rs.next();
				Mentor mentor=Mentor.builder().contractNumber(contractNumber).firstName(rs.getString("first_name"))
						.lastName(rs.getString("last_name")).salary(rs.getDouble("salary")).build();
				log.info("Entity with {} succesfully found", contractNumber);
				return Optional.ofNullable(mentor);
			} else {
				log.info("Entity with #{} was not found", contractNumber);
				throw new NoSuchElementException("Requested entity does not exist");
			}
		} catch(SQLException | NoSuchElementException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public void update(Mentor entity) throws DaoException {
		String query = "UPDATE	mentors SET first_name=?, last_name=?, salary=?  WHERE contract_number=?";
		try (
				Connection connection = ds.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);
			) {		statement.setString(1, entity.getFirstName());
					statement.setString(2, entity.getLastName());
					statement.setDouble(3, entity.getSalary());
					statement.setString(4, entity.getContractNumber());
					int row = statement.executeUpdate();
					log.info("{} entity updated",row);
				}catch(SQLException e) {
					throw new DaoException(e);
				} 
	}

	@Override
	public void delete(String contractNumber) throws DaoException {
		String query = "DELETE	FROM mentors WHERE contract_number=?";
		try (
				Connection connection = ds.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)
			){
			statement.setString(1, contractNumber);
			statement.executeUpdate();
			log.info("Entity with contract #{} was deleted", contractNumber);
		}catch(SQLException e) {
			throw new DaoException(e);
		} 
	}

	@Override
	public List<Mentor> getAll() throws DaoException {
		String query = "SELECT	first_name, last_name, salary, contract_number  FROM mentors";
		List<Mentor> result = new LinkedList<>();
		try (
				Connection connection = ds.getConnection();
				Statement statement = connection.createStatement();
				ResultSet rs = statement.executeQuery(query);
			) {
			while (rs.next()) {
				var mentor=Mentor.builder()
						.firstName(rs.getString("first_name"))
						.lastName(rs.getString("last_name"))
						.salary(rs.getDouble("salary"))
						.contractNumber(rs.getString("contract_number"))
						.build();
				result.add(mentor);
			}
			log.info("{} entities was read.", result.size());
		} catch(SQLException e) {
			throw new DaoException(e);
		} 
		return result;
	}

}











