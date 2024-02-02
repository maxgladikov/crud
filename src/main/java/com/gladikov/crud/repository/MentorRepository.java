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
import com.gladikov.crud.service.ConnectionProvider;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MentorRepository implements CrudRepository<Mentor> {
	private Connection connection = ConnectionProvider.get();

	@Override
	public void create(Mentor entity) {
		try {
			String query = "INSERT INTO mentors (first_name, last_name, contruct_number,salary) VALUES (?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, entity.getFirstName());
			statement.setString(2, entity.getLastName());
			statement.setString(3, entity.getContractNumber());
			statement.setDouble(4, entity.getSalary());
			statement.executeUpdate();
			connection.commit();
			log.info("Record created.");
		} catch (SQLException e) {
			log.error(e.getMessage());
		}

	}

	@Override
	public Optional<Mentor> read(String contractNumber) {
		String query = "SELECT	first_name, last_name, salary  FROM mentors WHERE contract_number=?";
		Optional<Mentor> result = Optional.empty();
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, contractNumber);
			ResultSet rs = statement.executeQuery();
			if (rs.isBeforeFirst()) {
				rs.next();
				Mentor.builder().contractNumber(contractNumber).firstName(rs.getString("first_name"))
						.lastName(rs.getString("last_name")).salary(rs.getDouble("salary")).build();
				log.info("Record read.");
			} else {
				log.info("Not present.");
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
		return result;
	}

	@Override
	public void update() {

	}

	@Override
	public void delete(String contractNumber) {
		String query = "DELETE	FROM mentors WHERE contract_number=?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
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
		try (Statement statement = connection.createStatement()) {
			ResultSet rs = statement.executeQuery(query);
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











