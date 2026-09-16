package com.wisdomtechinc.drwisetodospringboot.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.wisdomtechinc.drwisetodospringboot.models.AppUser;

public interface UserRepository extends CrudRepository<AppUser, Long> {

	Optional<AppUser> findByUsername(String username);

}
