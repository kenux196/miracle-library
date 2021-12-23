package org.kenux.miraclelibrary.repository;

import org.kenux.miraclelibrary.domain.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerMemoryRepository {

    List<Member> members = new ArrayList<>();

    public void clear() {
        members.clear();
    }

    public Member save(Member member) {
        member.assignId(getNextId());
        members.add(member);
        return member;
    }

    private Long getNextId() {
        return (long) (members.size() + 1);
    }

    public List<Member> findAll() {
        return members;
    }

    public Optional<Member> findById(Long id) {
        return members.stream().filter(customer -> customer.getId().equals(id)).findFirst();
    }

    public Optional<Member> findByName(String name) {
        return members.stream()
                .filter(customer -> customer.getName().equals(name))
                .findFirst();
    }

    public boolean existsByEmail(String email) {
        return members.stream()
                .anyMatch(customer -> customer.getEmail().equals(email));
    }
}
