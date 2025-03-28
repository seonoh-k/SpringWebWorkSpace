package org.zerock.domain;

import lombok.Data;

@Data
public class Criteria { // 페이지 정보를 관리하는 클래스
    private int pageNum; // 페이지 번호
    private int amount; // 글 전체 개수
    private String type;
    private String keyword;

    public Criteria() {
        this(1,10);
    }

    public Criteria(int pageNum, int amount) {
        this.pageNum = pageNum;
        this.amount = amount;
    }

    public String[] getTypeArr() {
        return type == null ? new String[]{} : type.split("");
    }
}
