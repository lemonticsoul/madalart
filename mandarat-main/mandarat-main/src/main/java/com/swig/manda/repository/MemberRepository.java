package com.swig.manda.repository;

import com.swig.manda.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;


@Transactional
public interface MemberRepository extends JpaRepository<Member,Long> {


    Member findByUsername(String username);
    Member findByEmail(String email);


    Boolean existsByUsername(String username);


    @Query("SELECT m.password FROM Member m WHERE m.username = :username")
    String findPasswordByUsername(@Param("username") String username);

    Optional<Member> findByEmailAndName(String email, String name);


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Member m SET m.password = :password WHERE m.username = :username")
    void updatePasswordByUsername(@Param("password") String password, @Param("username") String username);
}

