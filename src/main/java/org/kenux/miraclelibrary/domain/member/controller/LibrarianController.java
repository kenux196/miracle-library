package org.kenux.miraclelibrary.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.kenux.miraclelibrary.domain.member.dto.LibrarianJoinRequest;
import org.kenux.miraclelibrary.domain.member.service.LibrarianJoinService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/librarian")
@RequiredArgsConstructor
public class LibrarianController {

    private final LibrarianJoinService librarianJoinService;

    @PostMapping
    public ResponseEntity<?> join(@Valid @RequestBody LibrarianJoinRequest librarianJoinRequest) {
        final Long id = librarianJoinService.add(librarianJoinRequest);
        return ResponseEntity.ok(id);
    }
}