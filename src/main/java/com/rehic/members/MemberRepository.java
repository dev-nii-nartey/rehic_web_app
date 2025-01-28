package com.rehic.members;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface MemberRepository extends MongoRepository<Member, String> {
    Page<Member> findAllByIsDeletedFalse(Pageable pageable);

    @Query("{'$and': [{'$or': [{'firstName': {$regex : ?0, $options: 'i'}}, {'lastName': {$regex : ?0, $options: 'i'}}]}, {'isDeleted': false}]}")
    Page<Member> searchByName(String name, Pageable pageable);
}