package org.zerock.controller;

import com.google.gson.Gson;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.zerock.domain.Ticket;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml",
        "file:src/main/webapp/WEB-INF/spring/root-context.xml"}) // 두 개의 xml 파일을 연결
@Log4j
public class SampleControllerTests {

    // 테스트를 위한 브라우저 기능
    @Setter(onMethod_ = @Autowired)
    private WebApplicationContext ctx;

    // 목업을 통해 테스트
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
    }

    @Test
    public void testConvert() throws Exception{
        Ticket ticket = new Ticket();
        ticket.setTno(1234);
        ticket.setOwner("Spider-Man");
        ticket.setGrade("vvip");

        String jsonStr = new Gson().toJson(ticket);

        log.info(jsonStr);

        mockMvc.perform(post("/sample/ticket").contentType(MediaType.APPLICATION_JSON).content(jsonStr)).andExpect(status().is(200));
    }
}
