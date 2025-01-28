package com.rehic.members;


import java.util.List;
import java.util.UUID;

public interface MemberService {
    MemberDto addMember(MembershipRegistrationForm member);
    List<MemberDto> getMembers();
    MemberDto getMember(String email);
    void updateMember(MemberDto member);
    void deleteMember(UUID id);

}
