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
        // DTO를 엔티티로 변환
        MainTopic mainTopic = new MainTopic();
        mainTopic.setTitle(mainTopicDto.getTitle());
        // mainTopic에 필요한 다른 필드도 여기에서 설정

        // 엔티티 저장
        mainTopic = mainRepository.save(mainTopic);

        // 저장된 엔티티를 DTO로 변환하여 반환
        MainTopicDto savedDto = new MainTopicDto();
        savedDto.setId(mainTopic.getId());
        savedDto.setTitle(mainTopic.getTitle());
        // savedDto에 필요한 다른 필드도 여기에서 설정

        return savedDto;
    }

    public List<MainTopicDto> getAllMainTopics(){
        List<MainTopic> mainTopics = mainRepository.findAll();

        // 엔티티를 DTO로 변환
        List<MainTopicDto> mainTopicDtos = mainTopics.stream()
                .map(this:: convertEntityToDto)
                .collect(Collectors.toList());

        return mainTopicDtos;
    }

    private MainTopicDto convertEntityToDto(MainTopic mainTopic) {
        MainTopicDto mainTopicDto = new MainTopicDto();
        mainTopicDto.setId(mainTopic.getId());
        mainTopicDto.setTitle(mainTopic.getTitle());
        // 여기에 필요한 다른 필드들을 설정
        return mainTopicDto;
    }

    public MainTopicDto getMainTopicWithSubTopics(Long topicId) {
        Optional<MainTopic> mainTopicOpt = mainRepository.findById(topicId);

        if(mainTopicOpt.isPresent()) {
            MainTopic mainTopic = mainTopicOpt.get();

            // 중앙 주제에 연관된 세부 정보 조회
            List<Detail> details = detailRepository.findByMainTopicId(mainTopic.getId());

            // 엔티티를 DTO로 변환
            MainTopicDto mainTopicDto = convertEntityToDto(mainTopic);

            // 세부 정보 DTO로 변환하여 MainTopicDto에 추가
            List<DetailDto> detailDtos = details.stream()
                    .map(this::convertDetailEntityToDto) // 세부 정보 엔티티를 DTO로 변환하는 메서드 필요
                    .collect(Collectors.toList());
            mainTopicDto.setDetails(detailDtos); // MainTopicDto에 세부 정보 설정

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

        // 데이터베이스에 저장
        detail = detailRepository.save(detail);

        // 저장된 엔티티를 DTO로 변환하여 반환
        DetailDto savedDetailDto = new DetailDto();
        savedDetailDto.setId(detail.getId());
        savedDetailDto.setContent(detail.getcontent());
        savedDetailDto.setMainTopicId(detail.getmainTopicId());


        return savedDetailDto;

    }

}

