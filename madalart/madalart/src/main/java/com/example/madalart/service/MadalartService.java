package com.example.madalart.service;

import com.example.madalart.dto.DetailDto;
import com.example.madalart.dto.MainTopicDto;
import com.example.madalart.model.Detail;
import com.example.madalart.model.MainTopic;
import com.example.madalart.respository.DetailRepository;
import com.example.madalart.respository.MainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MadalartService {

    private final MainRepository mainRepository;
    private final DetailRepository detailRepository;

    @Autowired
    public MadalartService(MainRepository mainRepository, DetailRepository detailRepository) {
        this.mainRepository = mainRepository;
        this.detailRepository = detailRepository;
    }

    public MainTopicDto saveMainTopics(MainTopicDto mainTopicDto) {

        MainTopic mainTopic = new MainTopic();
        mainTopic.setTitle(mainTopicDto.getTitle());



        mainTopic = mainRepository.save(mainTopic);


        MainTopicDto savedDto = new MainTopicDto();
        savedDto.setId(mainTopic.getId());
        savedDto.setTitle(mainTopic.getTitle());


        return savedDto;
    }

    public List<MainTopicDto> getAllMainTopics(){
        List<MainTopic> mainTopics = mainRepository.findAll();


        List<MainTopicDto> mainTopicDtos = mainTopics.stream()
                .map(this:: convertEntityToDto)
                .collect(Collectors.toList());

        return mainTopicDtos;
    }

    private MainTopicDto convertEntityToDto(MainTopic mainTopic) {
        MainTopicDto mainTopicDto = new MainTopicDto();
        mainTopicDto.setId(mainTopic.getId());
        mainTopicDto.setTitle(mainTopic.getTitle());

        return mainTopicDto;
    }

    public MainTopicDto getMainTopicWithSubTopics(Long topicId) {
        Optional<MainTopic> mainTopicOpt = mainRepository.findById(topicId);

        if(mainTopicOpt.isPresent()) {
            MainTopic mainTopic = mainTopicOpt.get();


            List<Detail> details = detailRepository.findByMainTopicId(mainTopic.getId());


            MainTopicDto mainTopicDto = convertEntityToDto(mainTopic);


            List<DetailDto> detailDtos = details.stream()
                    .map(this::convertDetailEntityToDto)
                    .collect(Collectors.toList());
            mainTopicDto.setDetails(detailDtos);

            return mainTopicDto;

        } else {
            return null;
        }
    }

    private DetailDto convertDetailEntityToDto(Detail detail) {
        DetailDto detailDto = new DetailDto();
        detailDto.setId(detail.getId());
        detailDto.setTitle(detail.getTitle());
        detailDto.setmainTopicId(detail.getmainTopicId());

        return detailDto;
    }



    public DetailDto saveDetail(DetailDto detailDto) {

        Detail detail = new Detail();
        detail.setContent(detailDto.getContent());
        detail.setMainTopicId(detailDto.getMainTopicId());


        detail = detailRepository.save(detail);

        
        DetailDto savedDetailDto = new DetailDto();
        savedDetailDto.setId(detail.getId());
        savedDetailDto.setContent(detail.getcontent());
        savedDetailDto.setMainTopicId(detail.getmainTopicId());


        return savedDetailDto;

    }

}

