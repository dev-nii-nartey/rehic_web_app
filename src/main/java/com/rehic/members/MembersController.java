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
    public String addMember(@RequestBody MembershipRegistrationForm memberRegistrationForm) {
        MemberDto member = memberService.addMember(memberRegistrationForm);
        return member.getFirstName();
    }

    @GetMapping("/{email}")
    public MemberDto getMember(@PathVariable String email)  {
        return memberService.getMember(email) ;
    }

    @GetMapping
    public List<MemberDto> getAllMembers() {
        return memberService.getMembers();
    }

    @GetMapping("/test")
    public String test() {
        return "Works";
    }
}
