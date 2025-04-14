package org.example.booksy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class a {
    @GetMapping("/hello")
    public String helloPage() {
        return "hello";
    }
}
