package org.crm.crmproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.crm.crmproject.dto.CustomerJoinDTO;
import org.crm.crmproject.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/customer")
@Log4j2
@RequiredArgsConstructor
public class CustomerController {

    private final MemberService memberService;

    //  고객 회원가입
    @GetMapping("/join")
    public void customerJoinGet() {
        log.info("----- 고객이 가입하다 겟방식 -----");
    }

    @PostMapping("/join")
    public String customerJoinPost(CustomerJoinDTO customerJoinDTO, RedirectAttributes redirectAttributes) {

        log.info("----- 고객이 가입하다 포스트방식 -----");
        log.info(customerJoinDTO);

        try {
            memberService.customerJoin(customerJoinDTO);
        } catch (MemberService.MidExistException e) {
            redirectAttributes.addFlashAttribute("error", "customerID");
            return "redirect:/customer/join";
        }
        redirectAttributes.addAttribute("result", "success");
        return "redirect:/customer/login";
    }

}


