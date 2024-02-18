package com.example.madalart.controller;

import com.example.madalart.dto.DetailDto;
import com.example.madalart.dto.MainTopicDto;
import com.example.madalart.respository.DetailRepository;
import com.example.madalart.respository.MainRepository;
import com.example.madalart.service.MadalartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/madalart")
public class MandalartController {

    private final MadalartService madalartService;

    @Autowired
    public MandalartController(MadalartService madalartService) {
        this.madalartService=madalartService;
    }

    //메인 토픽 조회
    @GetMapping("/main")
    public ResponseEntity<Object> getAllMainTopics() {
        List<MainTopicDto> mainTopics = madalartService.getAllMainTopics();
        return ResponseEntity.ok(mainTopics);
    }

    // 메인토픽하고 상세정보 조회
    @GetMapping("/main/{topicId}")
    public ResponseEntity<MainTopicDto> getMainTopicWithDetails(@PathVariable Long topicId) {
        MainTopicDto mainTopic = madalartService.getMainTopicWithSubTopics(topicId);
        return ResponseEntity.ok(mainTopic);
    }

    // 메인 토픽 저장
    @PostMapping("/main")
    public ResponseEntity<MainTopicDto> saveMainTopic(@RequestBody MainTopicDto mainTopicDto) {
        MainTopicDto savedTopic = madalartService.saveMainTopics(mainTopicDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTopic);
    }

    // 상세 정보 저장
    @PostMapping("/details")
    public ResponseEntity<DetailDto> saveDetail(@RequestBody DetailDto detailDto) {
        DetailDto savedDetail = madalartService.saveDetail(detailDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDetail);
    }
}
