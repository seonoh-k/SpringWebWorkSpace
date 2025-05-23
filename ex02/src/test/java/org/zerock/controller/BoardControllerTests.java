package org.zerock.controller;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml",
"file:src/main/webapp/WEB-INF/spring/root-context.xml"}) // 두 개의 xml 파일을 연결
@Log4j
public class BoardControllerTests {

    // 테스트를 위한 브라우저 기능
    @Setter(onMethod_ = @Autowired)
    private WebApplicationContext ctx;

    // 목업을 통해 테스트
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
    }

//    @Test
//    public void testList() throws Exception {
//        log.info(mockMvc.perform(MockMvcRequestBuilders.get("/board/list")).andReturn().getModelAndView().getModelMap());
//    }

    @Test
    public void testList() throws Exception {
        log.info(mockMvc.perform(MockMvcRequestBuilders.get("/board/list")).andReturn().getModelAndView().getModelMap());
    }

    @Test
    public void testRegist() throws Exception {
        String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/register")
                .param("title","Test New Board")
                .param("content", "New Board").param("writer", "NewBoard"))
                .andReturn()
                .getModelAndView()
                .getViewName();

        log.info(resultPage);
    }

    @Test
    public void testGet() throws Exception {
        log.info(mockMvc.perform(MockMvcRequestBuilders.get("/board/get").param("bno", "10"))
                .andReturn().getModelAndView().getModelMap());
    }

    @Test
    public void testModify() throws Exception {
        String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/modify").param("bno", "10")
                .param("title", "바꾼 데이터").param("content", "바꾼 데이터").param("writer", "바꾼 데이터"))
                .andReturn().getModelAndView().getViewName();

        log.info(resultPage);
    }

    @Test
    public void testRemove() throws Exception {
        String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/remove").param("bno", "10"))
                .andReturn().getModelAndView().getViewName();

        log.info(resultPage);
    }
}
