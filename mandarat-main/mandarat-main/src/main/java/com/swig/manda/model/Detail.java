package com.swig.manda.model;

import jakarta.persistence.*;




@Entity
public class Detail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @ManyToOne
    @JoinColumn(name = "main_topic_id")
    private MainTopic mainTopic;

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = content;
    }


    public MainTopic getMainTopic() {
        return mainTopic;
    }


    public void setMainTopic(MainTopic mainTopic) {
        this.mainTopic = mainTopic;
    }



}

