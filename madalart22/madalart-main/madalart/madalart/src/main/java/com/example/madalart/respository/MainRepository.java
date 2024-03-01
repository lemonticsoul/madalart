package com.example.madalart.respository;

import com.example.madalart.model.MainTopic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainRepository extends JpaRepository<MainTopic, Long> {
}