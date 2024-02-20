package com.example.madalart.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Detail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String content;

    @ManyToOne
    @JoinColumn(name = "main_topic_id")
    private MainTopic mainTopic;



}

