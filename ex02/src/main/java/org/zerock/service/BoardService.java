package org.zerock.service;

import org.springframework.stereotype.Service;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

import java.util.List;

@Service
public interface BoardService {
    public void register(BoardVO boardVO);

    public BoardVO get(Long bno);

    public boolean modify(BoardVO boardVO);

    public boolean remove(Long bno);

    //public List<BoardVO> getList();

    public List<BoardVO> getListWithPaging(Criteria cri);

    public int getTotalCount(Criteria cri);
}
