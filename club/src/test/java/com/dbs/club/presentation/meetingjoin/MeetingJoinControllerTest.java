package com.dbs.club.presentation.meetingjoin;

import com.dbs.club.domain.meetingjoin.MeetingJoinService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = MeetingJoinController.class)
public class MeetingJoinControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MeetingJoinService meetingJoinService;

    @Test
    public void createMeetingJoin_성공() throws Exception {

        MeetingJoinRequestDto.Create requestDto = new MeetingJoinRequestDto.Create(1L, 1L);


        mockMvc.perform(post("/api/meeting-joins")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"memberId\": 1, \"meetingId\": 1}"))
                .andExpect(status().isCreated());
    }
}
