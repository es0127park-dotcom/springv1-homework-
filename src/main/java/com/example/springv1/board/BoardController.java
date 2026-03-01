package com.example.springv1.board;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/boards/save-form")
    public String saveForm() {
        return "board/save-form";
    }

    @GetMapping("/boards/1")
    public String detail() {
        return "board/detail";
    }

    @GetMapping("/boards/update-form")
    public String updateForm() {
        return "board/update-form";
    }
}
