package com.rehic.members;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MemberRepository extends MongoRepository<Member, String> {

}
