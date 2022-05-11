package org.kenux.miraclelibrary.domain.notice.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class NoticeResponse {
    private Long id;
    private String title;
    private String createdBy;
    private String content;
    private LocalDateTime createdTime;
}
