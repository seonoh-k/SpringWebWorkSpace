package org.zerock.domain;

import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class BoardVO {

    private long bno;
    private String title;
    private String content;
    private String writer;
    private Date regDate;
    private Date updateDate;

    private List<BoardAttachVO> attachList;
}
