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
import com.gladikov.crud.model.Sex;
import com.gladikov.crud.model.Student;
import com.gladikov.crud.util.ResourceProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class StudentRepository implements CrudRepository<Student> {
	private final ResourceProvider ds;
	@Override
	public void add(Student entity) throws DaoException {
		String query = """
				INSERT INTO students (first_name, last_name, contract_number, age, sex, academic_performance, mentor_id) VALUES (?, ?, ?, ?, ?, ?, ?)
						""";
		try (			
				Connection connection = ds.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);
			){
				statement.setString(1, entity.getFirstName());
				statement.setString(2, entity.getLastName());
				statement.setString(3, entity.getContractNumber());
				statement.setInt(4, entity.getAge());
				statement.setInt(5, entity.getSex().ordinal());
				statement.setDouble(6, entity.getAcademicPerformance());
				statement.setLong(7, entity.getMentorId());
				statement.executeUpdate();
				log.info("Entity {} {} created.", entity.getFirstName(), entity.getLastName());
		} catch(SQLException e) {
			throw new DaoException(e);
		}

	}

	@Override
	public Optional<Student> getByContractNumber(String contractNumber) throws DaoException { // в этом репозитории небольшие замечания по стилю такие же как в MentorRepository, поэтому повторяться не буду
		String query = """
				SELECT	first_name, last_name, contract_number, age, sex, academic_performance, mentor_id  FROM students WHERE contract_number=?
						""";
		Optional<Student> result = Optional.empty();
		try(Connection connection = ds.getConnection() ) {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, contractNumber);
			ResultSet rs = statement.executeQuery();
			if (rs.isBeforeFirst()) {
				rs.next();
				Student student=Student.builder()
						.contractNumber(contractNumber)
						.firstName(rs.getString("first_name"))
						.lastName(rs.getString("last_name"))
						.academicPerformance(rs.getDouble("academic_performance"))
						.age(rs.getInt("age"))
						.sex(Sex.values()[rs.getInt("sex")])
						.mentorId(rs.getLong("mentor_id"))
						.build();
				result=Optional.ofNullable(student);
				log.info("Entity with {} succesfully found", contractNumber);
			} else {
				log.info("Entity with {} was not found", contractNumber);
				throw new NoSuchElementException("Requested entity does not exist");
			}
		} catch(SQLException | NoSuchElementException e) {
			throw new DaoException(e);
		}
		return result;
	}
	
	public List<Student> getByMentor(long mentorId) throws DaoException {
		String query = """
				SELECT	first_name, last_name, contract_number, age, sex, academic_performance, mentor_id FROM students WHERE mentor_id=?
						""";
		List<Student> result = new LinkedList<>();
		try(Connection connection = ds.getConnection() ) {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setLong(1, mentorId);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Student student=Student.builder()
						.contractNumber(rs.getString("contract_number"))
						.firstName(rs.getString("first_name"))
						.lastName(rs.getString("last_name"))
						.academicPerformance(rs.getDouble("academic_performance"))
						.age(rs.getInt("age"))
						.sex(Sex.values()[rs.getInt("sex")])
						.mentorId(rs.getLong("mentor_id"))
						.build();
				result.add(student);
			}
			log.info("{} entities was read.", result.size());
		} catch(SQLException | NoSuchElementException e) {
			throw new DaoException(e);
		}
		return result;
	}
	
	@Override
	public List<Student> getAll() throws DaoException {
		String query = "SELECT	*  FROM students";
		List<Student> result = new LinkedList<>();
		try (
				Connection connection = ds.getConnection();
				Statement statement = connection.createStatement();
				ResultSet rs = statement.executeQuery(query);
			) {
			while (rs.next()) {
				Student student=Student.builder() // вот за этот билд должен отвечать маппер. Туда отправляешь ResultSet и собираешь там Student. Ну либо RowMapper используешь
						.contractNumber(rs.getString("contract_number"))
						.firstName(rs.getString("first_name"))
						.lastName(rs.getString("last_name"))
						.academicPerformance(rs.getDouble("academic_performance"))
						.age(rs.getInt("age"))
						.sex(Sex.values()[rs.getInt("sex")])
						.mentorId(rs.getLong("mentor_id"))
						.build();
				result.add(student);
			}
			log.info("{} entities was read.", result.size());
		} catch(SQLException e) {
			throw new DaoException(e);
		} 
		return result;
	}

	@Override
	public void update(Student entity) throws DaoException {
		String query = """
				UPDATE	students SET first_name=?, last_name=?, age=?, sex=?, academic_performance=?, mentor_id=?  WHERE contract_number=?
				""";
		try (
				Connection connection = ds.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);
			) {		statement.setString(1, entity.getFirstName());
					statement.setString(2, entity.getLastName());
					statement.setInt(3, entity.getAge());
					statement.setInt(4, entity.getSex().ordinal());
					statement.setDouble(5, entity.getAcademicPerformance());
					statement.setLong(6, entity.getMentorId());
					statement.setString(7, entity.getContractNumber());
					statement.executeUpdate();
					int row = statement.executeUpdate();
					log.info("{} entity updated",row);
				}catch(SQLException e) {
					throw new DaoException(e);
				} 
	}

	@Override
	public void delete(String contractNumber) throws DaoException {
		String query = "DELETE	FROM students WHERE contract_number=?";
		try (
				Connection connection = ds.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)
			){
			statement.setString(1, contractNumber);
			int row = statement.executeUpdate();
			log.info("Entity with contract #{} was deleted", contractNumber);
		}catch(SQLException e) {
			throw new DaoException(e);
		} 
	}

}











