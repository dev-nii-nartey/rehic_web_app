package com.rehic.members;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


public record MembershipRegistrationForm(
        String branchName,
        UUID recordId,
        LocalDate registrationDate,
        // Personal Information
@NotBlank String firstName,
        @NotBlank     String lastName,
        String preferredName,
        @NotBlank  LocalDate dateOfBirth,
        @NotBlank Gender gender,
        @NotBlank   MaritalStatus maritalStatus,
        @NotBlank  String residingAddress,
        @NotBlank  String primaryPhone,
        String secondaryPhone,
       @Email String emailAddress,
        String occupation,
        String employer,
        // Family Information
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
        // Spiritual Journey
        LocalDate dateJoinedChurch,
        Boolean baptizedWithHolySpirit,
        LocalDate dateOfSalvation,
        LocalDate baptismDate,
        String previousChurchAffiliation,
        Integer yearsAttended,
        // Ministry and Skills
        List<String> ministriesOfInterest,
        List<String> spiritualGifts,
        List<String> skills,
        // Faith and Commitment
        Boolean agreeWithBibleIsInspiredWord,
        Boolean agreeWithSalvationThroughFaith,
        Boolean agreeWithJesusSonOfGod,
        Boolean commitmentAttendServices,
        Boolean commitmentSupportActivities,
        Boolean commitmentTithe,
        Boolean commitmentLiveChristianValues,
        LocalDate signatureDate,
        // Consent and Privacy
        Boolean consentContactPermission,
        Boolean consentPhotoUse,
        LocalDate consentSignatureDate,
        // Additional Information
        String specialNeeds,
        String howDidYouHear,
        Boolean isDeleted

) {

    // Default values for date fields
    public MembershipRegistrationForm {
        if (registrationDate == null) registrationDate = LocalDate.now();
        if (signatureDate == null) signatureDate = LocalDate.now();
        if (consentSignatureDate == null) consentSignatureDate = LocalDate.now();
        if (recordId == null) recordId = UUID.randomUUID();

    }


}
