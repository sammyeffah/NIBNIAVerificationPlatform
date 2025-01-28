package com.nib.gh.nia.model;

import lombok.Data;

@Data
public class VerificationData {
    private int id;
    private String nationalId;
    private boolean requestStatus;
    private String transactionId;
    private String requestTime;
    private String responseTime;
    private String name;
    private String digitalAddress;
    private String otherDigitalAddress;
    private String phoneNumber;
    private String center;
    private String dateOfBirth;
    private String cardId;
    private String cardValidFrom;
    private String cardValidTo;
    private String reason;
}
