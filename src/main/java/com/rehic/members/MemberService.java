package com.rehic.members;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface MemberService {
    MemberDto addMember(MembershipRegistrationForm member);
     Page<MemberDto> getMembers(Pageable pageable);
    MemberDto getMember(String email);
    MemberDto updateMember(String email, MemberDto dto);
     void deleteMember(String email);
    Page<MemberDto> searchByName(String name, Pageable pageable);

}
