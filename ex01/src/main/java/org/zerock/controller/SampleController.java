package org.zerock.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.zerock.domain.SampleDTO;

@Controller
@RequestMapping("/sample/*")
@Log4j
public class SampleController {

    @RequestMapping("")
    public void basic() {
        log.info("basic======");
    }

    @GetMapping("/basicOnlyGet")
    public void basic2() {
        log.info("basicOnlyGet=====");
    }

    @GetMapping("/ex01")
    public String ex01(SampleDTO dto) { // 매개변수 = 커맨드 객체
        // 파라미터 값을 읽어 스프링 컨테이너에서 클래스 타입의 매개변수 객체를 생성함
        log.info("dto : " + dto);

        return "ex01";
    }

    @GetMapping("/ex02")
    public String ex02(@RequestParam("name") String name, @RequestParam("age") int age) {
        log.info("name : " + name);
        log.info("age : " + age);

        return "ex02"; // jsp 파일명
    }

    @GetMapping("/ex04")
    public String ex04(SampleDTO dto, @ModelAttribute("page") int page) { // 커맨드 객체 : 요청 정보를 받는 역할. 뷰화면에서 참조 가능
        log.info("dto : " + dto);
        log.info("page : " + page);

        return "/sample/ex04"; // view 정보
        // forward로 이동
    }

    @GetMapping("/ex05")
    public void ex05() { // return 값이 없을 경우 지정한 url 주소가 view 정보가 된다 - ex05.jsp
        log.info("ex05...");
    }

    @GetMapping("/ex06")
    public @ResponseBody SampleDTO ex06() {
        log.info("ex06..");

        SampleDTO dto = new SampleDTO();
        dto.setAge(20);
        dto.setName("kim");

        return dto;
    }

    @GetMapping("/ex07")
    public ResponseEntity<String> ex07() {

        log.info("ex07..");

        String msg = "{\"name\" : \"kim\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json;charset=UTF-8");

        return new ResponseEntity<>(msg, headers, HttpStatus.OK);
    }
}

// dto 커맨드 객체 특징
// 1. 요청 정보를 받는다
// 2. 요청 정보를 request 영역에 저장한다. 속성명 = 클래스명(첫글자 소문자)
// 3. 뷰 화면에서 사용한다
// 4. @ModelAttribute("속성명") - 저장시 속성명 지정 가능

// let dog = {k:v, k:v, k:v...}