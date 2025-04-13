package org.example.booksy.repository;

import org.example.booksy.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends CrudRepository<User, Long> {
   Optional<User> findByEmail(String email);
}
