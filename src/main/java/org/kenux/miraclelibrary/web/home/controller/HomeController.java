package org.kenux.miraclelibrary.web.home.controller;

import lombok.RequiredArgsConstructor;
import org.kenux.miraclelibrary.web.book.controller.dto.response.NewBookResponse;
import org.kenux.miraclelibrary.domain.book.service.BookService;
import org.kenux.miraclelibrary.web.notice.response.NoticeResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class HomeController {

    private final BookService bookService;

    @GetMapping
    public String home(Model model) {

        // TODO : 임시 코드 삭제 예정   - sky 2022/03/01
        List<NoticeResponse> notices = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            long number = i + 1;
            NoticeResponse notice = new NoticeResponse(
                    number, "공지 사항 1..." + number, "윤상규", "내용", LocalDateTime.now());
            notices.add(notice);
        }
        model.addAttribute("notices", notices);

        List<NewBookResponse> newBooks = bookService.getNewBooks();
        model.addAttribute("newBooks", newBooks);
        return "views/home/main";
    }

    // TODO : member domain 으로 이동   - sky 2022/03/01
    @GetMapping("/members/{id}")
    public String myInfoTest(@PathVariable Long id, Model model) {
        model.addAttribute("name", "kenux");
        return "views/members/member-info";
    }
}
