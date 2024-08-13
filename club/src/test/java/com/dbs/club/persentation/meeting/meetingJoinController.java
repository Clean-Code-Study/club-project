package com.example.meeting.controller;

import com.example.meeting.dto.MeetingRequestDto;
import com.example.meeting.service.MeetingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MeetingController.class)
public class MeetingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MeetingService meetingService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createMeeting() throws Exception {
        MeetingRequestDto.Create createDto = new MeetingRequestDto.Create(
                1L,
                "Sample Title",
                "Sample Content",
                "Sample Location",
                LocalDate.now(),
                10
        );

        Mockito.when(meetingService.createMeeting(any(MeetingRequestDto.Create.class))).thenReturn(1L);

        mockMvc.perform(post("/api/meetings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/meetings/1"));
    }
}
