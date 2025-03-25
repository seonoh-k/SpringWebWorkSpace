package org.zerock.mapper;

import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

import java.util.List;

public interface BoardMapper {

    public List<BoardVO> getList();

    public void insert(BoardVO boardVO);

    public Integer insertSelectKey(BoardVO boardVO);

    public BoardVO read(Long bno);

    public int delete(Long bno);

    public int update(BoardVO boardVO);

    public List<BoardVO> getListWithPaging(Criteria cri);

    public int getTotalCount(Criteria cri);
}
