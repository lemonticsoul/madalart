package com.example.madalart.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class MainTopicDto {
    private Long id;
    private String title;
    private List<DetailDto> details;
}
