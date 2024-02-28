package com.example.madalart;

import com.example.madalart.model.Detail;
import com.example.madalart.respository.DetailRepository;
import com.example.madalart.respository.MainRepository;
import com.example.madalart.service.MadalartService;
import com.sun.tools.javac.Main;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.example.madalart.model.Detail;
import com.example.madalart.model.MainTopic;

import com.example.madalart.dto.DetailDto;
import com.example.madalart.dto.MainTopicDto;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class DetailServiceTest {

    @Mock
    private MainRepository mainRepository;

    @Mock
    private DetailRepository detailRepository;


    private MadalartService madalartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.madalartService = new MadalartService(mainRepository,detailRepository);
    }

    @Test
    void testGetMainTopicWithSubTopics() {

        Long mainTopicId = 1L;
        MainTopic mainTopic = new MainTopic();
        mainTopic.setId(mainTopicId);
        mainTopic.setTitle("제호의 친구들");

        Detail detail1 = new Detail();
        detail1.setId(1L);
        detail1.setContent("제호");
        detail1.setMainTopic(mainTopic);

        Detail detail2 = new Detail();
        detail2.setId(2L);
        detail2.setContent("지윤");
        detail2.setMainTopic(mainTopic);

        Detail detail3 = new Detail();
        detail3.setId(3L);
        detail3.setContent("지호");
        detail3.setMainTopic(mainTopic);

        List<Detail> details = Arrays.asList(detail1, detail2, detail3);

        when(mainRepository.findById(mainTopicId)).thenReturn(Optional.of(mainTopic));
        when(detailRepository.findByMainTopicId(mainTopicId)).thenReturn(details);

        MainTopicDto mainTopicDto = madalartService.getMainTopicWithSubTopics(mainTopicId);

        assertEquals(mainTopic.getTitle(), mainTopicDto.getTitle());
        assertEquals(3, mainTopicDto.getDetails().size());
        assertEquals("제호", mainTopicDto.getDetails().get(0).getContent());
        assertEquals("지윤", mainTopicDto.getDetails().get(1).getContent());
        assertEquals("지호", mainTopicDto.getDetails().get(2).getContent());

        // Verify the title of the MainTopic is correctly set in DetailDtos
        mainTopicDto.getDetails().forEach(detailDto ->
                assertEquals("제호의 친구들", detailDto.getMainTopicTitle()));

        mainTopicDto.getDetails().forEach(detailDto -> {
            System.out.println("Detail ID: " + detailDto.getId());
            System.out.println("Content: " + detailDto.getContent());
            System.out.println("MainTopic ID: " + detailDto.getMainTopicId());
            System.out.println("MainTopic Title: " + detailDto.getMainTopicTitle());
            System.out.println("-----------------------------------");
        });


        verify(mainRepository, times(1)).findById(mainTopicId);
        verify(detailRepository, times(1)).findByMainTopicId(mainTopicId);
    }
}
