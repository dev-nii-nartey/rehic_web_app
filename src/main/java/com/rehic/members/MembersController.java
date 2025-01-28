package com.rehic.members;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.bind.annotation.*;



import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@RestController
@RequestMapping("/api/v1/rehic/members")
@AllArgsConstructor
@Data
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class MembersController {

    private MemberService memberService;

    @PostMapping
    public CustomResponses addMember(@RequestBody MembershipRegistrationForm memberRegistrationForm) {
        MemberDto member = memberService.addMember(memberRegistrationForm);
        return new CustomResponses(member.getFirstName());
    }

    @GetMapping("/{email}")
    public MemberDto getMember(@PathVariable String email)  {
        return memberService.getMember(email) ;
    }

    @GetMapping
    public Page<MemberDto> getAllMembers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return memberService.getMembers(PageRequest.of(page, size));
    }


    @DeleteMapping("/{email}")
    public void deleteMember(@PathVariable String email) {
        memberService.deleteMember(email);
    }

    @PutMapping
    public MemberDto updateMember( @RequestBody MemberDto dto) {
        return memberService.updateMember(dto.getEmailAddress(), dto);
    }


    @GetMapping("/search")
    public Page<MemberDto> searchMembers(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return memberService.searchByName(name, PageRequest.of(page, size));
    }

}
