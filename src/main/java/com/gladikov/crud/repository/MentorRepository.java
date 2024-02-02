package com.gladikov.crud.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.gladikov.crud.model.Mentor;
import com.gladikov.crud.service.ServiceProvider;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MentorRepository implements CrudRepository<Mentor> {

	@Override
	public void create(Mentor entity) {
		String query = "INSERT INTO mentors (first_name, last_name, contruct_number,salary) VALUES (?, ?, ?, ?)";
		try (			
				Connection connection = ServiceProvider.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);
			){
			statement.setString(1, entity.getFirstName());
			statement.setString(2, entity.getLastName());
			statement.setString(3, entity.getContractNumber());
			statement.setDouble(4, entity.getSalary());
			statement.executeUpdate();
			log.info("Record created.");
		} catch (SQLException e) {
			log.error(e.getMessage());
		}

	}

	@Override
	public Optional<Mentor> read(String contractNumber) {
		String query = "SELECT	first_name, last_name, salary  FROM mentors WHERE contract_number=?";
		Optional<Mentor> result = Optional.empty();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
				connection = ServiceProvider.getConnection();
				 statement = connection.prepareStatement(query);
				statement.setString(1, contractNumber);
				rs = statement.executeQuery();
			if (rs.isBeforeFirst()) {
				rs.next();
				Mentor.builder().contractNumber(contractNumber).firstName(rs.getString("first_name"))
						.lastName(rs.getString("last_name")).salary(rs.getDouble("salary")).build();
				log.info("Record read.");
			} else {
				log.info("Not present.");
			}
		} catch (SQLException e) {
			
		} finally {
			try {
				connection.close();
				statement.close();
				rs.close();
			} catch (SQLException e) {
				log.error(e.getMessage());
			}
		}
		return result;
	}

	@Override
	public void update() {

	}

	@Override
	public void delete(String contractNumber) {
		String query = "DELETE	FROM mentors WHERE contract_number=?";
		try (
				Connection connection = ServiceProvider.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)
			) {
			statement.setString(1, contractNumber);
			int row = statement.executeUpdate();
			log.info("? entity deleted",row);
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public List<Mentor> read() {
		String query = "SELECT	first_name, last_name, salary, contract_number  FROM mentors";
		List<Mentor> result = new LinkedList<>();
		try (
				Connection connection = ServiceProvider.getConnection();
				Statement statement = connection.createStatement();
				ResultSet rs = statement.executeQuery(query);
			) {
			while (rs.next()) {
				var mentor=Mentor.builder().contractNumber(rs.getString("contract_number")).firstName(rs.getString("first_name"))
						.lastName(rs.getString("last_name")).salary(rs.getDouble("salary")).build();
				result.add(mentor);
				log.info("Record read.");
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
		return result;
	}

}











