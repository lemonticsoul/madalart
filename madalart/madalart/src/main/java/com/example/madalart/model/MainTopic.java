package com.example.madalart.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity

public class MainTopic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;


    @OneToMany(mappedBy = "mainTopic", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Detail> details;

}
