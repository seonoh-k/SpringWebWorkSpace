package org.zerock.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageDTO;
import org.zerock.service.BoardService;

@Controller
@Log4j
@RequestMapping("/board/*")
@AllArgsConstructor
public class BoardController {

    // 생성자를 통한 자동 의존 주입
    BoardService service;

//    @GetMapping("list") // dispatcher servlet을 통해 list.jsp로 이동
//    public void list(Model model) {
//        model.addAttribute("list", service.getList());
//    }

    @GetMapping("/list")
    public void list(Model model, Criteria cri) {
        log.info(cri);
        model.addAttribute("list", service.getListWithPaging(cri));

        int total = service.getTotalCount(cri);

        model.addAttribute("pageMaker", new PageDTO(cri, total));
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
    public void get(@RequestParam("bno") Long bno,@ModelAttribute("cri") Criteria cri, Model model) {
        log.info("/get");
        log.info(cri.getPageNum());
        log.info(cri.getAmount());

        model.addAttribute("board", service.get(bno));
        // command 객체는 스프링 컨테이너에서 객체를 생성하고 영역 객체에 저장해 전송한다.
    }

    @PostMapping("/modify")
    public String modify(BoardVO board, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
        log.info("/modify");
        log.info("board : " + board);

        if(service.modify(board)) {
            rttr.addFlashAttribute("result", "success");
        }

        rttr.addAttribute("pageNum", cri.getPageNum());
        rttr.addAttribute("amount", cri.getAmount());

        return "redirect:/board/list";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
        log.info("/remove");

        if(service.remove(bno)) {
            rttr.addFlashAttribute("result", "success");
        }

        rttr.addAttribute("pageNum", cri.getPageNum());
        rttr.addAttribute("amount", cri.getAmount());
        return "redirect:/board/list";
    }
}
