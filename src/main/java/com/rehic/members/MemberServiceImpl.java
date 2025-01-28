package com.rehic.members;

import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@Transactional
@AllArgsConstructor
@Data
public class MemberServiceImpl implements MemberService {

    private MemberRepository memberRepository;

    @Override
    public MemberDto addMember(MembershipRegistrationForm member) {

        Member newMember = new Member(member);
        Member save = memberRepository.insert(newMember);
        return new MemberDto(save);
    }

    @Override
    public List<MemberDto> getMembers() {
        return memberRepository.findAll().stream().map(MemberDto::new).collect(Collectors.toList());
    }

    @Override
    public MemberDto getMember(String email) {
        Member member = memberRepository.findById(email).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource not found"));
        return new MemberDto(member);
    }

    @Override
    public void updateMember(MemberDto member) {

    }

    @Override
    public void deleteMember(UUID id) {

    }
}
