package com.swig.manda.repository;


import com.swig.manda.model.MainTopic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// MainRepository.java
public interface MainRepository extends JpaRepository<MainTopic, Long> {
    List<MainTopic> findByMember_Id(Long memberId);
    Optional<MainTopic> findByIdAndMember_Id(Long id, Long memberId);

}
