package org.kenux.miraclelibrary.domain.bookrent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@Data
public class BookReturnRequest {

    @NotNull
    private Long memberId;

    @NotEmpty
    private List<Long> books;
}
