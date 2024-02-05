
CREATE TABLE IF NOT EXISTS mentors ( 
	id BIGINT NOT NULL AUTO_INCREMENT,
	first_name 	VARCHAR(25) NOT NULL,
	last_name VARCHAR(25) NOT NULL,
	salary REAL NOT NULL CHECK(salary>0),
	contract_number VARCHAR(25) NOT NULL UNIQUE,
	PRIMARY KEY(id)
);


CREATE TABLE IF NOT EXISTS students (
	id BIGINT NOT NULL AUTO_INCREMENT,
	first_name 	VARCHAR(25) NOT NULL,
	last_name VARCHAR(25) NOT NULL,
	age INT NOT NULL CHECK(age>18 AND age<100),
	sex INT NOT NULL CHECK(sex=0 OR sex=1),
	contract_number VARCHAR(25) NOT NULL UNIQUE,
	academic_performance REAL NOT NULL CHECK(academic_performance>0 AND academic_performance<=5),
	mentor_id BIGINT NOT NULL,
	PRIMARY KEY(id),
	CONSTRAINT fk_mentor
	  FOREIGN KEY(mentor_id)
	    REFERENCES mentors(id)
);


