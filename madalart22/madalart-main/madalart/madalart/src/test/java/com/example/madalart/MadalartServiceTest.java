package com.example.madalart;

import com.example.madalart.dto.MainTopicDto;
import com.example.madalart.model.Detail;
import com.example.madalart.model.MainTopic;
import com.example.madalart.respository.DetailRepository;
import com.example.madalart.respository.MainRepository;
import com.example.madalart.service.MadalartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@SpringBootTest

public class MadalartServiceTest {


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
    void saveMainTopics_ShouldSaveMainTopic() {

        Detail detail1 = new Detail();
        detail1.setContent("지호");
        Detail detail2 = new Detail();
        detail2.setContent("제호");
        Detail detail3 = new Detail();
        detail3.setContent("지윤");


        List<Detail> details = new ArrayList<>();
        details.add(detail1);
        details.add(detail2);
        details.add(detail3);


        MainTopic mainTopic = new MainTopic();
        mainTopic.setTitle("Sample Topic");
        mainTopic.setDetails(details);

        MainTopic savedMainTopic = new MainTopic();
        savedMainTopic.setId(1L);
        savedMainTopic.setTitle(mainTopic.getTitle());
        savedMainTopic.setDetails(mainTopic.getDetails());

        //when
        when(mainRepository.save(any(MainTopic.class))).thenReturn(savedMainTopic);

        MainTopicDto mainTopicDto = new MainTopicDto();
        mainTopicDto.setTitle("Sample Topic");

        MainTopicDto result = madalartService.saveMainTopics(mainTopicDto);



        assertNotNull(result.getId());
        assertEquals("Sample Topic", result.getTitle());
        assertEquals(3, result.getDetails().size());

        assertEquals("지호", result.getDetails().get(0).getContent());
        assertEquals("제호", result.getDetails().get(1).getContent());
        assertEquals("지윤", result.getDetails().get(2).getContent());
        verify(mainRepository, times(1)).save(any(MainTopic.class));
    }

    @Test
    void getAllMainTopics_ShouldReturnAllMainTopics() {
        List<MainTopic> mainTopics = new ArrayList<>();
        mainTopics.add(new MainTopic(1L, "Topic 1"));
        mainTopics.add(new MainTopic(2L, "Topic 2"));

        when(mainRepository.findAll()).thenReturn(mainTopics);

        List<MainTopicDto> result = madalartService.getAllMainTopics();

        assertEquals(2, result.size());
        assertEquals("Topic 1", result.get(0).getTitle());
        assertEquals("Topic 2", result.get(1).getTitle());

    }

}