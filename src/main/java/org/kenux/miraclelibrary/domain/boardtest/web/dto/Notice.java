package org.kenux.miraclelibrary.domain.boardtest.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Notice {
    private Long id;
    private String title;
    private String createdBy;
    private String content;
    private LocalDateTime createdTime;
}
