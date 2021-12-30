package org.kenux.miraclelibrary.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@Getter
public class BookRentRequestDto {

    @NotNull
    private Long memberId;

    @NotEmpty
    private List<Long> bookIds;
}
