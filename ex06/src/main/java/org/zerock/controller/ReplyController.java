package org.zerock.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;
import org.zerock.service.ReplyService;

import java.util.List;

@RestController
@RequestMapping("/replies")
@Log4j
@AllArgsConstructor
public class ReplyController {

    // Rest 방식의 데이터 처리는 브라우저나 외부에서 서버를 호출할 때의 데이터의 포맷과 서버에서 보내지는 데이터의 타입을 명확하히 설계해야 한다.

    // 등록   : /replies/new  - post
    // 조회   : /replies/:rno - get
    // 삭제   : /replies/:rno - delete
    // 수정   : /replies/:rno - put | patch
    // 페이지 : /replies/pages/:bno/:page - get

    private ReplyService service;

    @PostMapping(value="/new", consumes="application/json", produces={MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> create(@RequestBody ReplyVO reply) {

        log.info("reply : " + reply);

        int insertCount = service.register(reply);
        log.info("insertCount : " + insertCount);

        return insertCount == 1? new ResponseEntity<>("success", HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value="/{rno}", produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<ReplyVO> read(@PathVariable("rno") Long rno){

        log.info("rno : " + rno);

        return new ResponseEntity<>(service.get(rno), HttpStatus.OK);
    }

    @GetMapping(value="/pages/{bno}/{page}", produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<List<ReplyVO>> getList(@PathVariable("bno") Long bno, @PathVariable("page") int page){
        log.info("bno : " + bno + ", page : " + page);

        Criteria cri = new Criteria(page, 10);

        return new ResponseEntity<>(service.getList(cri, bno), HttpStatus.OK);
    }

    @DeleteMapping(value="/{rno}", produces={MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> delete(@PathVariable("rno") Long rno) {
        log.info("rno : " + rno);

        return service.remove(rno) ? new ResponseEntity<>("success", HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(value="/{rno}", consumes="application/json", produces={MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> update(@PathVariable("rno") Long rno, @RequestBody ReplyVO reply) {
        log.info("rno : " + rno);
        reply.setRno(rno);
        log.info("reply : " + reply);

        return service.modify(reply) ? new ResponseEntity<>("success", HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
