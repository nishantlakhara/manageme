package com.websecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ArticleController {

    @RequestMapping("/articles")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="Articles") String name, Model model) {
        model.addAttribute("name", name);
        return "articles";
    }
}