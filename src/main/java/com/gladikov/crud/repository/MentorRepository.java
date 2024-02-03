package com.gladikov.crud.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import com.gladikov.crud.model.Mentor;
import com.gladikov.crud.util.ResourceProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MentorRepository implements CrudRepository<Mentor> {
	private final DataSource ds;
	@Override
	public void add(Mentor entity) {
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
			log.info("Entity {} {} created.", entity.getFirstName(), entity.getLastName());
		} catch (SQLException e) {
			log.error(e.getMessage());
		}

	}

	@Override
	public Optional<Mentor> getByContractNumber(String contractNumber) {
		String query = "SELECT	first_name, last_name, salary  FROM mentors WHERE contract_number=?";
		Optional<Mentor> result = Optional.empty();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
				connection = ds.getConnection();
				statement = connection.prepareStatement(query);
				statement.setString(1, contractNumber);
				rs = statement.executeQuery();
			if (rs.isBeforeFirst()) {
				rs.next();
				Mentor mentor=Mentor.builder().contractNumber(contractNumber).firstName(rs.getString("first_name"))
						.lastName(rs.getString("last_name")).salary(rs.getDouble("salary")).build();
				result=Optional.ofNullable(mentor);
				log.info("Entity with {} succesfully found", contractNumber);
			} else {
				log.info("Entity with {} was not found", contractNumber);
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
		} finally {
			try {
				statement.close();
				rs.close();
				connection.close();
			} catch (SQLException e) {
				log.error(e.getMessage());
			}
		}
		return result;
	}

	@Override
	public void update(Mentor entity) {
		String query = "UPDATE	mentors SET first_name=?, last_name=?, salary=?  WHERE contract_number=?";
		try (
				Connection connection = ds.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);
			) {		statement.setString(1, entity.getFirstName());
					statement.setString(2, entity.getLastName());
					statement.setDouble(3, entity.getSalary());
					int row = statement.executeUpdate();
					log.info("? entity updated",row);
				} catch (SQLException e) {
					log.error(e.getMessage());
				}
	}

	@Override
	public void delete(Mentor entiry) {
		String query = "DELETE	FROM mentors WHERE contract_number=?";
		try (
				Connection connection = ds.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)
			) {
			statement.setString(1, entiry.getContractNumber());
			int row = statement.executeUpdate();
			log.info("Entity with contract #{} was deleted", entiry.getContractNumber());
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public List<Mentor> getAll() {
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
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
		return result;
	}

}











