package com.rehic.auth;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends MongoRepository<MyUser, Long> {
  Optional <MyUser> findByUsername(String username);
}
