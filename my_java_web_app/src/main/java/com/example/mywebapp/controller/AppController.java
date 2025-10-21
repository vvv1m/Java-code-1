package com.example.mywebapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    @GetMapping("/")
    public String getIndex(Model model) {
        // 可以在这里添加模型属性
        return "index"; // 返回视图名称
    }
}