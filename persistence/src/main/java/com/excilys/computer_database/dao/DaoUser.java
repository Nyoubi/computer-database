package com.excilys.computer_database.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computer_database.model.User;

@Repository
public interface DaoUser extends JpaRepository<User,Integer>{

	Optional<User> findByUsername(String name);

	@Transactional
	@Modifying
	@Query(value = "INSERT INTO user_role(idRole, username) VALUES (1, :username)", nativeQuery = true)
	void saveRole(@Param("username") String username);
	
}
