package com.rehic.user;

import com.rehic.auth.MyUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends MongoRepository<MyUser, Long> {
  @Query("{ 'username' : ?0 }")
  Optional <MyUser> findByUsername(String username);
  boolean existsByUsername(String username);

}
