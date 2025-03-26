package org.zerock.mapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;

import java.util.List;
import java.util.stream.IntStream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml",
        "file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@Log4j
public class ReplyMapperTests {

    @Setter(onMethod_ = @__({@Autowired}))
    private ReplyMapper mapper;

    private Long[] bnoArr = {119L, 120L, 121L, 122L, 123L};

    @Test
    public void testMapper() {
        log.info(mapper);
    }

    @Test
    public void testInsert() {

        IntStream.rangeClosed(1,10).forEach(i -> {
            ReplyVO reply = new ReplyVO();
            reply.setBno(bnoArr[i%5]);
            reply.setReply("느금마 고려장"+i);
            reply.setReplyer("스파이더맨"+i);

            log.info("insert : " + reply);
            mapper.insert(reply);
        });

    }

    @Test
    public void testGetReply() {
        log.info(mapper.read(21L));
    }

    @Test
    public void testGetList() {
        List<ReplyVO> replyList = mapper.getList();

        replyList.forEach(log::info);
    }

    @Test
    public void testGetListWithPaging() {
        Criteria cri = new Criteria();
        cri.setPageNum(1);
        cri.setAmount(10);

        mapper.getListWithPaging(cri, bnoArr[1]).forEach(log::info);
    }

    @Test
    public void testDelete() {
        int result = mapper.delete(21L);

        log.info(result);
    }

    @Test
    public void testUpdate() {
        ReplyVO reply = new ReplyVO();
        reply.setRno(16L);
        reply.setReply("그렇게 하시면 안되죠");
        reply.setReplyer("아이언 니거");

        int result = mapper.update(reply);

        log.info(result);
    }
}
