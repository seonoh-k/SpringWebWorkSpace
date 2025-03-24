package org.zerock.mapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardMapperTests {

    @Setter(onMethod_ = @__({@Autowired}))
    private BoardMapper mapper;

    @Test
    public void testGetList() {
        mapper.getList().forEach(board -> log.info(board));
    }

    @Test
    public void testInsert() {
        BoardVO bVO = new BoardVO();
        bVO.setTitle("새로 작성한 데이터");
        bVO.setContent("새로 작성한 데이터");
        bVO.setWriter("새로 작성한 데이터");

        mapper.insert(bVO);

        log.info(bVO);
    }

    @Test
    public void testInsertSelectKey() {
        BoardVO bVO = new BoardVO();
        bVO.setBno(7L);
        bVO.setTitle("또 작성한 데이터");
        bVO.setContent("또 작성한 데이터");
        bVO.setWriter("또 작성한 데이터");

        mapper.insertSelectKey(bVO);

        log.info(bVO);
    }

    @Test
    public void testRead() {
        BoardVO bVO = mapper.read(7L);

        log.info(bVO);
    }

    @Test
    public void testDelete() {
        long result = mapper.delete(7L);

        log.info(result);
    }

    @Test
    public void testUpdate() {
        BoardVO bVO = new BoardVO();
        bVO.setBno(21L);
        bVO.setTitle("바꾼 데이터");
        bVO.setContent("바꾼 데이터");
        bVO.setWriter("바꾼 데이터");

        mapper.update(bVO);

        log.info(bVO);
    }
}
