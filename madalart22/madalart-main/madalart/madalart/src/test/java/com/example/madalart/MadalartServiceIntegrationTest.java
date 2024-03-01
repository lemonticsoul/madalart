package com.example.madalart;

import com.example.madalart.dto.DetailDto;
import com.example.madalart.dto.MainTopicDto;
import com.example.madalart.model.Detail;
import com.example.madalart.model.MainTopic;
import com.example.madalart.respository.DetailRepository;
import com.example.madalart.respository.MainRepository;
import com.example.madalart.service.MadalartService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest

public class MadalartServiceIntegrationTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private MadalartService madalartService;

    @Autowired
    private MainRepository mainRepository;

    @Autowired
    private DetailRepository detailRepository;
    @BeforeEach
    public void setUp() {
        // 선택적: 테스트 실행 전에 필요한 초기 설정을 수행
    }

    @AfterEach
    public void tearDown() {
        // 각 테스트 실행 후에 데이터베이스를 정리
        detailRepository.deleteAll();
        mainRepository.deleteAll();
    }




    @Test
    @Transactional
    public void testGetAllMainTopics() {
        MainTopic mainTopic1 = new MainTopic();
        mainTopic1.setTitle("Topic 1");
        mainRepository.save(mainTopic1);

        MainTopic mainTopic2 = new MainTopic();
        mainTopic2.setTitle("Topic 2");
        mainRepository.save(mainTopic2);

        List<MainTopicDto> mainTopicDtos = madalartService.getAllMainTopics();

        assertEquals(2, mainTopicDtos.size());
    }

    @Test
    @Transactional
    public void testSaveDetail() {
        MainTopic mainTopic = new MainTopic();
        mainTopic.setTitle("Main Topic for Detail");

        entityManager.persist(mainTopic);
        entityManager.flush();

        MainTopic savedMainTopic = entityManager.find(MainTopic.class, mainTopic.getId());

        DetailDto detailDto = new DetailDto();
        detailDto.setContent("Detail Content");
        detailDto.setMainTopicId(savedMainTopic.getId());

        DetailDto savedDetailDto = madalartService.saveDetail(detailDto);

        assertNotNull(savedDetailDto.getId());
        assertEquals("Detail Content", savedDetailDto.getContent());

        Detail foundDetail = entityManager.find(Detail.class, savedDetailDto.getId());
        assertEquals("Detail Content", foundDetail.getContent());
        assertEquals(savedMainTopic.getId(), foundDetail.getMainTopic().getId());
    }
}