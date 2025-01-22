package com.rehic.members;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/v1/rehic/members")
@AllArgsConstructor
@Data
public class MembersController {

    private MemberService memberService;

    @PostMapping
    private String addMember(@RequestBody MembershipRegistrationForm memberRegistrationForm) {
        MemberDto member = memberService.addMember(memberRegistrationForm);
        return member.getFirstName();
    }

    @GetMapping("/{email}")
    private MemberDto getMember(@PathVariable String email)  {
        return memberService.getMember(email) ;
    }

    @GetMapping
    private List<MemberDto> getAllMembers() {
        return memberService.getMembers();
    }

    @GetMapping("/test")
    private String test() {
        return "Works";
    }
}
