package org.kenux.miraclelibrary.web.boardtest.controller;

import org.kenux.miraclelibrary.web.boardtest.dto.Board;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class BoardTestController {

//    @GetMapping
//    public String indexPage() {
//        return "dashboard";
//    }

    @GetMapping("/utilities-color")
    public String utilitiesColor() {
        return "utilities-color";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/board")
    public String books(Model model) {
        List<Board> boards = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int number = i + 1;
            Board board = new Board(number, "테스트 중..." + number, "윤상규");
            boards.add(board);
        }
        model.addAttribute("boardList", boards);
        return "board";
    }
}
