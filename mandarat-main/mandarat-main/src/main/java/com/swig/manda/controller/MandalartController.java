package com.swig.manda.controller;


import com.swig.manda.dto.DetailDto;
import com.swig.manda.dto.MainTopicDto;
import com.swig.manda.service.MadalartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;



@RestController
@RequestMapping("/members/{memberId}")
public class MandalartController {

    private final MadalartService madalartService;

    @Autowired
    public MandalartController(MadalartService madalartService) {
        this.madalartService=madalartService;
    }

    //메인 토픽 조회
    @GetMapping("/main")
    public ResponseEntity<Object> getAllMainTopics(@PathVariable Long memberId) {
        List<MainTopicDto> mainTopics = madalartService.getAllMainTopics(memberId);
        if (mainTopics == null) {
            mainTopics = new ArrayList<>();
        }
        return ResponseEntity.ok(mainTopics);
    }


    // 메인토픽하고 상세정보 조회
    @GetMapping("/main/{topicId}")
    public ResponseEntity<MainTopicDto> getMainTopicWithDetails(@PathVariable Long memberId, @PathVariable Long topicId) {
        MainTopicDto mainTopic = madalartService.getMainTopicWithSubTopicsByMemberId(topicId, memberId);
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

    @PutMapping("/main/{topicId}")
    public ResponseEntity<MainTopicDto> updateMainTopic(@PathVariable Long topicId, @RequestBody MainTopicDto mainTopicDto){
        MainTopicDto updatedTopic =madalartService.updateMainTopic(topicId,mainTopicDto);
        if (updatedTopic==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedTopic);
    }



    @PutMapping("/details/{detailId}")
    public ResponseEntity<DetailDto> updatedetail(@PathVariable Long detailId, @RequestBody DetailDto detailDto){
        DetailDto updatedDetail = madalartService.updateDetail(detailId, detailDto);
        if (updatedDetail == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedDetail);
    }

    @DeleteMapping("/main")
    public ResponseEntity<Void> deleteAllMainTopics() {
        madalartService.deleteAllMainTopics();
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/details")
    public ResponseEntity<Void> deleteAlldetail() {
        madalartService.deleteAlldetail();
        return ResponseEntity.ok().build();
    }
}
