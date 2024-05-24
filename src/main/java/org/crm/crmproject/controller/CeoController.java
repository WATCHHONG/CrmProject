package org.crm.crmproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.crm.crmproject.dto.CeoDTO;
import org.crm.crmproject.repository.CeoRepository;
import org.crm.crmproject.service.MemberService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ceo")
@Log4j2
@RequiredArgsConstructor
public class CeoController {

    private final MemberService memberService;
    private final CeoRepository ceoRepository;
    private final PasswordEncoder passwordEncoder;

    //  사장 회원가입
    @GetMapping("/join")
    public void ceoJoinGet() {
        log.info("----- 사장이 가입하다 겟방식 -----");
    }

    @PostMapping("/join")
    public String ceoJoinPost(CeoDTO ceoDTO, RedirectAttributes redirectAttributes) {

        log.info("----- 고객이 가입하다 포스트방식 -----");
        log.info(ceoDTO);

        try {
            memberService.ceoJoin(ceoDTO);
        } catch (MemberService.MidExistException e) {
            redirectAttributes.addFlashAttribute("error", "ceoID");
            return "redirect:/ceo/join";
        }
        redirectAttributes.addAttribute("result", "success");
        return "redirect:/login";
    }

    @GetMapping("/update")
    public void ceoUpdateGet(){
        log.info("----- 사장 업데이트 겟또! -----");

    }

    @PostMapping("/update")
    public String ceoUpdatePost(@Valid CeoDTO ceoDTO, RedirectAttributes redirectAttributes) {
        log.info("----- 사장 업데이트 포스트! -----" + ceoDTO);

        try {
            ceoRepository.updateCeo(passwordEncoder.encode(ceoDTO.getCeoPw()), ceoDTO.getCeoName(), ceoDTO.getCeoEmail(),
                    ceoDTO.getCeoPhone(), ceoDTO.getStoreAddress(), ceoDTO.getCeoId());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "cid");
            return "redirect:/ceo/update";
        }
        redirectAttributes.addAttribute("result", "success");
        return "redirect:/ceo/login";
    }
}








