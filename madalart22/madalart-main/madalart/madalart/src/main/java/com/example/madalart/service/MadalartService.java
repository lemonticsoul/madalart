package com.example.madalart.service;

import com.example.madalart.dto.DetailDto;
import com.example.madalart.dto.MainTopicDto;
import com.example.madalart.model.Detail;
import com.example.madalart.model.MainTopic;
import com.example.madalart.respository.DetailRepository;
import com.example.madalart.respository.MainRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MadalartService {

    private final MainRepository mainRepository;
    private final DetailRepository detailRepository;

    @Autowired
    public MadalartService(MainRepository mainRepository, DetailRepository detailRepository) {
        this.mainRepository = mainRepository;
        this.detailRepository = detailRepository;
    }

    @Transactional
    public MainTopicDto saveMainTopics(MainTopicDto mainTopicDto) {

        MainTopic mainTopic = new MainTopic();
        mainTopic.setTitle(mainTopicDto.getTitle());


        MainTopic savedMainTopic = mainRepository.save(mainTopic);

        System.out.println(savedMainTopic.getTitle());

        MainTopicDto savedDto = new MainTopicDto();

        savedDto.setId(savedMainTopic.getId());
        savedDto.setTitle(savedMainTopic.getTitle());


        List<DetailDto> detailDtos = savedMainTopic.getDetails().stream()
                .map(this::convertDetailEntityToDto)
                .collect(Collectors.toList());
        savedDto.setDetails(detailDtos);


        System.out.println(savedDto.getId());
        System.out.println(savedDto.getTitle());


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
        List<DetailDto> detailDtos = mainTopic.getDetails().stream()
                .map(this::convertDetailEntityToDto)
                .collect(Collectors.toList());
        mainTopicDto.setDetails(detailDtos);


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
        detailDto.setContent(detail.getContent());
        detailDto.setMainTopicId(detail.getMainTopic().getId());
        detailDto.setMainTopicTitle(detail.getMainTopic().getTitle());
        return detailDto;


    }



    public  DetailDto saveDetail(DetailDto detailDto) {

        MainTopic mainTopic = mainRepository.findById(detailDto.getMainTopicId())
                .orElseThrow(() -> new RuntimeException("MainTopic not found with id: " + detailDto.getMainTopicId()));


        Detail detail = new Detail();
        detail.setContent(detailDto.getContent());
        detail.setMainTopic(mainTopic); // 여기서 MainTopic 설정


        Detail savedDetail = detailRepository.save(detail);


        DetailDto savedDetailDto = new DetailDto();
        savedDetailDto.setId(savedDetail.getId());
        savedDetailDto.setContent(savedDetail.getContent());
        savedDetailDto.setMainTopicId(savedDetail.getMainTopic().getId());
        savedDetailDto.setMainTopicTitle(savedDetail.getMainTopic().getTitle());

        return savedDetailDto;
    }
    public void deleteAllMainTopics() {
        mainRepository.deleteAll();
    }

    public void deleteAlldetail(){
        detailRepository.deleteAll();
    }

}

