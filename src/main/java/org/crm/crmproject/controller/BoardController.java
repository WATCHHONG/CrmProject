package org.crm.crmproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.crm.crmproject.dto.BoardDTO;
import org.crm.crmproject.service.BoardService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Log4j2
@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/test")
    public void testBoard(Model model, Long bno) {
        log.info("테스트 게시판 들어간다");

        BoardDTO boardDTO = boardService.findById(bno);

        model.addAttribute("boardDTO", boardDTO);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/register")
    public void registerGet() {
        log.info("테스트 게시판 register 페이지");
    }

    @PostMapping("/register")
    public String registerPost(BoardDTO boardDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {

        log.info("board POST register.......");

        // 등록 작업
        log.info(boardDTO);

        Long bno = boardService.register(boardDTO);
        redirectAttributes.addFlashAttribute("result", bno);

        return "redirect:/board/test";
    }

    //    @GetMapping("/list")
//    public void goBoard(){
//        log.info("------------list에 접속-------------");
//
//    }
//
}