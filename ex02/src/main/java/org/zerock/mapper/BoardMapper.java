package org.zerock.mapper;

import org.zerock.domain.BoardVO;

import java.util.List;

public interface BoardMapper {

    public List<BoardVO> getList();

    public void insert(BoardVO boardVO);

    public Integer insertSelectKey(BoardVO boardVO);

    public BoardVO read(Long bno);

    public int delete(Long bno);

    public int update(BoardVO boardVO);
}
