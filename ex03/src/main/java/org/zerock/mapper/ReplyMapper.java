package org.zerock.mapper;

import org.apache.ibatis.annotations.Param;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;

import java.util.List;

public interface ReplyMapper {

    public int insert(ReplyVO reply);

    public ReplyVO read(Long rno);

    public List<ReplyVO> getList();

    public List<ReplyVO> getListWithPaging(@Param("cri") Criteria cri, @Param("bno") Long bno);

    public int delete(Long rno);

    public int update(ReplyVO reply);
}
