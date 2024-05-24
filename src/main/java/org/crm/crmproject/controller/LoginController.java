package org.crm.crmproject.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
public class LoginController {

    @GetMapping("/login")
    public void login(String error, String logout) {
        log.info("로그인을 한다");
        log.info("로그아웃 : " + logout);

        if (logout != null) {
            log.info("로그아웃을 한다");
        }
    }
}
