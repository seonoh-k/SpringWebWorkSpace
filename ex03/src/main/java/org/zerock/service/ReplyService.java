package org.zerock.service;

import org.springframework.stereotype.Service;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyPageDTO;
import org.zerock.domain.ReplyVO;

import java.util.List;

@Service
public interface ReplyService {
    public int register(ReplyVO reply);

    public ReplyVO get(Long rno);

    public boolean modify(ReplyVO reply);

    public boolean remove(Long rno);

    //public List<ReplyVO> getList();

    public List<ReplyVO> getList(Criteria cri, Long bno);

    public int getTotalCount(Criteria cri);

    public ReplyPageDTO getListPage(Criteria cri, Long bno);
}
