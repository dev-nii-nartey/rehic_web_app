package com.rehic.members;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


public record MembershipRegistrationForm(
        String branchName,
        UUID recordId,
        LocalDate registrationDate,
        // Personal Information
        String firstName,
        String lastName,
        String preferredName,
        LocalDate dateOfBirth,
        Gender gender,
        MaritalStatus maritalStatus,
        String residingAddress,
        String primaryPhone,
        String secondaryPhone,
        String emailAddress,
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
        boolean baptizedWithHolySpirit,
        LocalDate dateOfSalvation,
        LocalDate baptismDate,
        String previousChurchAffiliation,
        Integer yearsAttended,
        // Ministry and Skills
        List<String> ministriesOfInterest,
        List<String> spiritualGifts,
        List<String> skills,
        // Faith and Commitment
        boolean agreeWithBibleIsInspiredWord,
        boolean agreeWithSalvationThroughFaith,
        boolean agreeWithJesusSonOfGod,
        boolean commitmentAttendServices,
        boolean commitmentSupportActivities,
        boolean commitmentTithe,
        boolean commitmentLiveChristianValues,
        LocalDate signatureDate,
        // Consent and Privacy
        boolean consentContactPermission,
        boolean consentPhotoUse,
        LocalDate consentSignatureDate,
        // Additional Information
        String specialNeeds,
        String howDidYouHear

) {

    // Default values for date fields
    public MembershipRegistrationForm {
        if (registrationDate == null) registrationDate = LocalDate.now();
        if (signatureDate == null) signatureDate = LocalDate.now();
        if (consentSignatureDate == null) consentSignatureDate = LocalDate.now();
        if (recordId == null) recordId = UUID.randomUUID();

    }


}
