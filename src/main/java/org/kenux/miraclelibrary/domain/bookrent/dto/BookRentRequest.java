package org.kenux.miraclelibrary.domain.bookrent.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@Getter
public class BookRentRequest {

    @NotNull
    private Long memberId;

    @NotEmpty
    private List<Long> bookIds;
}
