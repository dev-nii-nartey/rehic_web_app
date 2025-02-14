package com.rehic.members;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Document(collection = "members")
public record Member(
        UUID recordId,
        String branchName,
        LocalDate registrationDate,
        String firstName,
        String lastName,
        String preferredName,
        LocalDate dateOfBirth,
        Gender gender,
        MaritalStatus maritalStatus,
        String residingAddress,
        String primaryPhone,
        String secondaryPhone,
        @Id  String emailAddress,
        String occupation,
        String employer,
        String spouseName,
        String spousePhone,
        String fatherName,
        String fatherHometown,
        String fatherContact,
        String motherName,
        String motherHometown,
        String motherContact,
        String emergencyContactPhone,
        String emergencyContactRelationship,
        LocalDate dateJoinedChurch,
        Boolean baptizedWithHolySpirit,
        LocalDate dateOfSalvation,
        LocalDate baptismDate,
        String previousChurchAffiliation,
        Integer yearsAttended,
        List<String> ministriesOfInterest,
        List<String> spiritualGifts,
        List<String> skills,
        Boolean agreeWithBibleIsInspiredWord,
        Boolean agreeWithSalvationThroughFaith,
        Boolean agreeWithJesusSonOfGod,
        Boolean commitmentAttendServices,
        Boolean commitmentSupportActivities,
        Boolean commitmentTithe,
        Boolean commitmentLiveChristianValues,
        LocalDate signatureDate,
        Boolean consentContactPermission,
        Boolean consentPhotoUse,
        LocalDate consentSignatureDate,
        // Additional Information
        String specialNeeds,
        String howDidYouHear,
        Boolean isDeleted
) {
    public Member(MembershipRegistrationForm membershipRegistrationForm) {
        this(
                UUID.randomUUID(),
                membershipRegistrationForm.branchName(),
                LocalDate.now(),
                membershipRegistrationForm.firstName(),
                membershipRegistrationForm.lastName(),
                membershipRegistrationForm.preferredName(),
                membershipRegistrationForm.dateOfBirth(),
                membershipRegistrationForm.gender(),
                membershipRegistrationForm.maritalStatus(),
                membershipRegistrationForm.residingAddress(),
                membershipRegistrationForm.primaryPhone(),
                membershipRegistrationForm.secondaryPhone(),
                membershipRegistrationForm.emailAddress(),
                membershipRegistrationForm.occupation(),
                membershipRegistrationForm.employer(),
                membershipRegistrationForm.spouseName(),
                membershipRegistrationForm.spousePhone(),
                membershipRegistrationForm.fatherName(),
                membershipRegistrationForm.fatherHometown(),
                membershipRegistrationForm.fatherContact(),
                membershipRegistrationForm.motherName(),
                membershipRegistrationForm.motherHometown(),
                membershipRegistrationForm.motherContact(),
                membershipRegistrationForm.emergencyContactPhone(),
                membershipRegistrationForm.emergencyContactRelationship(),
                membershipRegistrationForm.dateJoinedChurch(),
                membershipRegistrationForm.baptizedWithHolySpirit(),
                membershipRegistrationForm.dateOfSalvation(),
                membershipRegistrationForm.baptismDate(),
                membershipRegistrationForm.previousChurchAffiliation(),
                membershipRegistrationForm.yearsAttended(),
                membershipRegistrationForm.ministriesOfInterest(),
                membershipRegistrationForm.spiritualGifts(),
                membershipRegistrationForm.skills(),
                true,
                true,
                true,
                true,
                true,
                true,
                true,
                LocalDate.now(),
                true,
                true,
                LocalDate.now(),
                membershipRegistrationForm.specialNeeds(),
                membershipRegistrationForm.howDidYouHear(),
                membershipRegistrationForm.isDeleted() != null ? membershipRegistrationForm.isDeleted() : false
        );
    }


    // Helper method to update isDeleted
    public Member withIsDeleted(boolean isDeleted) {
        return new Member(
                recordId, branchName, registrationDate, firstName, lastName, preferredName,
                dateOfBirth, gender, maritalStatus, residingAddress, primaryPhone, secondaryPhone,
                emailAddress, occupation, employer, spouseName, spousePhone, fatherName, fatherHometown,
                fatherContact, motherName, motherHometown, motherContact, emergencyContactPhone,
                emergencyContactRelationship, dateJoinedChurch, baptizedWithHolySpirit, dateOfSalvation,
                baptismDate, previousChurchAffiliation, yearsAttended, ministriesOfInterest,
                spiritualGifts, skills, agreeWithBibleIsInspiredWord, agreeWithSalvationThroughFaith,
                agreeWithJesusSonOfGod, commitmentAttendServices, commitmentSupportActivities,
                commitmentTithe, commitmentLiveChristianValues, signatureDate, consentContactPermission,
                consentPhotoUse, consentSignatureDate, specialNeeds, howDidYouHear, isDeleted
        );
    }

}
