package org.zerock.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyPageDTO;
import org.zerock.domain.ReplyVO;
import org.zerock.mapper.ReplyMapper;

import java.util.List;

@Service
@AllArgsConstructor
@Log4j
public class ReplyServiceImpl implements ReplyService{

    private ReplyMapper mapper;

    @Override
    public int register(ReplyVO reply) {
        log.info("register : " + reply);

        return mapper.insert(reply);
    }

    @Override
    public ReplyVO get(Long rno) {

        ReplyVO reply = mapper.read(rno);

        log.info(reply);

        return reply;
    }

    @Override
    public boolean modify(ReplyVO reply) {

        log.info("modify : " + reply);

        return mapper.update(reply) == 1;
    }

    @Override
    public boolean remove(Long rno) {

        log.info("remove : " + rno);

        return mapper.delete(rno) == 1;
    }

    @Override
    public List<ReplyVO> getList(Criteria cri, Long bno) {

        log.info("get reply list of a board by : " + bno);

        List<ReplyVO> replyList = mapper.getListWithPaging(cri, bno);

        replyList.forEach(log::info);

        return replyList;
    }

    @Override
    public int getTotalCount(Criteria cri) {
        return 0;
    }

    @Override
    public ReplyPageDTO getListPage(Criteria cri, Long bno) {
        return null;
    }
}
