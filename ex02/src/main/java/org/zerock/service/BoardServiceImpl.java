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

        log.info("get... " + bno);

        return mapper.read(bno);
    }

    @Override
    public boolean modify(BoardVO board) {

        log.info("modify..." + board);

        return mapper.update(board) == 1;
    }

    @Override
    public boolean remove(Long bno) {

        log.info("remove" + bno);

        return mapper.delete(bno) == 1;
    }

    @Override
    public List<BoardVO> getList() {

        log.info("get list..");

        return mapper.getList();

    }
}
