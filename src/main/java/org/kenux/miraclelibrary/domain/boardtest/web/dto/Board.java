package org.kenux.miraclelibrary.domain.boardtest.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Board {
    private int no;
    private String title;
    private String writer;
    private LocalDateTime updateTime;

    public Board(int no, String title, String writer) {
        this.no = no;
        this.title = title;
        this.writer = writer;
        this.updateTime = LocalDateTime.now();
    }
}
