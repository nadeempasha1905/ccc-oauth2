/**
 * 
 */
package com.ccc.oauth2.controller;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ccc.oauth2.dto.User;

/**
 * @author Administrator
 *
 */
public interface UserRepository extends JpaRepository<User, Integer> {

	User findOneByUsername(String username);
	
}
