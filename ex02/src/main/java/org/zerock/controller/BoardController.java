package org.zerock.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
import org.zerock.service.BoardService;

@Controller
@Log4j
@RequestMapping("/board/*")
@AllArgsConstructor
public class BoardController {

    // 생성자를 통한 자동 의존 주입
    BoardService service;

    @GetMapping("list") // dispatcher servlet을 통해 list.jsp로 이동
    public void list(Model model) {
        model.addAttribute("list", service.getList());
    }

    @GetMapping("/register")
    public void register() {

    }

    @PostMapping("/register")
    public String register(BoardVO board, RedirectAttributes rttr) {

        log.info("board : " + board);
        service.register(board);

        rttr.addFlashAttribute("result", board.getBno());

        // Controller는 기본 forward 방식으로 이동
        return "redirect:/board/list";
    }

    @GetMapping({"/get", "/modify"})
    public void get(@RequestParam("bno") Long bno, Model model) {
        log.info("/get");

        model.addAttribute("board", service.get(bno));
    }

    @PostMapping("/modify")
    public String modify(BoardVO board, RedirectAttributes rttr) {
        log.info("/modify");
        log.info("board : " + board);

        if(service.modify(board)) {
            rttr.addFlashAttribute("result", "success");
        }

        return "redirect:/board/list";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam("bno") Long bno, RedirectAttributes rttr) {
        log.info("/remove");

        if(service.remove(bno)) {
            rttr.addFlashAttribute("result", "success");
        }

        return "redirect:/board/list";
    }
}
