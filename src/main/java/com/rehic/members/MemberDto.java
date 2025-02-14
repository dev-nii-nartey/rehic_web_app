package com.rehic.members;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class MemberDto implements Serializable {
    private UUID recordId;
    private String branchName;
    private LocalDate registrationDate;
    // Personal Information
    private String firstName;
    private String lastName;
    private String preferredName;
    private LocalDate dateOfBirth;
    private Gender gender;
    private MaritalStatus maritalStatus;
    private String residingAddress;
    private String primaryPhone;
    private String secondaryPhone;
    private String emailAddress;
    private String occupation;
    private String employer;
    // Family Information
    private String spouseName;
    private String spousePhone;
    private String fatherName;
    private String fatherHometown;
    private String fatherContact;
    private String motherName;
    private String motherHometown;
    private String motherContact;
    private String emergencyContactPhone;
    private String emergencyContactRelationship;
    // Spiritual Journey
    private LocalDate dateJoinedChurch;
    private Boolean baptizedWithHolySpirit;
    private LocalDate dateOfSalvation;
    private LocalDate baptismDate;
    private String previousChurchAffiliation;
    private Integer yearsAttended;
    // Ministry and Skills
    private List<String> ministriesOfInterest;
    private List<String> spiritualGifts;
    private List<String> skills;
    // Additional Information
    private String specialNeeds;
    private String howDidYouHear;


    public MemberDto(Member member) {
        this.recordId = member.recordId();
        this.branchName = member.branchName();
        this.registrationDate = member.registrationDate();
        this.firstName = member.firstName();
        this.lastName = member.lastName();
        this.preferredName = member.preferredName();
        this.dateOfBirth = member.dateOfBirth();
        this.gender = member.gender();
        this.maritalStatus = member.maritalStatus();
        this.residingAddress = member.residingAddress();
        this.primaryPhone = member.primaryPhone();
        this.secondaryPhone = member.secondaryPhone();
        this.emailAddress = member.emailAddress();
        this.occupation = member.occupation();
        this.employer = member.employer();
        this.spouseName = member.spouseName();
        this.spousePhone = member.spousePhone();
        this.fatherName = member.fatherName();
        this.fatherHometown = member.fatherHometown();
        this.fatherContact = member.fatherContact();
        this.motherName = member.motherName();
        this.motherHometown = member.motherHometown();
        this.motherContact = member.motherContact();
        this.emergencyContactPhone = member.emergencyContactPhone();
        this.emergencyContactRelationship = member.emergencyContactRelationship();
        this.dateJoinedChurch = member.dateJoinedChurch();
        this.baptizedWithHolySpirit = member.baptizedWithHolySpirit();
        this.dateOfSalvation = member.dateOfSalvation();
        this.baptismDate = member.baptismDate();
        this.previousChurchAffiliation = member.previousChurchAffiliation();
        this.yearsAttended = member.yearsAttended();
        this.ministriesOfInterest = member.ministriesOfInterest();
        this.spiritualGifts = member.spiritualGifts();
        this.skills = member.skills();
        this.specialNeeds = member.specialNeeds();
        this.howDidYouHear = member.howDidYouHear();
    }
}
