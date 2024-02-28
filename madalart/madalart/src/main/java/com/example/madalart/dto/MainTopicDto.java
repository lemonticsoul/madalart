package com.example.madalart.dto;

import com.example.madalart.model.Detail;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


public class MainTopicDto {
    private Long id;
    private String title;
    private List<DetailDto> details;

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public List<DetailDto> getDetails() {
        return details;
    }


    public void setDetails(List<DetailDto> details) {
        this.details = details;
    }
}
