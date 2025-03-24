package org.zerock.service;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVO;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardServiceTests {

    @Setter(onMethod_ = @Autowired)
    private BoardService service;

    @Test
    public void testExist() {
        log.info(service);
        assertNotNull(service);
    }

    @Test
    public void testRegister() {
        BoardVO board = new BoardVO();
        board.setTitle("새로운 데이터");
        board.setContent("새로운 데이터");
        board.setWriter("새로운 데이터");

        service.register(board);

        log.info("생성된 게시물 번호 : " + board.getBno());
    }

    @Test
    public void testGetList() {
        service.getList().forEach(log::info);
    }

    @Test
    public void testRead() {
        log.info(service.get(9L));
    }

    @Test
    public void testUpdate() {

        BoardVO board = service.get(21L);

        if(board == null) {
            return;
        }else {
            board.setTitle("제목 수정");
            log.info("modify result : " + service.modify(board));
        }
    }

    @Test
    public void testRemove() {

        log.info(service.remove(9L));
    }
}
