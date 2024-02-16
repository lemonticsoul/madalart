package com.example.madalart.service;

import com.example.madalart.dto.DetailDto;
import com.example.madalart.dto.MainTopicDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MadalartService {

    //여기 안에 repository를 불러와서 데이터베이스에서 조작해야함 !!
    public MainTopicDto saveMainTopics(MainTopicDto mainTopicDto){
        //메인 토픽을 데이터베이스에 저장하는 로직
    }

    public List<MainTopicDto> getAllMainTopics(){
        //여긴 모든 메인 토픽 조회
    }

    public MainTopicDto getMainTopicWithSubTopics(Long topicId) {
        // 특정 메인 토픽 한개와 그 딸린 자식들 정보 조회
    }

    public DetailDto saveDetail(DetailDto detailDto) {
        // 특정 정보 저장 즉 상세페이지에서 쓰일 저장
    }
}
