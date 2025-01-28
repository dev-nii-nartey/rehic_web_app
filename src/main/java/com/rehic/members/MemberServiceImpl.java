package com.rehic.members;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;



@Service
@Transactional
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public MemberDto addMember(MembershipRegistrationForm form) {
        String email = form.emailAddress();
        return memberRepository.findById(email)
                .map(existing -> {
                    if (existing.isDeleted()) {
                        Member reactivated = new Member(form).withIsDeleted(false);
                        return new MemberDto(memberRepository.save(reactivated));
                    } else {
                        throw new ResponseStatusException(HttpStatus.CONFLICT, "Member already exists");
                    }
                })
                .orElseGet(() -> {
                    Member newMember = new Member(form).withIsDeleted(false);
                    return new MemberDto(memberRepository.save(newMember));
                });
    }

    @Override
    public Page<MemberDto> getMembers(Pageable pageable) {
        return memberRepository.findAllByIsDeletedFalse(pageable)
                .map(MemberDto::new);
    }

    @Override
    public MemberDto getMember(String email) {
        return null;
    }

    @Override
    public void deleteMember(String email) {
        Member member = memberRepository.findById(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        memberRepository.save(member.withIsDeleted(true));
    }


    @Override
    public MemberDto updateMember(String email, MemberDto dto) {
        Member existing = memberRepository.findById(email)
                .filter(m -> !m.isDeleted())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Member updated = updateFromDto(existing, dto);
        return new MemberDto(memberRepository.save(updated));
    }

    private Member updateFromDto(Member existing, MemberDto dto) {
        return new Member(
                // Non-updatable fields (from existing)
                existing.recordId(),
                // Updatable fields (check dto for non-null)
                dto.getBranchName() != null ? dto.getBranchName() : existing.branchName(),
                dto.getRegistrationDate() != null ? dto.getRegistrationDate() : existing.registrationDate(),
                dto.getFirstName() != null ? dto.getFirstName() : existing.firstName(),
                dto.getLastName() != null ? dto.getLastName() : existing.lastName(),
                dto.getPreferredName() != null ? dto.getPreferredName() : existing.preferredName(),
                dto.getDateOfBirth() != null ? dto.getDateOfBirth() : existing.dateOfBirth(),
                dto.getGender() != null ? dto.getGender() : existing.gender(),
                dto.getMaritalStatus() != null ? dto.getMaritalStatus() : existing.maritalStatus(),
                dto.getResidingAddress() != null ? dto.getResidingAddress() : existing.residingAddress(),
                dto.getPrimaryPhone() != null ? dto.getPrimaryPhone() : existing.primaryPhone(),
                dto.getSecondaryPhone() != null ? dto.getSecondaryPhone() : existing.secondaryPhone(),
                // Non-updatable: emailAddress
                existing.emailAddress(),
                dto.getOccupation() != null ? dto.getOccupation() : existing.occupation(),
                dto.getEmployer() != null ? dto.getEmployer() : existing.employer(),
                dto.getSpouseName() != null ? dto.getSpouseName() : existing.spouseName(),
                dto.getSpousePhone() != null ? dto.getSpousePhone() : existing.spousePhone(),
                dto.getFatherName() != null ? dto.getFatherName() : existing.fatherName(),
                dto.getFatherHometown() != null ? dto.getFatherHometown() : existing.fatherHometown(),
                dto.getFatherContact() != null ? dto.getFatherContact() : existing.fatherContact(),
                dto.getMotherName() != null ? dto.getMotherName() : existing.motherName(),
                dto.getMotherHometown() != null ? dto.getMotherHometown() : existing.motherHometown(),
                dto.getMotherContact() != null ? dto.getMotherContact() : existing.motherContact(),
                dto.getEmergencyContactPhone() != null ? dto.getEmergencyContactPhone() : existing.emergencyContactPhone(),
                dto.getEmergencyContactRelationship() != null ? dto.getEmergencyContactRelationship() : existing.emergencyContactRelationship(),
                dto.getDateJoinedChurch() != null ? dto.getDateJoinedChurch() : existing.dateJoinedChurch(),
                dto.getBaptizedWithHolySpirit() != null ? dto.getBaptizedWithHolySpirit() : existing.baptizedWithHolySpirit(),
                dto.getDateOfSalvation() != null ? dto.getDateOfSalvation() : existing.dateOfSalvation(),
                dto.getBaptismDate() != null ? dto.getBaptismDate() : existing.baptismDate(),
                dto.getPreviousChurchAffiliation() != null ? dto.getPreviousChurchAffiliation() : existing.previousChurchAffiliation(),
                dto.getYearsAttended() != null ? dto.getYearsAttended() : existing.yearsAttended(),
                dto.getMinistriesOfInterest() != null ? dto.getMinistriesOfInterest() : existing.ministriesOfInterest(),
                dto.getSpiritualGifts() != null ? dto.getSpiritualGifts() : existing.spiritualGifts(),
                dto.getSkills() != null ? dto.getSkills() : existing.skills(),
                // Fields not in DTO (retain existing values)
                existing.agreeWithBibleIsInspiredWord(),
                existing.agreeWithSalvationThroughFaith(),
                existing.agreeWithJesusSonOfGod(),
                existing.commitmentAttendServices(),
                existing.commitmentSupportActivities(),
                existing.commitmentTithe(),
                existing.commitmentLiveChristianValues(),
                existing.signatureDate(),
                existing.consentContactPermission(),
                existing.consentPhotoUse(),
                existing.consentSignatureDate(),
                // SpecialNeeds is in DTO
                dto.getSpecialNeeds() != null ? dto.getSpecialNeeds() : existing.specialNeeds(),
                // howDidYouHear not in DTO
                existing.howDidYouHear(),
                // Non-updatable: isDeleted
                existing.isDeleted()
        );
    }


    @Override
    public Page<MemberDto> searchByName(String name, Pageable pageable) {
        return memberRepository.searchByName(name, pageable)
                .map(MemberDto::new);
    }
}
