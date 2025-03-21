package org.zerock.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.zerock.domain.BoardVO;
import org.zerock.mapper.BoardMapper;

import java.util.List;

@Service
@AllArgsConstructor
@Log4j
public class BoardServiceImpl implements BoardService{

    private BoardMapper mapper;
    @Override
    public void register(BoardVO board) {

        log.info("register..." + board);
        mapper.insertSelectKey(board);

    }

    @Override
    public BoardVO get(Long bno) {
        return null;
    }

    @Override
    public boolean modify(BoardVO boardVO) {
        return false;
    }

    @Override
    public boolean remove(Long bno) {
        return false;
    }

    @Override
    public List<BoardVO> getList() {
        return null;
    }
}
