package org.crm.crmproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index"; // index.html 또는 다른 이름의 템플릿 파일 이름
    }
}